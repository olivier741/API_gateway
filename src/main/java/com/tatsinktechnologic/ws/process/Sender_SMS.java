/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.ws.process;


import com.tatsinktechnologic.bean.Body_Param;
import com.tatsinktechnologic.bean.Header_Param;
import com.tatsinktechnologic.bean.SenderResponse;
import com.tatsinktechnologic.dao.OracleConnexion_Pool;
import com.tatsinktechnologic.ws.handler.ServerPasswordCallback;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.jws.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import com.tatsinktechnologic.ws.interfaces.Sender_SMSInterface;
import com.tatsinktechnologic.xml.SMSserver;
import com.tatsinktechnologic.xml.User_Connection;
import com.tatsinktechnologic.xml.XML_ConfigLoader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.ws.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 *
 * @author olivier.tatsinkou
 */

@WebService(serviceName="Sender_SMS",endpointInterface = "cm.nexttel.noc.ws.interfaces.Sender_SMSInterface")
public class Sender_SMS implements Sender_SMSInterface{
   
     private static  Properties prop = new Properties();
     private static Logger logger = LogManager.getLogger(Sender_SMS.class);
     private User_Connection user_con= null;
     private SMSserver smsserver;
     private XML_ConfigLoader xmlConfig ;
     private OracleConnexion_Pool datbaseConn;
     
    @Resource
    WebServiceContext webServiceContext; 

    public Sender_SMS() {
        this.xmlConfig = XML_ConfigLoader.getConfigurationLoader();
        this.smsserver= xmlConfig.getSmsServer();
        datbaseConn = new OracleConnexion_Pool();
    }

    public SenderResponse send_sms(@WebParam(name = "ws_name") String WS_Name,@WebParam(name = "header_param") List<Header_Param> header_params,@WebParam(name = "body_param") List<Body_Param> body_params){
        SenderResponse resp = null;
        
         for (Header_Param param : header_params) {
                String name = param.getName();
                String value = param.getValue();
                
               logger.info("new soap request header param : "+name+ " value : "+value); 
         }
        
         for (Body_Param param : body_params) {
                String name = param.getName();
                String value = param.getValue();
                
               logger.info("new soap request body param : "+name+ " value : "+value); 
         }
         user_con= ServerPasswordCallback.getUser_conn();
        logger.info("#################### START SEND SMS ############################");
//        if (checkIP(user_con)){
//
//
//            boolean isSend = datbaseConn.Send_SMS_Vas(sender, receiver, content);
//
//            if (isSend){
//                resp = new SenderResponse("SUCCESS_SMS_CONTENT  ",0, "SMS Content have been send successfully");
//                logger.info("SUCCESS_SMS_CONTENT");
//                logger.info("SUCCESS Submition of : "+ sender+ " --> " +receiver +" ( msg ="+content+" )");
//               
//            }else{
//                resp = new SenderResponse("ERROR_DATABASE",-1, "Database issue. cannot send SMS");
//                logger.error("ERROR_SMS_CONTENT");
//                logger.error("Wrong Submition of : "+ sender+ " --> " +receiver +" ( msg ="+content+" )");
//            }
//
//        }else{
//            resp = new SenderResponse("ERROR_HOST_Auth",-2, "Your IP address is deny");
//        }           
//        logger.info("#################### END SEND SMS ############################");
        return resp;
    }
    

     
     private boolean checkIP(User_Connection user_conn) { 
       
        boolean result = false;
        List<String> listIP= Arrays.asList(user_conn.getClient_IP().split(","));       

        MessageContext messageContext = webServiceContext.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST); 
        String callerIpAddress = request.getRemoteAddr();

        logger.info("Client IP Address = " + callerIpAddress); 

        if (listIP.contains(callerIpAddress)){
            result= true;
        }else if (IPAddress.isInSubnet(callerIpAddress, user_conn.getSub_net(), user_conn.getMask())){
            result = true;
        }
        return result;
   }
     
}

