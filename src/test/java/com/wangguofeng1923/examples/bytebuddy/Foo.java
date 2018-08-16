package com.wangguofeng1923.examples.bytebuddy;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.ignite.plugin.PluginProvider;

public class Foo {
	public String sayHelloFoo() { 
	    return "Hello in Foo!"; 
	}
	public static String sayHello() { 
		 return AccessController.doPrivileged(new PrivilegedAction<String>() {
	            @Override public String run() {
	               

	                return "haha";
	            }
	        });
	}
}
