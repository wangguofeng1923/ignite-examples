package com.wangguofeng1923.ignite.examples.security.plugin;

import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.processors.security.GridSecurityProcessor;
import org.apache.ignite.internal.processors.security.os.GridOsSecurityProcessor;

public class SecurityProcessor extends GridOsSecurityProcessor{

	public SecurityProcessor(GridKernalContext ctx) {
		super(ctx);
	}

}
