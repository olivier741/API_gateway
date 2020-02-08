/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.ws.handler;

import com.tatsinktechnologic.xml.SMSserver;
import com.tatsinktechnologic.xml.User_Connection;
import com.tatsinktechnologic.xml.XML_ConfigLoader;
import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.log4j.Logger;
import org.apache.wss4j.common.ext.WSPasswordCallback;
/**
 *
 * @author olivier.tatsinkou
 */
public class ServerPasswordCallback implements CallbackHandler {
   
    private static Logger logger = Logger.getLogger(ServerPasswordCallback.class);
    private static User_Connection user_conn = null;
    

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        XML_ConfigLoader xmlConfig = XML_ConfigLoader.getConfigurationLoader();
        SMSserver smsserver = xmlConfig.getSmsServer();
        
        
        for (int i = 0; i < callbacks.length; i++) {

            WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];

            if (pc.getUsage() == WSPasswordCallback.USERNAME_TOKEN) {

                //You must set a password for the user, WSS4J would compare
                //the password with the password sent by client, if they match
                //message will be processed. Any mismatch in password will result in a SOAP Fault.

                if (smsserver.selectUser_con(pc.getIdentifier())!=null){
                  user_conn = smsserver.selectUser_con(pc.getIdentifier());
                }

                if(user_conn!= null && pc.getIdentifier().equalsIgnoreCase(user_conn.getUser())) {
                    pc.setPassword(user_conn.getPassword());
                }
                
            }
        }

    }

    public static User_Connection getUser_conn() {
        return user_conn;
    }
    
    
}
