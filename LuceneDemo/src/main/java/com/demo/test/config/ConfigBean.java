package com.demo.test.config;

import java.util.List;

public class ConfigBean {

	private String indexName;
	private String storePath;
	private String tempPath;
	private int everyPage;
	private Field key;
	private List<Field> fields;

	public static class Field {
		private String name;
		private boolean stored;
		private int indexOption;
		private boolean tokenized;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isStored() {
			return stored;
		}

		public void setStored(boolean stored) {
			this.stored = stored;
		}

		public int getIndexOption() {
			return indexOption;
		}

		public void setIndexOption(int indexOption) {
			this.indexOption = indexOption;
		}

		public boolean isTokenized() {
			return tokenized;
		}

		public void setTokenized(boolean tokenized) {
			this.tokenized = tokenized;
		}

	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public int getEveryPage() {
		return everyPage;
	}

	public void setEveryPage(int everyPage) {
		this.everyPage = everyPage;
	}

	public Field getKey() {
		return key;
	}

	public void setKey(Field key) {
		this.key = key;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

}
