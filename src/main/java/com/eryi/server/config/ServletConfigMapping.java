package com.eryi.server.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/18 22:52
 */
public class ServletConfigMapping {
    private static Map<String,ServletConfig> configs=new HashMap<String, ServletConfig>();

    static {
        configs.put(
                "/test",new ServletConfig("testServlet","/test","com.eryi.server.servlet.testServlet")
        );
    }

    public static Map<String,ServletConfig> getConfigs(){
        return configs;
    }
}
