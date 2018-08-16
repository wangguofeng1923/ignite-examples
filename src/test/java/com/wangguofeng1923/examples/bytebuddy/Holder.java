package com.wangguofeng1923.examples.bytebuddy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.plugin.CachePluginContext;
import org.apache.ignite.plugin.CachePluginProvider;
import org.apache.ignite.plugin.ExtensionRegistry;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.PluginConfiguration;
import org.apache.ignite.plugin.PluginContext;
import org.apache.ignite.plugin.PluginProvider;
import org.apache.ignite.plugin.PluginValidationException;

import com.wangguofeng1923.examples.bytebuddy.ignite.DemoPluginProvider;

import net.bytebuddy.implementation.bind.annotation.DefaultMethod;


public class Holder { 
     static List<PluginProvider<PluginConfiguration>>  result = new ArrayList<>(); 
     public static List<PluginProvider> intercept() {
    	 List<PluginProvider>  result = new ArrayList<>(); 
    	result.add(new DemoPluginProvider());
    	 return result; 
    	 
     
     } 
} 