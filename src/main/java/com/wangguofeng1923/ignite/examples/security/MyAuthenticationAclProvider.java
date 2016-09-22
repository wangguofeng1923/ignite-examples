package com.wangguofeng1923.ignite.examples.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ignite.IgniteException;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.apache.ignite.plugin.security.SecurityPermission;
import org.apache.ignite.plugin.security.SecurityPermissionSet;
import org.gridgain.grid.security.passcode.AuthenticationAclProvider;

public class MyAuthenticationAclProvider implements AuthenticationAclProvider,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SecurityCredentials credentials;
	public MyAuthenticationAclProvider(SecurityCredentials credentials){
		this.credentials=credentials;
		
	}

	@Override
	public Map<SecurityCredentials, SecurityPermissionSet> acl() throws IgniteException {
	
		SecurityPermissionSet set=new SecurityPermissionSet(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean defaultAllowAll() {
				return false;
			}

			@Override
			public Map<String, Collection<SecurityPermission>> taskPermissions() {
				Map<String, Collection<SecurityPermission>>map=new HashMap<>();
				return map;
			}

			@Override
			public Map<String, Collection<SecurityPermission>> cachePermissions() {
				Map<String, Collection<SecurityPermission>>map=new HashMap<>();
				return map;
			}

			@Override
			public Collection<SecurityPermission> systemPermissions() {
				Collection<SecurityPermission>coll=new ArrayList<>();
				return coll;
			}};
			 Map<SecurityCredentials, SecurityPermissionSet> resultMap=new HashMap<>();
			 resultMap.put(credentials, set);
		return resultMap;
	}};