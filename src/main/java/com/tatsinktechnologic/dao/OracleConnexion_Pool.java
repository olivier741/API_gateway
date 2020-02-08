/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao;

import com.tatsinktechnologic.xml.SMSserver;
import com.tatsinktechnologic.xml.XML_ConfigLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;



/**
 *
 * @author olivier.tatsinkou
 */
public class OracleConnexion_Pool {
    
   
    private static DataSource datasource;  
    private  Connection  connect;
    private PreparedStatement preparedStatement ;

    private static Logger logger = Logger.getLogger(OracleConnexion_Pool.class);
    

    private String SQL_INSERT_MT = " INSERT INTO mt (mt_id, mo_his_id, msisdn, MESSAGE,receive_time,retry_num,channel) \n"
                                         + "  VALUES  (mt_seq.NEXTVAL,1 , ?, ?,SYSDATE,0,?)";
    
     private static XML_ConfigLoader xmlConfig;
     private static SMSserver smsserver;
    

    //manage pool connexion
    public OracleConnexion_Pool() {
           xmlConfig= XML_ConfigLoader.getConfigurationLoader();
           smsserver= xmlConfig.getSmsServer();
           SQL_INSERT_MT= smsserver.getQuery();
           
             try {
                datasource= new DataSource("dbMonitor"); 
            } catch (Exception e) {
                logger.error("cannot create dataSource :",e);
            }

    }
    
    
    public boolean testConnection(){
        boolean result = false;
        try {
            connect =datasource.getInstance().getConnection();
            if (connect.isValid(100)) result = true;
         } catch (Exception e) {
             logger.error("SQL error :",e); 
        }
         
        return result;
    }

    public boolean Send_SMS_Vas(String channel, String Msisdn,String message)  {

        boolean result = false;
      
        try{
            connect = null;
            preparedStatement = null;

            logger.info("connection on SUPPORT_APP  to send SMS");
            connect =datasource.getInstance().getConnection();

              
            preparedStatement = connect.prepareStatement(SQL_INSERT_MT);
            preparedStatement.setString(1,Msisdn.trim());
            preparedStatement.setString(2, message);
            preparedStatement.setString(3, channel);
            preparedStatement.executeQuery();

            logger.info("Succes Send the error  : "+message+" : To "+Msisdn.trim());  

            result = true;
          logger.info("YOU HAVE SUCCESSFULLY SEND ALL MESSAGE");    
      } catch (Exception e) {
             logger.error("SQL error :",e); 
        } finally {
            if (preparedStatement != null) 
               try { 
                   preparedStatement.close(); 
               } catch (SQLException e) 
               {
                    logger.error("cannot close callableStatement :",e); 
               }
            if (connect != null) 
                try { 
                    connect.close(); 
                } catch (SQLException e) {
                   logger.error("cannot close connection1 :",e);
                }
        }

        return result;
      }
    
    
    

}