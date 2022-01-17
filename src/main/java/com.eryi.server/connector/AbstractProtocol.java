package com.eryi.server.connector;

import com.eryi.server.connector.processer.Http11Processer;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/10 23:30
 */
public  class AbstractProtocol {
    private Http11Processer http11Processer;
    public void start(){
        http11Processer=new Http11Processer();
    }

    protected Http11Processer getProcesser(){
        return this.http11Processer;
    }
}
