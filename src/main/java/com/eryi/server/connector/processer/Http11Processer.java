package com.eryi.server.connector.processer;


import com.eryi.server.config.ServletConfig;
import com.eryi.server.config.ServletConfigMapping;
import com.eryi.server.connector.Bean.Request;
import com.eryi.server.connector.Bean.Response;
import com.eryi.server.servlet.Servlet;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description http1.1 processer
 * @date 2022/1/5 23:25
 */
public class Http11Processer {

    private Request request;

    public Http11Processer(){
        this.request=new Request();
    }

    public String processHttpResponseContext(int code,String content,String message){
        if(code==200){
            return "HTTP/1.1 200 OK \n"+
                    "Content-Type: text/html\n"+
                    "\r\n"+content;
        }
        if(code==500){
            return "HTTP/1.1 500 Internal Error="+message+"\n"+
                    "Content-Type: text/html\n"+
                    "\r\n";
        }
        return null;
    }

    /**
     * 拆包传输层协议
     * @param socket
     */
    public void process(Socket socket){
        InputStream inputStream= null;
        try {
            inputStream = socket.getInputStream();
            //1.拆包
            int count=0;
            //收到的包，有可能是空包，
            while(count==0){
                //网络具有间断性，一块完整的内容（一段流）可能分成几段来发送
                // inputStream.available()用于计算一段流里面总共有多少内容，结果是累加的，分成几段就累加几次
                count=inputStream.available();
            }
            byte[] buffer=new byte[count];
            //2.解析http请求，封装为Request
            inputStream.read(buffer);
            String header=new String(buffer).split("\\n")[0];
            String[] headers=header.split("\\s");
            //2.1解析方法
            this.request.setMethods(headers[0]);
            //2.2解析URL
            this.request.setUrl(headers[1]);

            //3.返回响应
            Response response=createResponse(request.getUrl());
            socket.getOutputStream().write(new Http11Processer().processHttpResponseContext(200,response.getParam(),"success").getBytes());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理请求，返回响应
     */
    private Response createResponse(String fileName){
        Response response=new Response();
        //1.欢迎页
        if("/".equals(fileName)){
            response.setParam(readStaticFile("/index.html"));
        }
        //2.静态资源请求
        if(fileName.contains(".")){
            response.setParam(readStaticFile(fileName));
        }
        //3.动态资源请求（servlet）
        if(response.getParam()==null){
            response.setParam(readDynamicFile(fileName,request,response));
        }
        //4.404页面
        if(response.getParam()==null){
            response.setParam(readStaticFile("/404.html"));
        }

        return response;
    }

    private String readStaticFile(String fileName){
        URL url = this.getClass().getResource(fileName);
        if(url==null){
            return null;
        }
        String path = url.getPath();
        File file = new File(path);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return sbf.toString();
    }

    private String readDynamicFile(String url,Request request,Response response){
        String param="";
        try {
            Map<String,ServletConfig> configs=ServletConfigMapping.getConfigs();
            //解析URL
            String[] urlParams=url.split("/");
            String prefix="/"+urlParams[1];
            //根据URL前缀获取对应servlet
            ServletConfig servletConfig=configs.get(prefix);
            if(servletConfig==null){
                return null;
            }
            Class clazz=Class.forName(servletConfig.getClazz());
            Object[] params={request,response};
            //调用servlet对应方法处理请求
            Method method=null;
            if("GET".equals(request.getMethods())){
                method=clazz.getMethod("doGet",Request.class,Response.class);
            }
            if("POST".equals(request.getMethods())){
                method=clazz.getMethod("doPost",Request.class,Response.class);
            }
            if(method!=null) {
                Object object= method.invoke(clazz.newInstance(), params);
                param=object==null?null:((Response)object).getParam();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

}
