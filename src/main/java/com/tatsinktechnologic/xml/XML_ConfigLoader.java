/*
 * Copyright 2018 olivier.tatsinkou.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tatsinktechnologic.xml;


import java.io.File;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author olivier.tatsinkou
 */
public class XML_ConfigLoader {
      private static Logger logger = Logger.getLogger(XML_ConfigLoader.class);
     
     private SMSserver smsServer;

    private XML_ConfigLoader() {
        
        File sms_listener = new File("etc" + File.separator + "serverConf.xml");
   
        Serializer serializer_sms_listener = new Persister();
        try {
            smsServer = serializer_sms_listener.read(SMSserver.class, sms_listener);
            logger.info("successfull load : etc" + File.separator + "serverConf.xml");
        } catch (Exception e) {
            logger.error("ERROR in config file serverConf.xml",e);
        }

    }
    
    
    private static class SingletonXMLConfig{
        private static final XML_ConfigLoader _xmlconfigLoad = new XML_ConfigLoader();
    }
    
    public static XML_ConfigLoader getConfigurationLoader(){
        return SingletonXMLConfig._xmlconfigLoad;
    }

    public SMSserver getSmsServer() {
        return smsServer;
    }

    
    
}
