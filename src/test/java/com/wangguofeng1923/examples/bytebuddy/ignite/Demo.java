package com.wangguofeng1923.examples.bytebuddy.ignite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.plugin.PluginProvider;

import com.wangguofeng1923.examples.bytebuddy.Holder;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
public class Demo {
	

public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException {
	ByteBuddyAgent.install();
	
	new ByteBuddy().with(Implementation.Context.Disabled.Factory.INSTANCE)
		  .redefine(org.apache.ignite.internal.util.IgniteUtils.class)
		  .method(ElementMatchers.named("allPluginProviders"))
		  .intercept(MethodDelegation.to(Holder.class))
		  .make()
		  .load(Demo.class.getClassLoader(), 
				    ClassReloadingStrategy.fromInstalledAgent());
	
//	new ByteBuddy()
//	  .redefine(Foo.class)
//	  .method(ElementMatchers.named("sayHello"))
//	  .intercept(FixedValue.value("Hello Foo Redefined"))
//	  .make()
//	  .load(
//	    Foo.class.getClassLoader(), 
//	    ClassReloadingStrategy.fromInstalledAgent());
//	   
//	Foo f = new Foo();
//	  System.out.println(Foo.sayHello());
	  
	  
IgniteConfiguration igniteCfg=new IgniteConfiguration();
	
	Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger here.

	igniteCfg.setGridLogger(gridLog);
	
	try (	Ignite ignite=Ignition.start(igniteCfg);) {

	IgniteCluster cluster = ignite.cluster();

	// Compute instance over remote nodes.
//	IgniteCompute compute = ignite.compute(cluster.forRemotes());
	IgniteCompute compute = ignite.compute();
	// Print hello message on all remote nodes.
	compute.broadcast(() -> System.out.println("Hello node: " + cluster.localNode().id()));
	

		  Collection<IgniteCallable<Integer>> calls = new ArrayList<>();

		  // Iterate through all the words in the sentence and create Callable jobs.
		  for (final String word : "Count characters using callable".split(" "))
		    calls.add(word::length);

		  // Execute collection of Callables on the grid.
		  Collection<Integer> res = ignite.compute().call(calls);

		  // Add up all the results.
		  int sum = res.stream().mapToInt(Integer::intValue).sum();
		 
		  System.out.println("Total number of characters is '" + sum + "'.");
		}
	
	

}
}
