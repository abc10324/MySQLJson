package com.mj.model.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.NamedQuery;

import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mj.model.JsonTable;
import com.mj.util.SqlLoader;

@Repository
public interface JsonRepo extends JpaRepository<JsonTable, Integer> {
	
	@Query(value = "SELECT * FROM jsontable WHERE jsons ->> '$.name' = ?1 " , nativeQuery = true)
	public List<JsonTable> findByName(String name);
	
	@Query(value = "SELECT * FROM jsontable WHERE jsons ->> '$.name' in (?1) " , nativeQuery = true)
	public List<JsonTable> findByNames(List<String> names);
	
	@Query(value = "SELECT * FROM jsontable WHERE JSON_CONTAINS(jsons -> '$.tags' , ?1 )" , nativeQuery = true)
	public List<JsonTable> findByTag(String tag);
	
	@Query(value = "SELECT * FROM jsontable WHERE 1=1" 
				 + "AND (?#{#where['tags']} IS NULL OR JSON_CONTAINS(jsons -> '$.tags' , ?#{#where['tags']}))"
				 + "AND (?#{#where['name']} IS NULL OR jsons ->> '$.name' = ?#{#where['name']})"
				, nativeQuery = true)
	public List<JsonTable> search(@Param("where") Map<String,Object> where);
	
}
