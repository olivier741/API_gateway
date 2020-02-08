/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.model;

import java.util.List;

/**
 *
 * @author olivier.tatsinkou
 */
public class ServiceDetail {
  
  private String wsdl;
  private String nameSpace;
  private String prefix;
  private String serviceName;
  private String portName;
  private List<Operation> operations;


  public ServiceDetail(String wsdl) {

      super();
      this.wsdl = wsdl;

  }
  
  public String getWsdl() {
      return wsdl;
  }

  public void setWsdl(String wsdl) {
      this.wsdl = wsdl;
  }



  public String getNameSpace() {
      return nameSpace;
  }



  public void setNameSpace(String nameSpace) {
      this.nameSpace = nameSpace;
  }

  public String getPrefix() {
      return prefix;
  }

  public void setPrefix(String prefix) {
      this.prefix = prefix;
  }

  public String getServiceName() {
      return serviceName;
  }

  public void setServiceName(String serviceName) {
      this.serviceName = serviceName;
  }

  public String getPortName() {
      return portName;
  }

  public void setPortName(String portName) {
     this.portName = portName;
  }

  public List<Operation> getOperations() {
      return operations;
  }

  public void setOperations(List<Operation> operations) {
      this.operations = operations;
  }

}
