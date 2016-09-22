package com.wangguofeng1923.ignite.examples.grid;

import java.util.UUID;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMemoryMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class OffHeapMemory {
		public static void main(String[] args) {
			IgniteConfiguration igniteCfg = new IgniteConfiguration();
			igniteCfg.setGridLogger(new Slf4jLogger());

			try (Ignite ignite = Ignition.start(igniteCfg);) {
				CacheConfiguration<String,Long>config=new CacheConfiguration<>("myCache");
				config.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
				config.setMemoryMode(CacheMemoryMode.ONHEAP_TIERED);
				//2G off heap memory
				config.setOffHeapMaxMemory(2 * 1024L * 1024L * 1024L);
				config.setSwapEnabled(true);
				IgniteCache<String,Long> cache = ignite.getOrCreateCache(config);
				while(true){
					
					cache.put(UUID.randomUUID().toString(), 0L);
				}
				
			}
	}
}
