package com.wangguofeng1923.ignite.examples.clustering;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class NodeMetrics {
public static void main(String[] args) {
	IgniteConfiguration igniteCfg=new IgniteConfiguration();
	
	Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger here.

	igniteCfg.setGridLogger(gridLog);
	
	try (	Ignite ignite=Ignition.start(igniteCfg);) {
		IgniteCluster cluster = ignite.cluster();
		
		//Local Cluster Node
		ClusterNode localNode = cluster.localNode();

		// Node metrics.
		ClusterMetrics metrics = localNode.metrics();
		
		// Get some metric values.
		double cpuLoad = metrics.getCurrentCpuLoad();
		long usedHeap = metrics.getHeapMemoryUsed();
		int numberOfCores = metrics.getTotalCpus();
		int activeJobs = metrics.getCurrentActiveJobs();
	}
}
}
