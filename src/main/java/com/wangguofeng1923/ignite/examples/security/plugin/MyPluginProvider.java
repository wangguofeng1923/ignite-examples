package com.wangguofeng1923.ignite.examples.security.plugin;

import java.io.Serializable;
import java.util.UUID;

import javax.cache.Cache.Entry;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.plugin.CachePluginProvider;
import org.apache.ignite.plugin.ExtensionRegistry;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.PluginContext;
import org.apache.ignite.plugin.PluginProvider;
import org.apache.ignite.plugin.PluginValidationException;

public class MyPluginProvider implements CachePluginProvider<MyPluginConfiguration>{

	@Override
	public void start() throws IgniteCheckedException {
		System.out.println("start");
		
	}

	@Override
	public void stop(boolean cancel) {
		System.out.println("stop");
		
	}

	@Override
	public void onIgniteStart() throws IgniteCheckedException {
		System.out.println("onIgniteStart");
		
	}

	@Override
	public void onIgniteStop(boolean cancel) {
		System.out.println("onIgniteStop");
		
	}

	@Override
	public <T> T createComponent(Class<T> cls) {
		System.out.println("createComponent"+cls);
		return null;
	}

	@Override
	public <T, K, V> T unwrapCacheEntry(Entry<K, V> entry, Class<T> cls) {
		System.out.println("unwrapCacheEntry");
		return null;
	}

	@Override
	public void validate() throws IgniteCheckedException {
		System.out.println("validate");
		
	}

	@Override
	public void validateRemote(CacheConfiguration locCfg, MyPluginConfiguration locPluginCcfg,
			CacheConfiguration rmtCfg, ClusterNode rmtNode) throws IgniteCheckedException {
		System.out.println("validateRemote");
		
	}




	

}
