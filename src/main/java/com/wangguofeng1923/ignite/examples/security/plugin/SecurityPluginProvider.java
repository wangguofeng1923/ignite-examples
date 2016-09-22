package com.wangguofeng1923.ignite.examples.security.plugin;

import java.io.Serializable;
import java.util.UUID;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.processors.security.GridSecurityProcessor;
import org.apache.ignite.internal.processors.security.os.GridOsSecurityProcessor;
import org.apache.ignite.plugin.ExtensionRegistry;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.PluginContext;
import org.apache.ignite.plugin.PluginProvider;
import org.apache.ignite.plugin.PluginValidationException;

public class SecurityPluginProvider implements PluginProvider<SecurityPluginConfiguration>{

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String copyright() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IgnitePlugin plugin() {
		return new SecurityIgnitePlugin();
	}

	@Override
	public void initExtensions(PluginContext ctx, ExtensionRegistry registry) {
		
		
	}

	@Override
	public Object createComponent(PluginContext ctx, Class cls) {
		if(cls==GridSecurityProcessor.class){
//			return new GridSecurityProcessor();
		}
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
