package com.wangguofeng1923.ignite.examples.security.plugin;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.IgniteInternalFuture;
import org.apache.ignite.internal.processors.security.GridSecurityProcessor;
import org.apache.ignite.internal.processors.security.SecurityContext;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.security.AuthenticationContext;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.apache.ignite.plugin.security.SecurityException;
import org.apache.ignite.plugin.security.SecurityPermission;
import org.apache.ignite.plugin.security.SecurityPermissionSet;
import org.apache.ignite.plugin.security.SecuritySubject;
import org.apache.ignite.plugin.security.SecuritySubjectType;
import org.apache.ignite.spi.IgniteNodeValidationResult;
import org.apache.ignite.spi.discovery.DiscoverySpiNodeAuthenticator;

public class WhiteListSecurityProcessor
                          implements DiscoverySpiNodeAuthenticator, 
                                     GridSecurityProcessor, 
                                     IgnitePlugin {
                 
    //the hosts that will be allowed to join the cluster
    private Set<String> whitelist = new HashSet<>();
 
//    private boolean isAddressOk(Collection<String> addresses) {
//        //return true if the address is in the whitelist
//    }
    
	@Override
	public SecurityContext authenticateNode(ClusterNode node, SecurityCredentials cred) throws IgniteException {
		new SecurityContext(){

			@Override
			public SecuritySubject subject() {
				// TODO Auto-generated method stub
				return new SecuritySubject(){

					@Override
					public UUID id() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public SecuritySubjectType type() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Object login() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public InetSocketAddress address() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public SecurityPermissionSet permissions() {
						
						return new SecurityPermissionSet() {
							
							@Override
							public Map<String, Collection<SecurityPermission>> taskPermissions() {
								// TODO Auto-generated method stub
								return null;
							}
							
							@Override
							public Collection<SecurityPermission> systemPermissions() {
								// TODO Auto-generated method stub
								return null;
							}
							
							@Override
							public boolean defaultAllowAll() {
								// TODO Auto-generated method stub
								return false;
							}
							
							@Override
							public Map<String, Collection<SecurityPermission>> cachePermissions() {
								// TODO Auto-generated method stub
								return null;
							}
						};
					}};
			}

			@Override
			public boolean taskOperationAllowed(String taskClsName, SecurityPermission perm) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean cacheOperationAllowed(String cacheName, SecurityPermission perm) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean systemOperationAllowed(SecurityPermission perm) {
				// TODO Auto-generated method stub
				return false;
			}};
		return null;
	}
	@Override
	public IgniteNodeValidationResult validateNode(ClusterNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	
   /* @Override
    public SecurityCredentials authenticateNode(ClusterNode node, 
                                                SecurityCredentials cred) 
                                                throws IgniteException {
 
        return new GridSecurityContext(new GridSecuritySubject() {
             
            @Override
            public GridSecurityPermissionSet permissions() {
                if (isAddressOk(node.addresses())) {
                    return WhiteListPermissionSets.ALLOW_ALL;
                } else {
                    return WhiteListPermissionSets.ALLOW_NONE;
                }
            }
 
            //all other methods are noop
 
        });
    }
    @Nullable
    @Override
    public IgniteSpiNodeValidationResult validateNode(ClusterNode node) {
        if (!isAddressOk(node.addresses())) {
            return new IgniteSpiNodeValidationResult(node.id(), 
                                                     "Access denied", 
                                                     "Access denied");
        } else {
            return null;
        }
    }*/
    @Override
    public boolean isGlobalNodeAuthentication() {
        //allow any node to perform the authentication
        return true;
    }
 
    @Override
    public void start() throws IgniteCheckedException {
        //load the whitelist 
        //check that this process is running on a white listed server
        //if there's a problem throw new IgniteCheckedException
    }
 
    


	@Override
	public void stop(boolean cancel) throws IgniteCheckedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKernalStart() throws IgniteCheckedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKernalStop(boolean cancel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable collectDiscoveryData(UUID nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDiscoveryDataReceived(UUID joiningNodeId, UUID rmtNodeId, Serializable data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printMemoryStats() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public DiscoveryDataExchangeType discoveryDataType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDisconnected(IgniteFuture<?> reconnectFut) throws IgniteCheckedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IgniteInternalFuture<?> onReconnected(boolean clusterRestarted) throws IgniteCheckedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityContext authenticate(AuthenticationContext ctx) throws IgniteCheckedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SecuritySubject> authenticatedSubjects() throws IgniteCheckedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecuritySubject authenticatedSubject(UUID subjId) throws IgniteCheckedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void authorize(String name, SecurityPermission perm, SecurityContext securityCtx) throws SecurityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSessionExpired(UUID subjId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean enabled() {
		// TODO Auto-generated method stub
		return false;
	}



  
 
    //all other methods are noop
 
}