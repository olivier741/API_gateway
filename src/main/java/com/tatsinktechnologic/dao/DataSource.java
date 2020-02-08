/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao;

/**
 *
 * @author olivier.tatsinkou
 */

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import java.io.File;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.Loader;


public class DataSource {

    private  DataSource datasource;
    private  ComboPooledDataSource cpds;
    private String  databaseConf;
    private String sourceName;
    
    private static Logger logger = Logger.getLogger(DataSource.class);
    

    public DataSource(String source_name) throws IOException, SQLException, PropertyVetoException {
        this.databaseConf=databaseConf; 
        this.sourceName = source_name;
        
        cpds = new ComboPooledDataSource(sourceName);
        
       
        logger.info("user ="+ cpds.getUser());
        logger.info("test_table ="+ cpds.getAutomaticTestTable());
        logger.info("url ="+ cpds.getJdbcUrl());
        logger.info("password ="+ cpds.getPassword());
        logger.info("driver ="+ cpds.getDriverClass());
    }

    public DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
             
        if (datasource == null) {
            datasource = new DataSource(sourceName);
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {
        
        return this.cpds.getConnection();
    }

    public  void closeConnection(DataSource ds) throws SQLException{
         if ( ds instanceof PooledDataSource){
              cpds.close();
        }else{
           logger.info("this datasource already close");
         }     
     }
   
  
}
