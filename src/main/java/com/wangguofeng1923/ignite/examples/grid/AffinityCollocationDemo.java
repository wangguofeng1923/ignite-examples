package com.wangguofeng1923.ignite.examples.grid;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import com.wangguofeng1923.ignite.examples.domain.Company;
import com.wangguofeng1923.ignite.examples.domain.Person;
import com.wangguofeng1923.ignite.examples.domain.PersonKey;

public class AffinityCollocationDemo {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger

		igniteCfg.setGridLogger(gridLog);

		try (Ignite ignite = Ignition.start(igniteCfg);) {
			IgniteCache<Object,Object> cache = ignite.getOrCreateCache("mycache");
			ResultMap res=putData(cache);
			long companyId=res.getComp().getId();
			PersonKey personKey1=res.getPerson1().getPersonKey();
			PersonKey personKey2=res.getPerson2().getPersonKey();
			
			ignite.compute().affinityRun(cache.getName(), companyId, ()->{
				
				  Company company = (Company)cache.get(companyId);

				  // Since we collocated persons with the company in the above example,
				  // access to the persons objects is local.
				  Person person1 = (Person)cache.get(personKey1);
				  Person person2 = (Person)cache.get(personKey2);
				System.out.println(person1.getLastName());
				System.out.println(person2.getLastName());
			});
			
//			CacheConfiguration<String,Integer>config=new CacheConfiguration<>("myCache");
//			config.setCacheMode(CacheMode.PARTITIONED);
//			config.setBackups(1);
//			config.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_ASYNC);
//			IgniteCache<String,Integer> cache = ignite.getOrCreateCache(config);
//			
//			for(int i=0;i<100;i++){
//				cache.put(String.valueOf(i), i);
//			}
		
		}

	}
	
	
	private static ResultMap putData(IgniteCache<Object,Object>cache) {
		Person p1=new Person();
		p1.setId(1L);
		p1.setFirstName("wang");
		p1.setLastName("guofeng");
		p1.setSalary(100D);
		
		
		Person p2=new Person();
		p2.setId(2L);
		p2.setFirstName("wang");
		p2.setLastName("guofeng2");
		p2.setSalary(200D);
		
		Company comp=new Company(3L,"mycompany");
		
		// Instantiate person keys with the same company ID which is used as affinity key.
		
		PersonKey key1=new PersonKey(p1.getId(),comp.getId());
		PersonKey key2=new PersonKey(p2.getId(),comp.getId());
		
		p1.setPersonKey(key1);
		p2.setPersonKey(key2);
		// Both, the company and the person objects will be cached on the same node.
		cache.put(key1, p1);
		cache.put(key2, p2);
		cache.put(comp.getId(), comp);
		ResultMap res=new ResultMap();
		res.setComp(comp);
		res.setPerson1(p1);
		res.setPerson2(p2);
		return res;
	}
	
	private static class ResultMap{
		private Person person1;
		private Person person2;
		private Company comp;
		public Person getPerson1() {
			return person1;
		}
		public void setPerson1(Person person1) {
			this.person1 = person1;
		}
		public Person getPerson2() {
			return person2;
		}
		public void setPerson2(Person person2) {
			this.person2 = person2;
		}
		public Company getComp() {
			return comp;
		}
		public void setComp(Company comp) {
			this.comp = comp;
		}
		
	}

}
