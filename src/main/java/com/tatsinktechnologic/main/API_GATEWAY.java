/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.main;

import com.tatsinktechnologic.ws.handler.ServerPasswordCallback;
import com.tatsinktechnologic.ws.interfaces.Sender_SMSInterface;
import com.tatsinktechnologic.ws.process.Sender_SMS;
import com.tatsinktechnologic.xml.Http_Listener;
import com.tatsinktechnologic.xml.SMSserver;
import com.tatsinktechnologic.xml.XML_ConfigLoader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

/**
 *
 * @author olivier.tatsinkou
 */
public class API_GATEWAY {

    /**
     * @param args the command line arguments
     */
    private static Logger logger = Logger.getLogger(API_GATEWAY.class);
    private static XML_ConfigLoader xmlConfig;
    public static void main(String[] args) {
        // TODO code application logic here
        
        PropertyConfigurator.configure("etc" + File.separator + "log4j.cfg");
        System.setProperty("com.mchange.v2.c3p0.cfg.xml", "etc" + File.separator + "database-config.xml");
        logger.info("Load log4j config file done.");
        
       xmlConfig= XML_ConfigLoader.getConfigurationLoader();
       SMSserver smsserver= xmlConfig.getSmsServer();
       Http_Listener http_listener = smsserver.getHttp_listerner();
       
       
       try {
            Sender_SMS send_sms = new Sender_SMS();            
            JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
            svrFactory.setServiceClass(Sender_SMSInterface.class);  
            svrFactory.setAddress("http://"+http_listener.getHttp_IP()+":"+http_listener.getHttp_port()+"/send");
            svrFactory.setServiceBean(send_sms);
            
            Map inProps = new HashMap();
            inProps.put( WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
            
            if (http_listener.getWs_security_mode().equals("0")){
                // Password type : plain text
              inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);  
            }else{
                // for hashed password use:
               inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST); 
            }

            inProps.put( WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName() );

            svrFactory.getInInterceptors().add(new LoggingInInterceptor());
            
            //UsernameToken security headers
            svrFactory.getInInterceptors().add( new WSS4JInInterceptor( inProps ) );
            
            svrFactory.getOutInterceptors().add(new LoggingOutInterceptor());
  
            svrFactory.create();           
            logger.info("%%%%%%%%%%% SUCCESSFULL LOAD WEB SERVICE SERVER %%%%%%%%%%%%%%");
       } catch (Exception e) {
           logger.error("cannot publish webservice :", e);
       
       }
    }
    
}
