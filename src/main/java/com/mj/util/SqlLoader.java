package com.mj.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class SqlLoader {
	
	private static Map<String,String> sqlMap = new HashMap<>();
	
	static {
		ResourceLoader rl = new FileSystemResourceLoader();
		
		try {
			Resource[] resourceList = ResourcePatternUtils.getResourcePatternResolver(rl).getResources("classpath:com/mj/model/mapper/*.xml");
			
			for(Resource resource : resourceList) {
				InputStream is = resource.getInputStream();
				
				XmlMapper mapper = new XmlMapper();
				
				List<Map<String,String>> resultList = mapper.readValue(is, new TypeReference<List<Map<String,String>>>(){});
				
				for(Map<String,String> map : resultList) {
					if(map.containsKey("id")) {
						sqlMap.put(map.get("id"), map.get(""));
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String methodName) {
		return sqlMap.containsKey(methodName) ? sqlMap.get(methodName) : "";
	}
	
}
