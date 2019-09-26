package com.mj.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name="jsontable")
public class JsonTable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@JsonIgnore
	private String  jsons;
	
	@Transient
	private String  name;
	@Transient
	private String  sex;
	@Transient
	private List<String> tags;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJsons() {
		return jsons;
	}
	public void setJsons(String jsons) {
		this.jsons = jsons;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public void genJsonCol() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", this.name);
		map.put("sex", this.sex);
		map.put("tags", this.tags);
		
		this.jsons = (new Gson()).toJson(map);
	}
	
	public void parseJsonCol() {
		Map<String,Object> map = (new Gson()).fromJson(this.jsons, Map.class);
		this.name = (String) map.get("name");
		this.sex = (String) map.get("sex");
		this.tags = (List<String>) map.get("tags");
	}
	
	
}
