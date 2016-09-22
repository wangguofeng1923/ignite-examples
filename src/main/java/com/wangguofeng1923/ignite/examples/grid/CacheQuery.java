package com.wangguofeng1923.ignite.examples.grid;

import java.util.List;
import java.util.stream.Collectors;

import javax.cache.Cache;
import javax.cache.Cache.Entry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.TextQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import com.wangguofeng1923.ignite.examples.domain.Person;

public class CacheQuery {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger
		igniteCfg.setGridLogger(gridLog);
	
		
		try (Ignite ignite = Ignition.start(igniteCfg);) {
			CacheConfiguration<Long, Person>cacheConfiguration=new CacheConfiguration<>("mycache");
			cacheConfiguration.setIndexedTypes(Long.class, Person.class);
			IgniteCache<Long, Person> cache = ignite.getOrCreateCache(cacheConfiguration);
			for(int i=0;i<100;i++){
				Person p=new Person();
				p.setId(i);
				p.setFirstName("person:"+i);
				if(i%5==0){
					p.setResume("person with resume!");
				}else{
					p.setResume("no resume!");
				}
				p.setSalary(i*20);
				cache.put(Long.valueOf(i), p);
			}
			// Find only persons earning more than 1,000.
			ScanQuery<Long, Person>scanQuery=new ScanQuery<>((Long id, Person p)->  p.getSalary() > 1000);
		
			try (QueryCursor<Cache.Entry<Long,Person>>cursor = cache.query(scanQuery)) {
			  for (Cache.Entry<Long,Person>entry  : cursor){
				 Person p= entry.getValue();
				 System.out.println(p);
			  }
			}
			//stream
			List<Long> keys = cache.query(new ScanQuery<Long, Person>(
				    (k, p) -> p.getSalary() > 1000) // Remote filter.
				).getAll().stream().map(Cache.Entry::getKey).collect(Collectors.toList());
			//text search
			 System.out.println("==============text search result list===============");
			TextQuery<Long, Person> txt = new TextQuery<Long, Person>(Person.class, "person");
			try (QueryCursor<Entry<Long, Person>> masters = cache.query(txt)) {
				  for (Entry<Long, Person> e : masters)
				    System.out.println(e.getValue().toString());
				}
		}

	}
}