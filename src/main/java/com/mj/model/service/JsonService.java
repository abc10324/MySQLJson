package com.mj.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mj.model.JsonTable;
import com.mj.model.dao.JsonRepo;

@Service
public class JsonService {

	@Autowired
	private JsonRepo repo;

	public List<JsonTable> findByName(String name){
		List<JsonTable> resultList = repo.findByName(name);
		
		for(JsonTable json : resultList) {
			json.parseJsonCol();
		}
		
		return resultList;
	}
	
	public List<JsonTable> findByNames(List<String> names){
		List<JsonTable> resultList = repo.findByNames(names);
		
		for(JsonTable json : resultList) {
			json.parseJsonCol();
		}
		
		return resultList;
	}
	
	public List<JsonTable> findByTag(List<String> tag){
//		List<JsonTable> resultList = repo.findByTag((new Gson()).toJson(tag));
		
		Map<String,Object> where = new HashMap<>();
		where.put("tags", (new Gson()).toJson(tag));
		List<JsonTable> resultList = repo.search(where);
		
		
		for(JsonTable json : resultList) {
			json.parseJsonCol();
		}
		
		return resultList;
	}
	
	public List<JsonTable> findAll(){
		List<JsonTable> resultList = repo.findAll();
		
		for(JsonTable json : resultList) {
			json.parseJsonCol();
		}
		
		return resultList;
	}
	
	public List<JsonTable> findAllByParams(Map<String,Object> params){
		params.put("tags", (new Gson()).toJson(params.get("tags")));
		List<JsonTable> resultList = repo.search(params);
		
		
		for(JsonTable json : resultList) {
			json.parseJsonCol();
		}
		
		return resultList;
	}
	
	public void insert(JsonTable jsonTable) {
		jsonTable.genJsonCol();
		repo.save(jsonTable);
	}
	
	public void update(JsonTable jsonTable) {
		jsonTable.genJsonCol();
		repo.save(jsonTable);
	}
	
	public boolean exist(Integer id) {
		return repo.exists(id);
	}
	
	
}
