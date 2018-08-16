package com.wangguofeng1923.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteInClosure;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public static void main( String[]args ) throws Exception
    {
    	DataRegionConfiguration dataRegionConfiguration=new DataRegionConfiguration();
    	dataRegionConfiguration.setName("demo-region");
    	dataRegionConfiguration.setPersistenceEnabled(true);
    	
    	DataStorageConfiguration dataStorageConfiguration=new DataStorageConfiguration();
    	dataStorageConfiguration.setDataRegionConfigurations(dataRegionConfiguration);
    	

    	CacheConfiguration<String, String>cacheCfg=new CacheConfiguration<String, String>();
    	cacheCfg.setDataRegionName("demo-region");
    	cacheCfg.setName("demo-cache");
    	
    	IgniteConfiguration  igniteCfg=new IgniteConfiguration();
    	//cfg.setAuthenticationEnabled(true);
    	igniteCfg.setCacheConfiguration(cacheCfg);
    	igniteCfg.setDataStorageConfiguration(dataStorageConfiguration);
    	
    	igniteCfg.setGridLogger(new Slf4jLogger());
    	igniteCfg.setActiveOnStart(true);
    	igniteCfg.setDeploymentMode(DeploymentMode.CONTINUOUS);
    	igniteCfg.setPluginConfigurations(new DemoPluginConfiguration());
    	igniteCfg.setWarmupClosure(new IgniteInClosure<IgniteConfiguration>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void apply(IgniteConfiguration e) {
				System.out.println("WarmupClosure");
				
			}
		});
        try(Ignite ignite=Ignition.start(igniteCfg);){
        	if(!ignite.cluster().active()) {
        		ignite.cluster().active(true);
        	}
        	Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

        	// Open JDBC connection
        	Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");

        	// Create database tables.
        	try (Statement stmt = conn.createStatement()) {

        	    // Create table based on REPLICATED template.
        	    stmt.executeUpdate("CREATE TABLE City (" + 
        	    " id LONG PRIMARY KEY, name VARCHAR) " +
        	    " WITH \"template=replicated\"");

        	    // Create table based on PARTITIONED template with one backup.
        	    stmt.executeUpdate("CREATE TABLE Person (" +
        	    " id LONG, name VARCHAR, city_id LONG, " +
        	    " PRIMARY KEY (id, city_id)) " +
        	    " WITH \"backups=1, affinityKey=city_id\"");
        	  
        	    // Create an index on the City table.
        	    stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");

        	    // Create an index on the Person table.
        	    stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
        	}
        	
        	// Populate City table
        	try (PreparedStatement stmt =
        	conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)")) {

        	    stmt.setLong(1, 1L);
        	    stmt.setString(2, "Forest Hill");
        	    stmt.executeUpdate();

        	    stmt.setLong(1, 2L);
        	    stmt.setString(2, "Denver");
        	    stmt.executeUpdate();

        	    stmt.setLong(1, 3L);
        	    stmt.setString(2, "St. Petersburg");
        	    stmt.executeUpdate();
        	}

        	// Populate Person table
        	try (PreparedStatement stmt =
        	conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)")) {

        	    stmt.setLong(1, 1L);
        	    stmt.setString(2, "John Doe");
        	    stmt.setLong(3, 3L);
        	    stmt.executeUpdate();

        	    stmt.setLong(1, 2L);
        	    stmt.setString(2, "Jane Roe");
        	    stmt.setLong(3, 2L);
        	    stmt.executeUpdate();

        	    stmt.setLong(1, 3L);
        	    stmt.setString(2, "Mary Major");
        	    stmt.setLong(3, 1L);
        	    stmt.executeUpdate();

        	    stmt.setLong(1, 4L);
        	    stmt.setString(2, "Richard Miles");
        	    stmt.setLong(3, 2L);
        	    stmt.executeUpdate();
        	}
        	
        	// Get data
        	try (Statement stmt = conn.createStatement()) {
        	    try (ResultSet rs =
        	    stmt.executeQuery("SELECT p.name, c.name " +
        	    " FROM Person p, City c " +
        	    " WHERE p.city_id = c.id")) {

        	      while (rs.next())
        	         System.out.println(rs.getString(1) + ", " + rs.getString(2));
        	    }
        	}
        }
      
    }

}
