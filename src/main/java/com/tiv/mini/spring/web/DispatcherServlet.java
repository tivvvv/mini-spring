package com.tiv.mini.spring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    /**
     * package名称列表
     */
    private List<String> packageNames = new ArrayList<>();

    /**
     * url列表
     */
    private List<String> urlMappingNames = new ArrayList<>();

    /**
     * controller名称列表
     */
    private List<String> controllerNames = new ArrayList<>();

    /**
     * controller名称与对象映射
     */
    private Map<String, Object> controllerObjs = new HashMap<>();

    /**
     * controller名称与类映射
     */
    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    /**
     * url与对象映射
     */
    private Map<String, Object> urlMappingObjs = new HashMap<>();

    /**
     * url与方法映射
     */
    private Map<String, Method> urlMappingMethods = new HashMap<>();

    private String contextConfigLocationStr;

    private WebApplicationContext webApplicationContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        contextConfigLocationStr = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(contextConfigLocationStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        refresh();
    }

    protected void refresh() {
        // 初始化controller
        initController();

        // 初始化url映射
        initMapping();
    }

    private void initController() {
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                obj = clz.newInstance();
                this.controllerObjs.put(controllerName, obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if (isRequestMapping) {
                    String methodName = method.getName();
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(urlMapping);
                    this.urlMappingObjs.put(urlMapping, obj);
                    this.urlMappingMethods.put(urlMapping, method);
                }
            }
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> controllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            controllerNames.addAll(scanPackage(packageName));
        }
        return controllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> controllerNames = new ArrayList<>();
        URI uri = null;
        try {
            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File dir = new File(uri);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                controllerNames.add(controllerName);
            }
        }
        return controllerNames;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (!this.urlMappingNames.contains(servletPath)) {
            return;
        }
        Object result = null;
        Method method = this.urlMappingMethods.get(servletPath);
        Object obj = this.urlMappingObjs.get(servletPath);
        try {
            result = method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().append(result.toString());
    }

}
