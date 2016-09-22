package com.wangguofeng1923.ignite.examples.security;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.apache.ignite.plugin.security.SecurityCredentialsProvider;

public class MySecurityCredentialsProvider implements SecurityCredentialsProvider {
	private SecurityCredentials credentials;
	public MySecurityCredentialsProvider(SecurityCredentials credentials){
		this.credentials=credentials;
		
	}
	@Override
	public SecurityCredentials credentials() throws IgniteCheckedException {
	
		return credentials;
	}
};
