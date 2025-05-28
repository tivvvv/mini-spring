package com.tiv.minispring.bean;

import com.tiv.minispring.core.Resource;
import org.dom4j.Element;

/**
 * xml bean定义读取器
 */
public class XmlBeanDefinitionReader {

    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            this.simpleBeanFactory.registerBeanDefinition(new BeanDefinition(beanId, beanClassName));
        }
    }

}
