package com.wangguofeng1923.ignite.examples.grid;

import javax.cache.configuration.FactoryBuilder;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import com.wangguofeng1923.ignite.examples.domain.Person;
import com.wangguofeng1923.ignite.examples.store.PersonCacheStore;

public class DataLoadDemo {
	public static void main(String[] args) {
			IgniteConfiguration igniteCfg = new IgniteConfiguration();
			igniteCfg.setGridLogger(new Slf4jLogger()); // Provide correct SLF4J logger
			igniteCfg.setMetricsLogFrequency(0L);//disable metrics
			try (Ignite ignite = Ignition.start(igniteCfg);) {
//				Ignite ignite = Ignition.start(igniteCfg);
				CacheConfiguration<Long, Person>cacheConfiguration=new CacheConfiguration<>("personCache");
				cacheConfiguration.setWriteBehindBatchSize(10);
				cacheConfiguration.setWriteBehindEnabled(false);
				cacheConfiguration.setWriteBehindFlushFrequency(300);
				cacheConfiguration.setWriteBehindFlushSize(20);
				cacheConfiguration.setWriteBehindFlushThreadCount(1);
				cacheConfiguration.setCacheStoreFactory(FactoryBuilder.factoryOf(PersonCacheStore.class));
				cacheConfiguration.setReadThrough(true);
				cacheConfiguration.setWriteThrough(true);
				
				
				IgniteCache<Long, Person>cache=ignite.getOrCreateCache(cacheConfiguration);
				IgniteBiPredicate<Long, Person>predicate=new IgniteBiPredicate<Long, Person>() {

					private static final long serialVersionUID = 1L;

					@Override
					public boolean apply(Long key, Person p) {
						return false;
					}
				};
				cache.loadCache(predicate, new Object[0]);
				
			}
	}
}
