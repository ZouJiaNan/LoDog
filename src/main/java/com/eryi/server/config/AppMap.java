package com.eryi.server.config;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author ZouJiaNan
 * @version 1.0
 * @description
 * @date 2022/2/19 17:41
 */
public class AppMap {
    private static Map<String, Map<String,String>> appInfos = new HashMap();
    private static final String startSign = "-start";
    private static final String endSign = "-end";

    /**
     * 解压应用部署目录下的app压缩包
     *
     * @param path
     */
    private static void unZip(String path) {
        try {
            File appDir = new File(path);
            File[] fs = appDir.listFiles();
            String parentPath = appDir.getPath();
            for (File app : fs) {
                if (app.getName().endsWith(".jar")) {
                    //1.获取应用部署目录下部署的app
                    JarFile jar = new JarFile(app);
                    //2.获取app名称,创建app文件夹
                    String dirName = app.getName().replaceAll(".jar", "");
                    for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements(); ) {
                        JarEntry entry = (JarEntry) enums.nextElement();
                        String fileName = path + dirName + File.separator + entry.getName();
                        File f = new File(fileName);
                        if (fileName.endsWith("/")) {
                            f.mkdirs();
                        }
                    }
                    //2.解压app压缩包
                    for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements(); ) {
                        JarEntry entry = (JarEntry) enums.nextElement();
                        String fileName = entry.getName();
                        File f = new File(parentPath + File.separator + dirName + File.separator + fileName.replace(parentPath, ""));
                        if (f == null) {
                            f.mkdir();
                        }
                        if (!fileName.endsWith("/")) {
                            InputStream is = jar.getInputStream(entry);
                            FileOutputStream fos = new FileOutputStream(f);
                            while (is.available() > 0) {
                                fos.write(is.read());
                            }
                            fos.close();
                            is.close();
                        }
                    }
                    //3.删除原app压缩包
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析app的信息
     * @param path
     */
    public static void initAppInfos(String path) {
        //1.解压部署目录下的app压缩包
        unZip(path);

        File baseDir = new File(path);
        File[] apps = baseDir.listFiles();
        //2.解析app的配置文件
        for (File app : apps) {
            //app名称
            String appName = app.getName();
            //解析servletMap
            appInfos.put(appName,preServletMapTxt(""));
        }

    }

    /**
     * 解析app的servletMap.txt
     * @param path
     * @return
     */
    private static Map<String, String> preServletMapTxt(String path) {
        path = "D:\\LogDog\\";
        Map<String,String> servletMap=new HashMap();
        String ServletClass=null;
        String ServletMapping=null;
        try {
            FileInputStream fis = new FileInputStream("D:\\LogDog\\servletMap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                if (startSign.equals(line)) {
                    while (!endSign.equals(line) && line != null) {
                        line = br.readLine();
                        String[] lineStrs = line.split("=");
                        if ("Servlet.Class".equals(lineStrs[0])) {
                            ServletClass = lineStrs[1];
                        }
                        if ("Servlet.Mapping".equals(lineStrs[0])) {
                            ServletMapping = lineStrs[1];
                        }
                    }
                }
                if(ServletClass!=null&&ServletMapping!=null){
                    servletMap.put(ServletClass,ServletMapping);
                }
                line=br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return servletMap;
    }


    /**
     * 获取appInfos
     */
    public static Map<String, Map<String,String>> getAppInfos(){
        return appInfos;
    }
}
