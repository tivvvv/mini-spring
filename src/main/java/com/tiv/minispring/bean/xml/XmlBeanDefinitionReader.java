package com.tiv.minispring.bean.xml;

import com.tiv.minispring.bean.BeanDefinition;
import com.tiv.minispring.bean.SimpleBeanFactory;
import com.tiv.minispring.bean.injection.ConstructorArgumentValue;
import com.tiv.minispring.bean.injection.ConstructorArgumentValues;
import com.tiv.minispring.bean.injection.PropertyValue;
import com.tiv.minispring.bean.injection.PropertyValues;
import com.tiv.minispring.core.Resource;
import org.dom4j.Element;

import java.util.List;

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
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            for (Element e : propertyElements) {
                String pName = e.attributeValue("name");
                String pType = e.attributeValue("type");
                String pValue = e.attributeValue("value");
                propertyValues.addPropertyValue(new PropertyValue(pName, pType, pValue));
            }
            beanDefinition.setPropertyValues(propertyValues);

            // 处理构造器参数
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String cName = e.attributeValue("name");
                String cType = e.attributeValue("type");
                String cValue = e.attributeValue("value");
                constructorArgumentValues.addGenericArgumentValue(new ConstructorArgumentValue(cName, cType, cValue));
            }
            beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
            this.simpleBeanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

}
