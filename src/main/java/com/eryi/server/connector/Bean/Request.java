package com.eryi.server.connector.Bean;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/13 22:32
 */
public class Request {
    private String url;
    private String methods;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }
}
