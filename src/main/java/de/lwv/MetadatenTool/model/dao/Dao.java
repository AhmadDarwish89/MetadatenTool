/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.de.lwv.MetadatenTool.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ahmad
 */
public class Dao {
    public  Connection getConnection() throws Exception{
       Class.forName("com.mysql.jdbc.Driver");
        Connection con =DriverManager.getConnection("jdbc:oracle:thin:@srvkora08.lwv-hessen.local:1522:LWVEDEV1");

       if(con!=null){
           System.out.println("Sucsess");
           return con;

       }
        System.out.println("faild");
           return null;
    }

  public static void closeConnection(Connection con) throws Exception {
      if(con!=null){
          con.close();
          
         }
          
     }
  }
   

