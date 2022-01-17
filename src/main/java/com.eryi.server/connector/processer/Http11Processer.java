package com.eryi.server.connector.processer;


import com.eryi.server.connector.Bean.Request;
import com.eryi.server.connector.Bean.Response;

import java.io.*;
import java.net.Socket;
import java.net.URL;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description http1.1 processer
 * @date 2022/1/5 23:25
 */
public class Http11Processer {

    private Request request;
    private Response response;

    public Http11Processer(){
        this.request=new Request();
        this.response=new Response();
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

            //返回响应
            String content=readFile(request.getUrl());
            socket.getOutputStream().write(new Http11Processer().processHttpResponseContext(200,content,"success").getBytes());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     */
    private String readFile(String fileName){
        URL url = this.getClass().getResource(fileName);
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
            return sbf.toString();
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

}
