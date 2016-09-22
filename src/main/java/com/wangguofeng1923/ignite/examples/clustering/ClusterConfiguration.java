package com.wangguofeng1923.ignite.examples.clustering;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.DiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

public class ClusterConfiguration {
	private static DiscoverySpi buildDiscoverySpi(){
		TcpDiscoverySpi discoSpi=new TcpDiscoverySpi();
		
		TcpDiscoveryMulticastIpFinder ipFinder=new TcpDiscoveryMulticastIpFinder();
//		ipFinder.setMulticastGroup("228.10.10.157");
		ipFinder.setMulticastPort(25);
		discoSpi.setIpFinder(ipFinder);
		return discoSpi;
	}
	public static void main(String[] args) {
		IgniteConfiguration igniteCfg = new IgniteConfiguration();
		igniteCfg.setDiscoverySpi(buildDiscoverySpi());
		
		
		
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger

		igniteCfg.setGridLogger(gridLog);

		try (Ignite ignite = Ignition.start(igniteCfg);) {
			IgniteCluster cluster = ignite.cluster();

		}
	}
}
