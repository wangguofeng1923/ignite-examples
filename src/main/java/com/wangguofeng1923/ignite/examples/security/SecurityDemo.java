package com.wangguofeng1923.ignite.examples.security;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.apache.ignite.plugin.security.SecurityCredentialsBasicProvider;

public class SecurityDemo {

	public static void main(String[] args) {
		
		IgniteConfiguration igniteCfg=new IgniteConfiguration();
		Slf4jLogger gridLog = new Slf4jLogger(); // Provide correct SLF4J logger here.
		igniteCfg.setGridName(String.valueOf(System.currentTimeMillis()));

		igniteCfg.setGridLogger(gridLog);
		igniteCfg.setDeploymentMode(DeploymentMode.CONTINUOUS);
//		Map<String,Object>userAttrs=new HashMap<>();
//		userAttrs.put("group", "mygroup");
//		igniteCfg.setUserAttributes(userAttrs);
//		CacheConfiguration<String, String>cacheConfiguration=new CacheConfiguration<>("cache");
//		cacheConfiguration.setPluginConfigurations(new MyPluginConfiguration());
//		igniteCfg.setCacheConfiguration(cacheConfiguration);
//		igniteCfg.setPluginConfigurations(new WhiteListPluginConfiguration());
		
//	GridGainConfiguration gainConfiguration=new GridGainConfiguration();
	//Create security credentials.
	SecurityCredentials creds = new SecurityCredentials("username"+System.currentTimeMillis(), "password");
	//Create basic security provider.
	SecurityCredentialsBasicProvider provider = new SecurityCredentialsBasicProvider(creds);
//	gainConfiguration.setSecurityCredentialsProvider(provider);
	//final SecurityCredentials credentials=new SecurityCredentials("login", "password");
	//AuthenticationAclProvider basicProvider=new MyAuthenticationAclProvider(credentials);
	//PasscodeAuthenticator auth=new PasscodeAuthenticator();
	//auth.setAclProvider(basicProvider);
	//gainConfiguration.setAuthenticator(auth);
	//SecurityCredentialsProvider securityCredentialsProvider=new MySecurityCredentialsProvider(credentials);
	//gainConfiguration.setSecurityCredentialsProvider(securityCredentialsProvider);
//	igniteCfg.setPluginConfigurations(gainConfiguration);
			Ignite ignite=Ignition.start(igniteCfg);

			IgniteCluster cluster = ignite.cluster();
	
			System.out.println(cluster.nodes().size());
	}
}
