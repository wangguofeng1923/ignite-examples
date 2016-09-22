package com.wangguofeng1923.ignite.examples.grid;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class NearCacheDemo {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger

		igniteCfg.setGridLogger(gridLog);
	
		
		try (Ignite ignite = Ignition.start(igniteCfg);) {
			NearCacheConfiguration<String, Integer>nearCacheConfiguration=new NearCacheConfiguration<>();
			nearCacheConfiguration.setNearStartSize(100);
			nearCacheConfiguration.setNearEvictionPolicy(new LruEvictionPolicy<String, Integer>(100_000));
			IgniteCache<String,Integer> cache= ignite.getOrCreateNearCache("myCache", nearCacheConfiguration);
			
			for(int i=0;i<100;i++){
				cache.put(String.valueOf(i), i);
			}
		
		}

	}
}