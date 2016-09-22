package com.wangguofeng1923.examples.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

public class LoggerStartupListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {  
  
    private static final String DEFAULT_LOG_FILE = "MYAPP";  
  
    private boolean started = false;  
      
    @Override  
    public void start() {  
        if( started )  
            return;  
//  
//        String userHome = System.getProperty( "user.home" );  
//  
//        String logFile = System.getProperty( "log.file" ); // log.file is our custom jvm parameter to change log file name dynamicly if needed  
//  
//        logFile = ( logFile != null && logFile.length() > 0 ) ? logFile : DEFAULT_LOG_FILE;  
//  
        Context context = getContext();  
//  
//        context.putProperty( "MY_HOME", userHome );  
//        context.putProperty( "LOG_FILE", logFile );  
        LoggerContext loggerContext=(LoggerContext)context;
        loggerContext.getFrameworkPackages().add("org.apache.ignite.logger.slf4j");
        loggerContext.getFrameworkPackages().add("org.apache.ignite.internal.GridLoggerProxy");
        loggerContext.getFrameworkPackages().add("org.apache.ignite.internal.util.IgniteUtils");
        loggerContext.getFrameworkPackages().add("com.atomikos.logging.Slf4jLogger");
        started = true;  
    }  
  
    @Override  
    public void stop() {}  
  
    @Override  
    public boolean isStarted() {  
        return started;  
    }  
  
    @Override  
    public boolean isResetResistant() {  
        return true;  
    }  
  
    @Override  
    public void onStart( LoggerContext context ) {}  
  
    @Override  
    public void onReset( LoggerContext context ) {}  
  
    @Override  
    public void onStop( LoggerContext context ) {}

	@Override
	public void onLevelChange(Logger var1, Level level) {
		// TODO Auto-generated method stub
		
	}  
  

  
  
      
  
}  