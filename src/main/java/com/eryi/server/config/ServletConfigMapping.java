package com.eryi.server.config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/18 22:52
 */
public class ServletConfigMapping {
    private static Map<String, ServletConfig> configs = new HashMap<String, ServletConfig>();

    static {
        try {
            //1.获取配置文件中配置的应用部署目录
            String BaseDir = ConfigMap.getValue("BaseDir");
            //2.解析应用部署目录下的应用
            AppMap.initAppInfos(BaseDir);
            //3.读取解析结果
            Map<String, Map<String, String>> appInfos = AppMap.getAppInfos();
            Set<String> keySet = appInfos.keySet();
            for (String key : keySet) {
                Map<String, String> appInfo = appInfos.get(key);
                Set<String> servletClazzs = appInfo.keySet();
                for (String servletClazz : servletClazzs) {
                    configs.put(
                            key, new ServletConfig("testServlet",appInfo.get(servletClazz) , servletClazz)
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, ServletConfig> getConfigs() {
        return configs;
    }
}
