package com.wangguofeng1923.ignite.examples.grid;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.cache.configuration.FactoryBuilder;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.eviction.EvictionPolicy;
import org.apache.ignite.cache.eviction.fifo.FifoEvictionPolicy;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.cache.eviction.random.RandomEvictionPolicy;
import org.apache.ignite.cache.eviction.sorted.SortedEvictionPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import com.wangguofeng1923.ignite.examples.domain.Person;
import com.wangguofeng1923.ignite.examples.store.PersonCacheStore;

public class EvictionPolicyDemo {
	public static void main(String[] args) throws InterruptedException {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		igniteCfg.setGridLogger(new Slf4jLogger()); // Provide correct SLF4J logger
		igniteCfg.setMetricsLogFrequency(0L);//disable metrics
		
		try (Ignite ignite = Ignition.start(igniteCfg);) {
//			Ignite ignite = Ignition.start(igniteCfg);
			CacheConfiguration<Long, Person>cacheConfiguration=new CacheConfiguration<>("personCache");
			cacheConfiguration.setWriteBehindBatchSize(10);
			cacheConfiguration.setWriteBehindEnabled(false);
			cacheConfiguration.setWriteBehindFlushFrequency(300);
			cacheConfiguration.setWriteBehindFlushSize(20);
			cacheConfiguration.setWriteBehindFlushThreadCount(1);
			cacheConfiguration.setCacheStoreFactory(FactoryBuilder.factoryOf(PersonCacheStore.class));
			cacheConfiguration.setReadThrough(true);
			cacheConfiguration.setWriteThrough(true);
			
			cacheConfiguration.setOffHeapMaxMemory(-1);
//			cacheConfiguration.setEvictSynchronized(true);
			LruEvictionPolicy<Long, Person>evictionPolicy=new LruEvictionPolicy<>();
//			FifoEvictionPolicy<K, V>
//			RandomEvictionPolicy<K, V>
//			SortedEvictionPolicy<K, V>
			//Batch eviction is supported only if maximum memory limit isn't set.
			//evictionPolicy.setMaxMemorySize(200);//bytes
			evictionPolicy.setMaxSize(100);//max number
			evictionPolicy.setBatchSize(10);//batch evit.

			cacheConfiguration.setEvictionPolicy(evictionPolicy);
			
			IgniteCache<Long, Person>cache=ignite.getOrCreateCache(cacheConfiguration);
			Set<Long>keys=new HashSet<>();
			for(long i=0;i<300;i++){
				Person p=new Person();
				p.setId(i);
				p.setFirstName("wang-"+i);
				p.setLastName("guofeng-"+i);
				cache.put(p.getId(), p);
				keys.add(i);
			}
			Thread.sleep(5000);
			//the persons with id smaller than 200(Total-MaxSize) will be loaded from CacheStore(PersonCacheStore.load).
			Map<Long,Person>result=cache.getAll(keys);
			System.out.println(result.size());
		}
}
}
