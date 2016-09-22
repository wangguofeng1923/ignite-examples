package com.wangguofeng1923.ignite.examples;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class Demo {
public static void main(String[] args) {
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
