package com.eryi.server.connector;

import com.eryi.server.connector.endpoint.BIOEndPoint;
import com.eryi.server.connector.processer.Http11Processer;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/10 22:40
 */
public class ProtocolHandler {
    private BIOEndPoint bioEndPoint;
    public ProtocolHandler(){
        bioEndPoint=new BIOEndPoint();
    }

    public void start(){
        bioEndPoint.start();
    }
}
