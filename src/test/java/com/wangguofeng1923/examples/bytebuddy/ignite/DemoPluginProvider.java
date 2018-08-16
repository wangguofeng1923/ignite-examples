package com.wangguofeng1923.examples.bytebuddy.ignite;

import java.io.Serializable;
import java.util.UUID;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.GridPluginContext;
import org.apache.ignite.plugin.CachePluginContext;
import org.apache.ignite.plugin.CachePluginProvider;
import org.apache.ignite.plugin.ExtensionRegistry;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.PluginContext;
import org.apache.ignite.plugin.PluginProvider;
import org.apache.ignite.plugin.PluginValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoPluginProvider implements PluginProvider<DemoPluginConfiguration>{
	private static final Logger logger=LoggerFactory.getLogger(DemoPluginProvider.class);
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "name";
	}

	@Override
	public String version() {
		// TODO Auto-generated method stub
		return "version";
	}

	@Override
	public String copyright() {
		return "copyright";
	}

	@Override
	public DemoPlugin plugin() {
		return new DemoPlugin();
	}

	@Override
	public void initExtensions(PluginContext ctx, ExtensionRegistry registry) throws IgniteCheckedException {
		org.apache.ignite.internal.GridPluginContext ct=(GridPluginContext) ctx;
		Ignite ignite=ct.grid();
		logger.info(ctx.getClass().getName());
		
	}

	@Override
	public <T> T createComponent(PluginContext ctx, Class<T> cls) {
		logger.info(cls.getName());
		return null;
	}

	@Override
	public CachePluginProvider createCacheProvider(CachePluginContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(PluginContext ctx) throws IgniteCheckedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop(boolean cancel) throws IgniteCheckedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIgniteStart() throws IgniteCheckedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIgniteStop(boolean cancel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable provideDiscoveryData(UUID nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveDiscoveryData(UUID nodeId, Serializable data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateNewNode(ClusterNode node) throws PluginValidationException {
		// TODO Auto-generated method stub
		
	}

	

}
