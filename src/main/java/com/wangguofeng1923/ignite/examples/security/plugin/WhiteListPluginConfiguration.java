package com.wangguofeng1923.ignite.examples.security.plugin;

import org.apache.ignite.plugin.PluginConfiguration;
import org.apache.ignite.plugin.PluginProvider;

public class WhiteListPluginConfiguration implements PluginConfiguration {

    public Class<? extends PluginProvider> providerClass() {
        return WhiteListPluginProvider.class;
    }
}