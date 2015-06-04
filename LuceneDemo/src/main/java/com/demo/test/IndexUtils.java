package com.demo.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.demo.test.config.ConfigBean;
import com.demo.test.config.ConfigurationLoader;
import com.demo.test.config.IndexTypeOptions;

public class IndexUtils {
	protected static Logger logger = Logger.getLogger(IndexUtils.class);

	public static void rebuildOrUpdateIndex(Iterable<Product> products,
			boolean create) throws IOException {
		ConfigBean config = ConfigurationLoader.getProductConf();
		if (create) {
			FileUtils.cleanDirectiory(config.getTempPath());
		}
		IndexWriter writer = getIndexWriter(config, create);

		try {
			for (Product product : products) {
				if (!create) {
					ConfigBean.Field key = config.getKey();
					writer.deleteDocuments(new Term(key.getName(), product
							.getId()));
				}
				writer.addDocument(createProductDoc(product, config));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (writer != null) {

				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				writer = null;
			}
		}

		if (create) {
			try {
				FileUtils.copyDirectiory(config.getTempPath(),
						config.getStorePath());
				logger.info("index files is copied from "
						+ config.getTempPath() + " to " + config.getStorePath());
			} catch (IOException e) {
				logger.error("error with copy index files: ", e);
				throw e;
			}
		}
	}

	private static IndexWriter getIndexWriter(ConfigBean config, boolean create)
			throws IOException {
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		String indexPath = null;
		if (create) {
			// Create a new index in the directory, removing any
			// previously indexed documents:
			iwc.setOpenMode(OpenMode.CREATE);
			indexPath = config.getTempPath();
		} else {
			// Add new documents to an existing index:
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			indexPath = config.getStorePath();
		}
		System.out.println("indexPath:" + indexPath);
		File file = new File(indexPath);
		if (!file.exists()) {
			file.mkdir();
		}
		Directory dir = FSDirectory.open(Paths.get(indexPath));

		// Optional: for better indexing performance, if you
		// are indexing many documents, increase the RAM
		// buffer. But if you do this, increase the max heap
		// size to the JVM (eg add -Xmx512m or -Xmx1g):
		//
		// iwc.setRAMBufferSizeMB(256.0);

		IndexWriter writer = new IndexWriter(dir, iwc);
		return writer;
	}

	private static Document createProductDoc(Product product, ConfigBean config) {

		ConfigBean.Field key = config.getKey();
		Document doc = new Document();
		FieldType fieldType = new FieldType();
		fieldType.setStored(key.isStored());
		fieldType.setIndexOptions(IndexTypeOptions.fromType(key
				.getIndexOption()));
		fieldType.setTokenized(key.isTokenized());
		doc.add(new Field(key.getName(), product.getId(), fieldType));
		for (ConfigBean.Field field : config.getFields()) {
			String value = getProperty(product, field.getName());
			if (StringUtils.isNotEmpty(value)) {
				FieldType field_type = new FieldType();
				field_type.setStored(field.isStored());
				field_type.setIndexOptions(IndexTypeOptions.fromType(field
						.getIndexOption()));
				field_type.setTokenized(field.isTokenized());
				doc.add(new Field(field.getName(), value, field_type));
			}
		}
		return doc;
	}

	private static String getProperty(Object obj, String name) {
		String result = null;
		try {
			result = BeanUtils.getProperty(obj, name);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}
