package com.mj.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mj.model.JsonTable;
import com.mj.model.service.JsonService;

@RestController
public class QueryController {

	@Autowired
	private JsonService jsonService;
	
	@GetMapping("/JsonTable/{name}")
	public Object query(@PathVariable String name) {
		return jsonService.findByName(name);
	}
	
	@GetMapping("/JsonTable/Tag/{tag}")
	public Object queryByTag(@PathVariable String tag) {
		return jsonService.findByTag(Collections.singletonList(tag));
	}
	
	@GetMapping("/JsonTable")
	public Object queryAll(@RequestParam(required=false) List<String> names) {
		System.out.println("names : " + names);
		
		return names != null ? jsonService.findByNames(names) : jsonService.findAll();
	}
	
	@PostMapping("/JsonTable")
	public Object insert(@RequestBody String body) {
		JsonTable jsonTable = (new Gson()).fromJson(body, JsonTable.class);
		jsonService.insert(jsonTable);
		
		return Collections.singletonMap("status", "success");
	}
	
	@PutMapping("/JsonTable")
	public Object update(@RequestBody String body) {
		JsonTable jsonTable = (new Gson()).fromJson(body, JsonTable.class);
		
		if(jsonService.exist(jsonTable.getId()))
			jsonService.update(jsonTable);
		else
			return Collections.singletonMap("status", "No such id");
		
		return Collections.singletonMap("status", "success");
	}
	
}
