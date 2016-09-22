package com.wangguofeng1923.ignite.examples.grid;

import java.util.stream.StreamSupport;

import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListenerException;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.ContinuousQuery;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import com.wangguofeng1923.ignite.examples.domain.Person;

public class ContinuousQueryDemo {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		igniteCfg.setGridLogger(new Slf4jLogger());
		
		try (Ignite ignite = Ignition.start(igniteCfg);) {
			

			CacheConfiguration<Long, Person>cacheConfiguration=new CacheConfiguration<>("personCache");
			cacheConfiguration.setIndexedTypes(Long.class, Person.class);
			IgniteCache<Long, Person> cache = ignite.getOrCreateCache(cacheConfiguration);
			
			
			// Create new continuous query.
			ContinuousQuery<Long, Person> qry = new ContinuousQuery<>();
			qry.setInitialQuery(new ScanQuery<Long, Person>((k, v) -> k > 10));
			// Callback that is called locally when update notifications are received.
			qry.setLocalListener((Iterable<CacheEntryEvent<? extends Long, ? extends Person>> events)->{
				StreamSupport
				.stream(events.spliterator(), false)
				.forEach(e->{
					System.out.println("changed data: key=" + e.getKey() + ", val=" + e.getValue());
				});
			});

			// This filter will be evaluated remotely on all nodes.
			// Entry that pass this filter will be sent to the caller.

				qry.setRemoteFilterFactory(new Factory<CacheEntryEventFilter<Long,Person>>() {

					private static final long serialVersionUID = 1L;

					@Override
					public CacheEntryEventFilter<Long, Person> create() {
						CacheEntryEventFilter<Long, Person> filter=new CacheEntryEventFilter<Long, Person>() {

							@Override
							public boolean evaluate(CacheEntryEvent<? extends Long, ? extends Person> event)
									throws CacheEntryListenerException {
								return	event.getValue().getId()>40;
							}
						};
						return filter;
					}
				});
				// Execute query.
				try (QueryCursor<Cache.Entry<Long, Person>> cur = cache.query(qry)) {
				  // Iterate through existing data stored in cache.
//				  for (Cache.Entry<Long, Person> e : cur)
//				    System.out.println("key=" + e.getKey() + ", val=" + e.getValue());

				  // Add a few more keys and watch a few more query notifications.
					  for(int i=0;i<100;i++){
							Person p=new Person();
							p.setId(i);
							p.setAge(i);
							p.setFirstName("firstName:"+i);
							p.setLastName("lastName"+i);
							if(i%5==0){
								p.setResume("person with resume!");
							}else{
								p.setResume("no resume!");
							}
							p.setSalary(i*20);
							cache.put(Long.valueOf(i), p);
						}
				}
				

		}
	}
}
