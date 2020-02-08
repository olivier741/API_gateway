/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.xml;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author olivier.tatsinkou
 */
@Root(name="server")
public class SMSserver {
    @Element(name="query",required = true) 
    private String query;
    
    @Element(name="http_listener",required = true) 
    private Http_Listener http_listerner;
    
    @ElementList(inline=true, name="user_connection" ,required = true)
    private List<User_Connection> listUser_Connection = new ArrayList<User_Connection>();

    public SMSserver(  @Element(name="query",required = true) String query,
                       @Element(name="http_listener" ,required = true)Http_Listener http_listerner,
                       @ElementList(inline=true, name="user_connection" ,required = true) List<User_Connection> listUser_Connection ) {
        this.query=query;
        this.http_listerner = http_listerner;
        this.listUser_Connection = listUser_Connection;
    }

    public String getQuery() {
        return query;
    }

    public Http_Listener getHttp_listerner() {
        return http_listerner;
    }

    public List<User_Connection> getListUser_Connection() {
        return listUser_Connection;
    }

    
     public  User_Connection selectUser_con(String user){
        User_Connection result = null;
        if (listUser_Connection != null){
            for (User_Connection e : listUser_Connection){
                if (e.getUser().equals(user)){
                    result = e;
                    break;
                }
            }
        }
        return result;
    }


}
