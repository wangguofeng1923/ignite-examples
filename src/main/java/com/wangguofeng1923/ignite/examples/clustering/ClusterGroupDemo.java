package com.wangguofeng1923.ignite.examples.clustering;

import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

public class ClusterGroupDemo {
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg=new IgniteConfiguration();
		
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger here.

		igniteCfg.setGridLogger(gridLog);
		
		Ignite ignite=Ignition.start(igniteCfg);
			IgniteCluster cluster = ignite.cluster();
			//all remote nodes
			ClusterGroup remoteGroup = cluster.forRemotes();

			// Cluster group with remote nodes, i.e. other than this node.
			Collection<ClusterNode> grpNodes = remoteGroup.nodes();

			// First node in the group (useful for groups with one node).
			ClusterNode firstNode = remoteGroup.node();

			// And if you know a node ID, get node by ID.
//			UUID myID = ...;

//			node = remoteGroup.node(myId);
			
			//filter all node with attribute 'ROLE' with value 'worker'
			ClusterGroup workers = ignite.cluster().forAttribute("ROLE", "worker");

			Collection<ClusterNode> nodes = workers.nodes();
			
			//get the node who's currentcpu load small than 0.5
			ClusterGroup readyNodes = cluster.forPredicate((node) -> node.metrics().getCurrentCpuLoad() < 0.5);
			
			
			// Group containing oldest node out of remote nodes.
			ClusterGroup oldestGroup = cluster.forRemotes().forOldest();

			ClusterNode oldestNode = oldestGroup.node();
			
			


			// Cluster group metrics.
			ClusterMetrics metrics = remoteGroup.metrics();

			// Get some metric values.
			double cpuLoad = metrics.getCurrentCpuLoad();
			long usedHeap = metrics.getHeapMemoryUsed();
			int numberOfCores = metrics.getTotalCpus();
			int activeJobs = metrics.getCurrentActiveJobs();
		
	}
}
