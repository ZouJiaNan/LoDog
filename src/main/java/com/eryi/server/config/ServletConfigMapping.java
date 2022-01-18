package com.eryi.server.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/1/18 22:52
 */
public class ServletConfigMapping {
    private static List<ServletConfig> configs=new ArrayList<ServletConfig>();

    static {
        configs.add(
                new ServletConfig("testServlet","/test","com.eryi.server.servlet.testServlet")
        );
    }

    public static List<ServletConfig> getConfigs(){
        return configs;
    }
}
