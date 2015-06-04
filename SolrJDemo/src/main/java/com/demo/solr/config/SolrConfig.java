package com.demo.solr.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SolrConfig {

	private final static Logger logger = Logger.getLogger(SolrConfig.class);

	public static String zkHost;

	public static int zkConnectTimeout;

	public static int zkClientTimeout;

	public static String defaultCollection;

	static {
		InputStream is = SolrConfig.class
				.getResourceAsStream("/solr.properties");
		if (is != null) {
			Properties properties = new Properties();
			try {
				properties.load(is);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
			zkHost = properties.getProperty("zk.host");
			String zkClientTimeoutStr = properties
					.getProperty("zk.client.timeout");
			if (StringUtils.isNotEmpty(zkClientTimeoutStr)) {
				zkClientTimeout = Integer.parseInt(zkClientTimeoutStr);
			}
			String zkConnectTimeoutStr = properties
					.getProperty("zk.connect.timeout");
			if (StringUtils.isNotEmpty(zkConnectTimeoutStr)) {
				zkConnectTimeout = Integer.parseInt(zkConnectTimeoutStr);
			}
			defaultCollection = properties
					.getProperty("solr.default.collection");

		}
	}
}
