package com.demo.solr;

import org.apache.solr.client.solrj.impl.CloudSolrClient;

import com.demo.solr.config.SolrConfig;

public class CloudSolrClientFactory {
	private static CloudSolrClient cloudSolrClient = null;
	private static CloudSolrClientFactory solrClientFactory = null;
	private static String zkHost = SolrConfig.zkHost;
	private static String defaultCollection = SolrConfig.defaultCollection;
	private static int zkClientTimeout = SolrConfig.zkClientTimeout;
	private static int zkConnectTimeout = SolrConfig.zkConnectTimeout;

	private CloudSolrClientFactory() {
	}

	public synchronized CloudSolrClient getCloudSolrClient() {

		if (cloudSolrClient == null) {
			cloudSolrClient = new CloudSolrClient(zkHost);
			cloudSolrClient.setDefaultCollection(defaultCollection);
			cloudSolrClient.setZkClientTimeout(zkClientTimeout);
			cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
			cloudSolrClient.connect();
		}
		return cloudSolrClient;
	}

	public static synchronized CloudSolrClientFactory getInstance() {
		if (solrClientFactory == null) {
			solrClientFactory = new CloudSolrClientFactory();
		}
		return solrClientFactory;
	}
}
