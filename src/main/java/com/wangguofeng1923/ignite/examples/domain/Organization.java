package com.wangguofeng1923.ignite.examples.domain;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Organization implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@QuerySqlField(index = true)
	private long id;
	
	@QuerySqlField(index = true)
	private String name;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
