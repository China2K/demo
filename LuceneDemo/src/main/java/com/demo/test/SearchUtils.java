package com.demo.test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;

import com.demo.test.config.ConfigurationLoader;

/** Simple command-line based search demo. */
public class SearchUtils {
	private final static String[] Search_FieldNames = {"name","sn","keywords"};


	public static List<Product> search(String keywords) throws IOException,
			ParseException {
		List<Product> result = new ArrayList<Product>();
		String index = ConfigurationLoader.getProductConf().getStorePath();
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths
				.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		/*Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser("sn", analyzer);
		Query query = parser.parse(keywords);*/
		keywords=keywords.trim().toLowerCase();
		BooleanQuery query = new BooleanQuery();
		String[] ks = keywords.split(" ");
		
		for (String fieldName : Search_FieldNames) {
			
			query.add(new TermQuery(new Term(fieldName, QueryParser.escape(keywords))), BooleanClause.Occur.SHOULD);
			query.add(new WildcardQuery(new Term(fieldName, "*" + QueryParser.escape(keywords) + "*")), BooleanClause.Occur.SHOULD);
			for (String k : ks) {
				if (StringUtils.isNotEmpty(k.trim())) {
					String k_trim = k.trim();
					query.add(new TermQuery(new Term(fieldName, QueryParser.escape(k_trim))), BooleanClause.Occur.SHOULD);
					query.add(new WildcardQuery(new Term(fieldName, "*" + QueryParser.escape(k_trim) + "*")), BooleanClause.Occur.SHOULD);
				}
			}
		}
		
		TopDocs results = searcher.search(query, 100);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents");
		for (ScoreDoc scoreDoc : hits) {
			Document doc = searcher.doc(scoreDoc.doc);
			result.add(getItemFromDoc(doc));
		}
		return result;
	}

	private static Product getItemFromDoc(Document doc) {
		Product product = new Product();
		product.setId(doc.get("id"));
		product.setKeywords(doc.get("keywords"));
		product.setName(doc.get("name"));
		product.setSn(doc.get("sn"));
		return product;
	}
}
