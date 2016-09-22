package com.wangguofeng1923.ignite.examples.grid;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.CollectionConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteAsyncSupport;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class JCacheDemo {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger

		igniteCfg.setGridLogger(gridLog);

		try (Ignite ignite = Ignition.start(igniteCfg);) {
			IgniteCache<String,Integer> cache = ignite.getOrCreateCache("myCache");
			
			CacheConfiguration cfg = new CacheConfiguration();

			cfg.setName("trans-cache");
			cfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
			// Create cache with given name, if it does not exist.
			IgniteCache<Integer, String> transCache = ignite.getOrCreateCache(cfg);
			
			// Increment cache value 10 times.
			for (int i = 0; i < 10; i++)
			  cache.invoke("mykey", (entry, arg) -> {
			    Integer val = entry.getValue();

			    entry.setValue(val == null ? 1 : val + 1);

			    return null;
			  });
			
			
		 	for (int i = 0; i < 10; i++){
		 		transCache.put(i, Integer.toString(i));
			 }
		 
		    for (int i = 0; i < 10; i++){
		        System.out.println("Got [key=" + i + ", val=" + transCache.get(i) + ']');
		    }
		    IgniteCache<String, Integer> asyncCache = ignite.<String, Integer>getOrCreateCache("asyncCache")
		    		.withAsync();
		    System.out.println( asyncCache.isAsync());


			 // Asynhronously store value in cache.
			 asyncCache.getAndPut("1", 1);
	
			 // Get future for the above invocation.
			 IgniteFuture<Integer> fut = asyncCache.future();
			 // Asynchronously listen for the operation to complete.
			 fut.listen(f -> {
				 System.out.println(f.isDone());
				 System.out.println("Previous cache value: " + f.get());
				 
				 
			 });
		
		}

	}
}
