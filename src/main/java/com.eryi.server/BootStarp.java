package com.eryi.server;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description 主启动类
 * @date 2022/1/5 23:24
 */
public class BootStarp {
    public static void main(String[] args) {
        //@todo init

        //start
        MyTomcat myTomcat=new MyTomcat();
        myTomcat.start();
    }
}
