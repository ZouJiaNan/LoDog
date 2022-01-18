package com.eryi.server.servlet;

import com.eryi.server.connector.Bean.Request;
import com.eryi.server.connector.Bean.Response;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description 测试servlet
 * @date 2022/1/18 22:34
 */
public class testServlet implements Servlet{
    public Response doGet(Request request, Response response) {
        response.setParam("testSuccess");
        return response;
    }

    public Response doPost(Request request, Response response) {
        return null;
    }
}
