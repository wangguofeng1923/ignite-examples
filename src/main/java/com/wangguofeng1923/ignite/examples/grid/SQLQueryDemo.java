package com.wangguofeng1923.ignite.examples.grid;

import java.util.List;
import java.util.stream.Collectors;

import javax.cache.Cache;
import javax.cache.Cache.Entry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMemoryMode;
import org.apache.ignite.cache.affinity.AffinityKey;
import org.apache.ignite.cache.eviction.random.RandomEvictionPolicy;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.cache.query.TextQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import com.wangguofeng1923.ignite.examples.domain.Organization;
import com.wangguofeng1923.ignite.examples.domain.Person;

public class SQLQueryDemo {
	public static void main(String[] args) {
//		System.getProperties().put("IGNITE_H2_DEBUG_CONSOLE", true);
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger
		igniteCfg.setGridLogger(gridLog);
		
		
		try (Ignite ignite = Ignition.start(igniteCfg);) {
			
			/**
			CacheConfiguration<Object,Object> ccfg = new CacheConfiguration<>();

			// Here we are setting 3 key-value type pairs to be indexed.
			ccfg.setIndexedTypes(
			  MyKey.class, MyValue.class,
			  Long.class, MyOtherValue.class,
			  UUID.class, String.class
			);
			
			*/
			CacheConfiguration<Long, Person>cacheConfiguration=new CacheConfiguration<>("personCache");
			cacheConfiguration.setIndexedTypes(Long.class, Person.class);
			IgniteCache<Long, Person> personCache = ignite.getOrCreateCache(cacheConfiguration);
			for(int i=0;i<100;i++){
				Person p=new Person();
				p.setId(i);
				p.setAge(i);
				p.setOrgId(i%5);
				p.setFirstName("firstName:"+i);
				p.setLastName("lastName"+i);
				if(i%5==0){
					p.setResume("person with resume!");
				}else{
					p.setResume("no resume!");
				}
				p.setSalary(i*20);
				personCache.put(Long.valueOf(i), p);
			}
			
			CacheConfiguration<Long, Organization>orgCacheConfiguration=new CacheConfiguration<>("orgCache");
			orgCacheConfiguration.setIndexedTypes(Long.class, Organization.class);
			IgniteCache<Long, Organization> orgCache = ignite.getOrCreateCache(orgCacheConfiguration);
			
			for(int i=0;i<10;i++){
				Organization org=new Organization();
				org.setId(i);
				org.setName("Ignite"+i);
				orgCache.put(Long.valueOf(i), org);
			}
			
			//SqlQuery
			SqlQuery<Long, Person>sql = new SqlQuery<Long, Person>(Person.class, "salary > ?");
			// Find all persons earning more than 1,000.
			try (QueryCursor<Entry<Long, Person>> cursor = personCache.query(sql.setArgs(1000))) {
			  for (Entry<Long, Person> e : cursor)
			    System.out.println(e.getValue().toString());
			}
			
			//SqlFieldsQuery
			// Execute query to get names of all employees.
			SqlFieldsQuery sqlFieldsQuery = new SqlFieldsQuery(
			  "select concat(firstName, ' ', lastName) from Person");

			// Iterate over the result set.
			try (QueryCursor<List<?>> cursor = personCache.query(sqlFieldsQuery)) {
			  for (List<?> row : cursor)
			    System.out.println("personName=" + row.get(0));
			}
			
			//cross cache query.
			 System.out.println("==================cross cache query==================");
			SqlFieldsQuery crossCacheQuery = new SqlFieldsQuery(
				    "select p.firstName  "
				        + "from Person as p, \"orgCache\".Organization as org where "
				        + "p.orgId = org.id "
				        + "and org.name = ?");

				// Execute the query and obtain the query result cursor.
				try (QueryCursor<List<?>> cursor =  personCache.query(crossCacheQuery.setArgs("Ignite0"))) {
				    for (List<?> row : cursor)
				        System.out.println("Person name=" + row.get(0));
				}
				
				 System.out.println("==================join query==================");
				
				SqlQuery <Long,Person>joinSql = new SqlQuery <Long,Person>(Person.class,
						  "from Person,\"orgCache\".Organization as org"
						  + " where Person.orgId = org.id "
						  + " and lower(org.name) = lower(?)");

						// Find all persons working for Ignite organization.
						try (QueryCursor<Entry<Long, Person>> cursor = personCache.query(joinSql.setArgs("Ignite2"))) {
						  for (Entry<Long, Person> e : cursor)
						    System.out.println(e.getValue().toString());
						}
						
						
						 System.out.println("==================SqlQueryWithDistributedJoin==================");
						
						IgniteCache<AffinityKey<Long>, Person> cache = ignite.cache("personCache");

						// SQL clause query with join over non-collocated data.
						String distributedJoinSql =
						  "from Person, \"orgCache\".Organization as org " +
						  "where Person.orgId = org.id " +
						  "and lower(org.name) = lower(?)";

						SqlQuery qry = new SqlQuery<AffinityKey<Long>, Person>(Person.class, distributedJoinSql).setArgs("Ignite2");

						// Enable distributed joins for the query.
						qry.setDistributedJoins(true);

						// Execute the query to find out employees for specified organization.
						System.out.println("Following people are 'ApacheIgnite' employees (distributed join): "+ cache.query(qry).getAll());
						
						 System.out.println("==================ExplainSqlQuery==================");
						
						SqlFieldsQuery explainSql = new SqlFieldsQuery(
								  "explain select firstName from Person where age = ?").setArgs(26); 
						
						System.out.println(cache.query(explainSql).getAll());
						
						/**off heap index*/
						System.out.println("==================off heap index configuration==================");
						CacheConfiguration<Object, Object> ccfg = new CacheConfiguration<>();

						// Set unlimited off-heap memory for cache and enable off-heap indexes.
						ccfg.setOffHeapMaxMemory(0); 

						// Cache entries will be placed on heap and can be evited to off-heap.
						ccfg.setMemoryMode(CacheMemoryMode.ONHEAP_TIERED);
						ccfg.setEvictionPolicy(new RandomEvictionPolicy(100_000));

						// Increase size of SQL on-heap row cache for off-heap indexes.
						ccfg.setSqlOnheapRowCacheSize(100_000);
		}

	}
}