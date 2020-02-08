/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author olivier.tatsinkou
 */
@XmlRootElement(name = "body_param") 
@XmlAccessorType(XmlAccessType.FIELD)
public class Body_Param {
    @XmlAttribute
    private String name;
    
    @XmlAttribute
    private String value;

    public Body_Param() {
    }

    public Body_Param(String name, String value) {
        this.name = name;
        this.value = value;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}
