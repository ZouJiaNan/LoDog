package com.eryi.server.servlet;

import com.eryi.server.connector.Bean.Request;
import com.eryi.server.connector.Bean.Response;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description servlet规范顶级父接口
 * @date 2022/1/18 22:31
 */
public interface Servlet{
    public Response doGet(Request request, Response response);
    public Response doPost(Request request, Response response);
}
