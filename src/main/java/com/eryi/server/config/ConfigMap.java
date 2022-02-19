package com.eryi.server.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/2/19 17:30
 */
public class ConfigMap {
    private static Map<String,String> kvMap=new HashMap<String, String>();

    static{
        try {
            FileInputStream fis = new FileInputStream("D:\\LogDog\\server.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while((line=br.readLine())!=null){
                String[] KVS=line.split("=");
                if(KVS.length>=2){
                    kvMap.put(KVS[0],KVS[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return kvMap.get(key);
    }
}
