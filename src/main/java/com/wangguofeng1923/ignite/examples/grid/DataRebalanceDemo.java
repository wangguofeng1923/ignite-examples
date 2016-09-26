package com.wangguofeng1923.ignite.examples.grid;

import javax.cache.configuration.FactoryBuilder;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import com.wangguofeng1923.ignite.examples.domain.Person;
import com.wangguofeng1923.ignite.examples.store.PersonCacheStore;

public class DataRebalanceDemo {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		igniteCfg.setGridLogger(new Slf4jLogger()); // Provide correct SLF4J logger
		igniteCfg.setMetricsLogFrequency(0L);//disable metrics
		igniteCfg.setRebalanceThreadPoolSize(100);
		try (Ignite ignite = Ignition.start(igniteCfg);) {
//			Ignite ignite = Ignition.start(igniteCfg);
			CacheConfiguration<Long, Person>cacheConfiguration=new CacheConfiguration<>("personCache");
			/*
			 * SYNC

			Synchronous rebalancing mode. Distributed caches will not start until all necessary data is loaded from other available grid nodes.
			 This means that any call to cache public API will be blocked until rebalancing is finished.
			
			ASYNC
			
			Asynchronous rebalancing mode. Distributed caches will start immediately and will load all necessary data 
			from other available grid nodes in the background.
			
			NONE
			
			In this mode no rebalancing will take place which means that caches will be either loaded on demand from persistent store
			 whenever data is accessed, or will be populated explicitly.
			 */
			cacheConfiguration.setRebalanceMode(CacheRebalanceMode.SYNC);
			cacheConfiguration.setRebalanceBatchSize(20);
			//replace with IgniteConfiguration.setRebalanceThreadPoolSize
//			cacheConfiguration.setRebalanceThreadPoolSize(100);
			//minimize the rebanace's impact 
			cacheConfiguration.setRebalanceThrottle(3000);
			//Delay in milliseconds upon a node joining or leaving topology (or crash) after which rebalancing should be started automatically.
			cacheConfiguration.setRebalanceDelay(3000L);
			
			//Rebalance order can be set to non-zero value for caches with SYNC or ASYNC rebalance modes only. 
			//Rebalancing for caches with smaller rebalance order will be completed first. By default, rebalancing is not ordered.
			cacheConfiguration.setRebalanceOrder(1);
			IgniteCache<Long, Person>cache=ignite.getOrCreateCache(cacheConfiguration);
			
		}
}
}
