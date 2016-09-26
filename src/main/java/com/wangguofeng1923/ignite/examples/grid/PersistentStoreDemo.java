package com.wangguofeng1923.ignite.examples.grid;

import javax.cache.configuration.FactoryBuilder;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import com.wangguofeng1923.ignite.examples.domain.Person;
import com.wangguofeng1923.ignite.examples.store.PersonCacheStore;

public class PersistentStoreDemo {

	public static void main(String[] args) {
			IgniteConfiguration igniteCfg = new IgniteConfiguration();
			igniteCfg.setGridLogger(new Slf4jLogger()); // Provide correct SLF4J logger
			igniteCfg.setMetricsLogFrequency(0L);
//			igniteCfg.setCacheConfiguration(cacheConfiguration);
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

				Person p=cache.get(1L);//PersonCacheStore.load
				if(p==null){
					p=new Person();
					p.setId(1L);
					p.setFirstName("wang");
					p.setLastName("guofeng");
					p.setAge(100);
					p.setSalary(200D);
					cache.put(p.getId(), p);//PersonCacheStore.write
				}
				System.out.println(p);
				
				cache.remove(p.getId());//PersonCacheStore.delete
				
				
				
			}

	}

}
