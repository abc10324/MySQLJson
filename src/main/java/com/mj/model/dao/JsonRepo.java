package com.mj.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mj.model.JsonTable;

@Repository
public interface JsonRepo extends JpaRepository<JsonTable, Integer> {

	@Query(value = "SELECT * FROM jsontable WHERE jsons ->> '$.name' = ?1 " , nativeQuery = true)
	public List<JsonTable> findByName(String name);
	
	@Query(value = "SELECT * FROM jsontable WHERE jsons ->> '$.name' in (?1) " , nativeQuery = true)
	public List<JsonTable> findByNames(List<String> names);
	
	@Query(value = "SELECT * FROM jsontable WHERE JSON_CONTAINS(jsons -> '$.tags' , ?1 )" , nativeQuery = true)
	public List<JsonTable> findByTag(String tag);
	
}
