package com.demo.test;

import java.util.Date;

public class Product {
	private String id;
	private String name;
	private String keywords;
	private String description;
	private String sn;
	private Date updatedTime;

	public Product() {
		super();
	}

	public Product(String id, String name, String keywords, String description,
			String sn) {
		super();
		this.id = id;
		this.name = name;
		this.keywords = keywords;
		this.description = description;
		this.sn = sn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}