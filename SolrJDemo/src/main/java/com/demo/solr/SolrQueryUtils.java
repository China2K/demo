package com.demo.solr;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.demo.solr.model.Product;

public class SolrQueryUtils {

	private final static HttpSolrClient solrClient = SolrClientFactory
			.getInstance().getSolrClient();

	public static List<Product> search(String name, String keywords,
			String description, String sn, int start, int limit) throws SolrServerException, IOException{

		String searchParam = "";

		if (StringUtils.isNotEmpty(name)) {
			if (StringUtils.isNotEmpty(searchParam)) {
				searchParam += " AND name:" + name;
			} else {
				searchParam += "  name:" + name;
			}
		}

		if (StringUtils.isNotEmpty(keywords)) {
			if (StringUtils.isNotEmpty(searchParam)) {
				searchParam += " AND keywords:" + keywords;
			} else {
				searchParam += "  keywords:" + keywords;
			}
		}

		if (StringUtils.isNotEmpty(description)) {
			if (StringUtils.isNotEmpty(searchParam)) {
				searchParam += " AND description:" + description;
			} else {
				searchParam += "  description:" + description;
			}
		}

		if (StringUtils.isNotEmpty(sn)) {
			if (StringUtils.isNotEmpty(searchParam)) {
				searchParam += " AND sn:" + sn;
			} else {
				searchParam += "  sn:" + sn;
			}
		}

		if (!StringUtils.isNotEmpty(searchParam)) {
			searchParam = "*:*";
		}

		SolrQuery sQuery = new SolrQuery();

		sQuery.setQuery(searchParam);
		sQuery.setStart(start);
		sQuery.setRows(limit);
		sQuery.addSort("sn", ORDER.desc);

		QueryResponse qrsp = solrClient.query(sQuery);

		List<Product> products = qrsp.getBeans(Product.class);
		return products;
	}

}
