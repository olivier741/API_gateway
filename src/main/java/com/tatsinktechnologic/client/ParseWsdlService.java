/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.client;

import com.tatsinktechnologic.model.Operation;
import com.tatsinktechnologic.model.ServiceDetail;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author olivier.tatsinkou
 */
public class ParseWsdlService {
    
    private static final String PORT = "port";
    private static final String SERVICE = "service";
    private static final String COMMENT = "#comment";
    private static final String TARGET_NAMESPACE = "targetNamespace";
    private static final String XML_SCHEMA_ATTR = "http://www.w3.org/2001/XMLSchema";
    private static final String OPERATION = "operation";
    private static final String OPERATION_1 = "wsdl:operation";

 
  public static void main(String[] args) {

                //proxy setting, only if you need
        System.setProperty("http.proxyHost", "proxy.dcu.ie");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("sun.net.client.defaultConnectTimeout", "" + 2000);
        System.setProperty("sun.net.client.defaultReadTimeout", "" + 2000);

        ParseWsdlService m = new ParseWsdlService();
//        String targetNameSpace = "http://microsoft.com/webservices/";
//        String endpointUrl = "http://www50.brinkster.com/vbfacileinpt/np.asmx";
//        QName serviceName = new QName(targetNameSpace, "PrimeNumbers");
//        QName portName = new QName(targetNameSpace, "PrimeNumbersSoap");
//        String SOAPAction = "http://microsoft.com/webservices/GetPrimeNumbers";
//
//        SOAPMessage response;
//        try {
//            response = m.invoke(serviceName, portName, endpointUrl, SOAPAction);
//            if (response.getSOAPBody().hasFault()) {
//                System.out.println(response.getSOAPBody().getFault());
//            } else {
//                System.out.println("ok");
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        
        String wsdlPath = "http://10.124.249.17:8024/ViewWS?wsdl";
        
        try {
           ServiceDetail bookService = m.parse(wsdlPath);
           
            System.out.println( bookService.getNameSpace());
            System.out.println( bookService.getServiceName());
            System.out.println( bookService.getPortName());
            System.out.println( bookService.getOperations().size());
            System.out.println( bookService.getOperations());
        } catch (Exception e) {
        }
       
    }
    
    public SOAPMessage invoke(QName serviceName, QName portName, String endpointUrl, String soapActionUri) throws Exception {
        /** Create a service and add at least one port to it. **/
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endpointUrl);

        /** Create a Dispatch instance from a service.**/
        Dispatch dispatch = service.createDispatch(portName,SOAPMessage.class, Service.Mode.MESSAGE);

        // The soapActionUri is set here. otherwise we get a error on .net based services.
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, new Boolean(true));
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY, soapActionUri);

        /** Create SOAPMessage request. **/
        // compose a request message
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();

        //Create objects for the message parts
        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = envelope.getBody();

        //Populate the Message.  In here, I populate the message from a xml file
        StreamSource preppedMsgSrc = new StreamSource(new FileInputStream("etc"+File.separator+"request.xml"));
        soapPart.setContent(preppedMsgSrc);

        //Save the message
        message.saveChanges();

        System.out.println(message.getSOAPBody().getFirstChild().getTextContent());

        SOAPMessage response = (SOAPMessage) dispatch.invoke(message);

        return response;
    }
    
    
     public ServiceDetail parse(String wsdlPath) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {

        ServiceDetail sd = new ServiceDetail(wsdlPath);

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(readWsdl(wsdlPath));
        removeComments(document, document.getChildNodes());
        String tagPrefix = getNamespacePrefix(document);
        
        if (tagPrefix != null) {
            sd.setPrefix(tagPrefix);
        }
        
        String nameSpace = getTargetNamespace(document);
        sd.setNameSpace(nameSpace);
        
        List operations = getOperations(document);
        sd.setOperations(operations);
        
        String serviceName = getServiceName(document);
        sd.setServiceName(serviceName);
        String portName = getPortName(document);

        sd.setPortName(portName);

        return sd;

 }
     
   private String getServiceName(Document document) {

     String serviceName = null;
     if (document.getElementsByTagName(SERVICE).getLength() > 0) {
         NodeList nodeListOfService = document.getElementsByTagName(SERVICE);
         for (int i = 0; i < nodeListOfService.getLength(); i++) {
             Node portNode = nodeListOfService.item(i).getAttributes().getNamedItem("name");
             serviceName = portNode.getNodeValue();
         }

     }
     return serviceName;

 }
   
   
 private String getPortName(Document document) {

     String serviceName = null;

     if (document.getElementsByTagName(PORT).getLength() > 0) {

         NodeList nodeListOfService = document.getElementsByTagName(PORT);
         
         for (int i = 0; i < nodeListOfService.getLength(); i++) {
             Node portNode = nodeListOfService.item(i).getAttributes().getNamedItem("name");
             serviceName = portNode.getNodeValue();
         }
     }
     return serviceName;
 }
   
   
     
    
   private String getTargetNamespace(Document document) {
        return document.getFirstChild().getAttributes().getNamedItem(TARGET_NAMESPACE).getNodeValue();
     }
    
   private String getNamespacePrefix(Document document) {

     String tagPrefix = null;
     int l = document.getFirstChild().getAttributes().getLength();
     for (int i = 0; i < l; i++) {
         String cmpAttribute = document.getFirstChild().getAttributes().item(i).getNodeValue();
         if (cmpAttribute.equals(XML_SCHEMA_ATTR)) {
             tagPrefix = document.getFirstChild().getAttributes().item(i).getNodeName().replace("xmlns:", "");
         }
     }
     return tagPrefix;

 }
   
    private void removeComments(Document document, NodeList allNodesOfDocumnet) {

     for (int index = 0; index < allNodesOfDocumnet.getLength(); index++) {
         if (document.getFirstChild().getNodeName().equalsIgnoreCase(COMMENT)) {
             document.removeChild(document.getFirstChild());
         }
     }

 }



 private List getOperations(Document document) {

     List operations = new ArrayList();
     NodeList nodeListOfOperations = null;
     if ((document.getElementsByTagName(OPERATION).getLength() > 0) || (document.getElementsByTagName(OPERATION_1).getLength() > 0)) {
         if (document.getElementsByTagName(OPERATION).getLength() > 0) {
             nodeListOfOperations = document.getElementsByTagName(OPERATION);
         } else if (document.getElementsByTagName(OPERATION_1).getLength() > 0) {
             nodeListOfOperations = document.getElementsByTagName(OPERATION_1);
         }
     }
     
     for (int i = 0; i < nodeListOfOperations.getLength(); i++) {
         Operation ope = new Operation(nodeListOfOperations.item(i).getAttributes().getNamedItem("name").getNodeValue());
         Node paraOrder = nodeListOfOperations.item(i).getAttributes().getNamedItem("parameterOrder");
         if (paraOrder != null) {
             ope.setParameterNames(Arrays.asList(paraOrder.getNodeValue().split(" ")));
         } else {
             ope.setParameterNames(Arrays.asList("number".split(",")));
         }

         if (!operations.contains(ope)) {
             operations.add(ope);
         }
     }
     return operations;
 }

 private InputStream readWsdl(String wsdlUrl) throws IOException {
     URL url = new URL(wsdlUrl);
     URLConnection uc = url.openConnection();
     return uc.getInputStream();
 }

}
