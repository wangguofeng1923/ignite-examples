package com.wangguofeng1923.ignite.examples.grid;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class CacheModeDemo {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger

		igniteCfg.setGridLogger(gridLog);

		try (Ignite ignite = Ignition.start(igniteCfg);) {
			CacheConfiguration<String,Integer>config=new CacheConfiguration<>("myCache");
			config.setCacheMode(CacheMode.LOCAL);
//			config.setCacheMode(CacheMode.REPLICATED);
//			config.setCacheMode(CacheMode.PARTITIONED);
			IgniteCache<String,Integer> cache = ignite.getOrCreateCache(config);
			
			for(int i=0;i<100;i++){
				cache.put(String.valueOf(i), i);
			}
		
		}

	}
}