package com.tiv.minispring.context;

import com.tiv.minispring.bean.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类路径xml应用上下文
 */
public class ClassPathXmlApplicationContext {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();

    private Map<String, Object> singletons = new HashMap<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    /**
     * 获取bean实例
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }

    /**
     * 实例化bean
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                singletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取xml文件
     *
     * @param fileName
     */
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            for (Element element : rootElement.elements()) {
                String beanId = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                beanDefinitions.add(new BeanDefinition(beanId, beanClassName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
