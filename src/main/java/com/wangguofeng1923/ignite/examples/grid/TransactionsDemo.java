package com.wangguofeng1923.ignite.examples.grid;

import java.util.stream.StreamSupport;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.configuration.Factory;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListenerException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.jta.jndi.CacheJndiTmFactory;
import org.apache.ignite.cache.query.ContinuousQuery;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionDeadlockException;
import org.apache.ignite.transactions.TransactionIsolation;
import org.apache.ignite.transactions.TransactionOptimisticException;
import org.apache.ignite.transactions.TransactionTimeoutException;

import com.atomikos.icatch.jta.TransactionManagerFactory;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.wangguofeng1923.ignite.examples.domain.Person;

public class TransactionsDemo {

	public static void main(String[] args) throws SystemException, NotSupportedException {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		igniteCfg.setGridLogger(new Slf4jLogger());
		TransactionConfiguration txCfg=new TransactionConfiguration();
		
		UserTransactionManager tm = new UserTransactionManager();
		CacheJndiTmFactory factory=new CacheJndiTmFactory(){
			private static final long serialVersionUID = 1L;

			@Override
			public TransactionManager create() {
				return tm;
			}
			
		};
		
		txCfg.setTxManagerFactory(factory);
		igniteCfg.setTransactionConfiguration(txCfg);
		
		
		try (Ignite ignite = Ignition.start(igniteCfg);) {
			CacheConfiguration<String, Integer>cacheConfiguration=new CacheConfiguration<>("mycache");
			cacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
			IgniteCache<String, Integer> cache = ignite.getOrCreateCache(cacheConfiguration);
			//init cache
			cache.put("Hello", 1);
			
			System.out.println("=================Ignite Transactions=================");
			IgniteTransactions transactions = ignite.transactions();
			//TransactionConcurrency.PESSIMISTIC  锁类型
			//TransactionIsolation.READ_COMMITTED 隔离级别
			//Transaction tx = transactions.txStart(TransactionConcurrency.PESSIMISTIC, TransactionIsolation.READ_COMMITTED)
			try (Transaction tx = transactions.txStart()) {
			    Integer hello = cache.get("Hello");
			  
			    if (hello == 1)
			        cache.put("Hello", 11);
			  
			    cache.put("World", 22);
			  
			    tx.commit();
			}
			//Deadlock Detection in Pessimistic Transactions
			System.out.println("=================Deadlock Detection in Pessimistic Transactions=================");
			try (Transaction tx = transactions.txStart(TransactionConcurrency.PESSIMISTIC, TransactionIsolation.READ_COMMITTED, 300, 0)) {

			   cache.put("Hello", 11);
			  
			    cache.put("World", 22);
			  
			    tx.commit();
			}catch (CacheException e) {
			    if (e.getCause() instanceof TransactionTimeoutException &&
			            e.getCause().getCause() instanceof TransactionDeadlockException)    
			            
			            System.out.println(e.getCause().getCause().getMessage());
			    }
			
			// OPTIMISTIC Transactions
			System.out.println("=================OPTIMISTIC Transactions=================");
//			while (true) {
//			    try (Transaction tx =  
//			         ignite.transactions().txStart(TransactionConcurrency.OPTIMISTIC,
//			                                       TransactionIsolation.SERIALIZABLE)) {
//			        // Modify cache entires as part of this transacation.
//			    	
//			        
//			        // commit transaction.  
//			        tx.commit();
//
//			        // Transaction succeeded. Leave the while loop.
//			        break;
//			    }
//			    catch (TransactionOptimisticException e) {
//			        // Transaction has failed. Retry.
//			    }
//			}
			
			
			//JTA transaction
			System.out.println("=================JTA transaction=================");
			IgniteCache<String, Integer> cache2 = ignite.getOrCreateCache(new CacheConfiguration<String, Integer>("mycache2").setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL));
			IgniteCache<String, Integer> cache3 = ignite.getOrCreateCache(new CacheConfiguration<String, Integer>("mycache3").setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL));
			tm.setTransactionTimeout(60);
			tm.begin();

			try {
				  cache.put("World1", 23);
				  cache2.put("World2", 24);
				  cache3.put("World3", 25);
				  System.out.println(cache.get("World1"));
				  System.out.println(cache2.get("World2"));
				  System.out.println(cache3.get("World3"));
				  if(System.currentTimeMillis()>1){
					  throw new RuntimeException();
				  }
				  tm.commit();
			} catch (Exception e) {
				tm.rollback();
			}
			
			  System.out.println(cache.get("World1"));
			  System.out.println(cache2.get("World2"));
			  System.out.println(cache3.get("World3"));


				

		}
	}
}
