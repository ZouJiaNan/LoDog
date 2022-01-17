package com.eryi.server;

import com.eryi.server.connector.Connector;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/5 23:16
 */
public class MyTomcat {
    private Connector connector;
    public void start(){
        connector=new Connector();
        connector.start();
    }
}
