package com.eryi.server.connector.endpoint;

import com.eryi.server.connector.AbstractProtocol;
import com.eryi.server.connector.Connector;
import com.eryi.server.connector.processer.Http11Processer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/5 23:37
 */
public class BIOEndPoint extends AbstractProtocol {
    private int port=9999;

    public BIOEndPoint(){

    }

    public BIOEndPoint(int port){
        this.port=port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void start(){
        super.start();
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("my tomcat started on "+port);
            while(true){
                //接收请求
                Socket socket=serverSocket.accept();
                getProcesser().process(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
