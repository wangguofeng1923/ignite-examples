package com.wangguofeng1923.ignite.examples.store;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;

import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.OID;
import org.neodatis.odb.Objects;
import org.neodatis.odb.OdbConfiguration;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.criteria.EqualCriterion;

import com.wangguofeng1923.ignite.examples.domain.Person;

public class PersonCacheStore extends CacheStoreAdapter<Long, Person> {
	private static final String DB_NAME="ignite-neodatis.odb";
	public PersonCacheStore(){
		try {
			OdbConfiguration.setDatabaseCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	//Cache.get
	@Override
	public Person load(Long key) throws CacheLoaderException {
		ODB odb=null;
		try{
		 odb=ODBFactory.open(DB_NAME);
		 CriteriaQuery query=odb.criteriaQuery(Person.class, new EqualCriterion("id",key));
		 Objects<Person>objs=	 odb.getObjects(query);
		 if(objs.hasNext()){
			 return objs.next();
		 }
		}finally{
			if(odb!=null){
				odb.close();
			}
		}
		return null;
	}
	//Cache.put
	@Override
	public void write(Entry<? extends Long, ? extends Person> entry) throws CacheWriterException {
		ODB odb=null;
		try{
		 odb=ODBFactory.open(DB_NAME);
		 odb.store(entry.getValue());

		}finally{
			if(odb!=null){
				odb.close();
			}
		}
	}
	//Cache.remove
	@Override
	public void delete(Object key) throws CacheWriterException {
		ODB odb=null;
		try{
		 odb=ODBFactory.open(DB_NAME);
		 CriteriaQuery query=odb.criteriaQuery(Person.class, new EqualCriterion("id",key));
		 Objects<Person>objs=	 odb.getObjects(query);
		 while(objs.hasNext()){
			 odb.delete(objs.next());
		 }
		}finally{
			if(odb!=null){
				odb.close();
			}
		}
		
	}
	
	//IgniteCache.loadCache(),IgniteCache.localLoadCache()
	@Override
	public void loadCache(IgniteBiInClosure<Long, Person> clo, Object... args) {
		ODB odb=null;
		try{
		 odb=ODBFactory.open(DB_NAME);
		 Objects<Person>objs=	 odb.getObjects(Person.class);
		 while(objs.hasNext()){
			 Person p=objs.next();
			 clo.apply(p.getId(), p);
		 }
		}finally{
			if(odb!=null){
				odb.close();
			}
		}
		
		super.loadCache(clo, args);
	}

	
	
	@Override
	public Map<Long, Person> loadAll(Iterable<? extends Long> keys) {
		// TODO Auto-generated method stub
		return super.loadAll(keys);
	}

	@Override
	public void writeAll(Collection<Entry<? extends Long, ? extends Person>> entries) {
		// TODO Auto-generated method stub
		super.writeAll(entries);
	}

	@Override
	public void deleteAll(Collection<?> keys) {
		// TODO Auto-generated method stub
		super.deleteAll(keys);
	}

	@Override
	public void sessionEnd(boolean commit) {
		// TODO Auto-generated method stub
		super.sessionEnd(commit);
	}

}
