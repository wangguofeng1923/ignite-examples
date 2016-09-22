package com.wangguofeng1923.ignite.examples.security.plugin;

import java.io.Serializable;
import java.util.UUID;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.processors.security.GridSecurityProcessor;
import org.apache.ignite.plugin.ExtensionRegistry;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.PluginContext;
import org.apache.ignite.plugin.PluginProvider;
import org.apache.ignite.plugin.PluginValidationException;
import org.jetbrains.annotations.Nullable;

public class WhiteListPluginProvider 
	  implements PluginProvider<WhiteListPluginConfiguration> {
		  
		    @Override
		    public String name() {
		        return "WhiteListSecurity";
		    }
		 
		    @Override
		    public String version() {
		        return "1.0.0";
		    }
		 
		    @Nullable
		    @Override
		    public Object createComponent(PluginContext ctx, Class cls) {
		        if (cls.isAssignableFrom(GridSecurityProcessor.class)) {
		            return new WhiteListSecurityProcessor();
		        } else {
		            return null;
		        }
		    }
		 
		    @Override
		    public IgnitePlugin plugin() {
		        return new WhiteListSecurityProcessor();
		    }
	
	@Override
	public String copyright() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void initExtensions(PluginContext ctx, ExtensionRegistry registry) {
		// TODO Auto-generated method stub
		
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
