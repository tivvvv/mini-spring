package com.tiv.mini.spring.bean.xml;

import com.tiv.mini.spring.bean.BeanDefinition;
import com.tiv.mini.spring.bean.SimpleBeanFactory;
import com.tiv.mini.spring.bean.injection.ConstructorArgumentValue;
import com.tiv.mini.spring.bean.injection.ConstructorArgumentValues;
import com.tiv.mini.spring.bean.injection.PropertyValue;
import com.tiv.mini.spring.bean.injection.PropertyValues;
import com.tiv.mini.spring.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * xml bean定义读取器
 */
public class XmlBeanDefinitionReader {

    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    /**
     * 加载bean定义
     *
     * @param resource
     */
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

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

            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pName = e.attributeValue("name");
                String pType = e.attributeValue("type");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (pValue != null && !pValue.equals("")) {
                    isRef = false;
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                propertyValues.addPropertyValue(new PropertyValue(pName, pType, pV, isRef));
            }
            beanDefinition.setPropertyValues(propertyValues);

            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
            this.simpleBeanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

}
