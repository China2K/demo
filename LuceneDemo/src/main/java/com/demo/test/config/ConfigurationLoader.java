package com.demo.test.config;

import org.apache.log4j.Logger;

public class ConfigurationLoader {

	static Logger logger = Logger.getLogger(ConfigurationLoader.class);

	private static ConfigBean productConf;

	public void initialize() {
		logger.info("initialize method");
	}

	public static ConfigBean getProductConf() {
		return productConf;
	}

	public void setProductConf(ConfigBean productConf) {
		this.productConf = productConf;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

	}

}
