package com.eryi.server.connector;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/5 23:36
 */
public class Connector {
    private ProtocolHandler protocolHandler;
    public Connector(){
        protocolHandler=new ProtocolHandler();
    }

    public void start(){
        protocolHandler.start();
    }
}
