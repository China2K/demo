package com.demo.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;

import com.demo.solr.config.ISolrFields;
import com.demo.solr.model.Product;

public class SolrIndexUtils {
	private Logger logger = Logger.getLogger(SolrIndexUtils.class);

	private final CloudSolrClient solrClient = CloudSolrClientFactory
			.getInstance().getCloudSolrClient();


	public void addIndexs( List<Product> records) throws SolrServerException, IOException {
		int count = 0;
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		int size = records.size();
		for (int i = 0; i < size; i++) {
			SolrInputDocument item = new SolrInputDocument();
			Product record = records.get(i);
			logger.info("Start adding solr index for " + record.toString());
			item.addField(ISolrFields.ID, record.getId());
			item.addField(ISolrFields.DESCRIPTION,record.getDescription());
			item.addField(ISolrFields.KEYWORDS,record.getKeywords());
			item.addField(ISolrFields.NAME,record.getName());
			item.addField(ISolrFields.SN,record.getSn());
			docs.add(item);
			count++;
			if (count % 1000 == 0 || count == size) {
				logger.info("Begin commit " + count + " records");
				solrClient.add(docs);
				solrClient.commit();
				docs.clear();
				logger.info("End commit " + count + " records");
			}
		}
	}

}
