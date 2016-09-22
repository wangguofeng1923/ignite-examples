package com.wangguofeng1923.ignite.examples.grid;

import java.util.concurrent.locks.Lock;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCondition;
import org.apache.ignite.IgniteLock;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class LockDemo {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		igniteCfg.setGridLogger(new Slf4jLogger());

		try (Ignite ignite = Ignition.start(igniteCfg);) {
			CacheConfiguration<String,Integer>config=new CacheConfiguration<>("myCache");
			config.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
			IgniteCache<String,Integer> cache = ignite.getOrCreateCache(config);
			// Create a lock for the given key.
			Lock lock = cache.lock("keyLock");
		

			try {
			    // Aquire the lock.
			    lock.lock();
			  
			    cache.put("Hello", 11);
			    cache.put("World", 22);
			}
			finally {
			    // Release the lock.
			    lock.unlock();
			}
			IgniteLock igniteLock=ignite.reentrantLock("myreentranlock", true, false, true);
			
//			igniteLock.newCondition();

			try {
			    // Aquire the lock.
				igniteLock.lock();
			  
			    cache.put("Hello", 11);
			    cache.put("World", 22);
			}
			finally {
			    // Release the lock.
				igniteLock.unlock();
			}
		}

	}
}
