package com.tiv.mini.spring.web;

import com.tiv.mini.spring.core.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private Map<String, MappingValue> mappingValues;

    private Map<String, Class<?>> mappingClz = new HashMap<>();

    private Map<String, Object> mappingObjs = new HashMap<>();

    private String contextConfigLocationStr;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        contextConfigLocationStr = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;

        try {
            xmlPath = this.getServletContext().getResource(contextConfigLocationStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Resource resource = new XmlResource(xmlPath);
        XmlConfigReader xmlConfigReader = new XmlConfigReader();
        mappingValues = xmlConfigReader.loadConfig(resource);
        Refresh();
    }

    protected void Refresh() {
        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClz();
            Object obj = null;
            Class<?> clz = null;

            try {
                clz = Class.forName(className);
                obj = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            mappingClz.put(id, clz);
            mappingObjs.put(id, obj);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (this.mappingValues.get(servletPath) == null) {
            return;
        }

        Class<?> clz = this.mappingClz.get(servletPath);
        Object obj = this.mappingObjs.get(servletPath);
        String methodName = this.mappingValues.get(servletPath).getMethod();

        Object result = null;
        try {
            Method method = clz.getMethod(methodName);
            result = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().append(result.toString());
    }

}
