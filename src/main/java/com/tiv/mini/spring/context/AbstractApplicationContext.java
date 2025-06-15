package com.tiv.mini.spring.context;

import com.tiv.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.tiv.mini.spring.beans.factory.config.BeanPostProcessor;
import com.tiv.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.tiv.mini.spring.beans.factory.exception.BeansException;
import com.tiv.mini.spring.context.event.ApplicationEventPublisher;
import com.tiv.mini.spring.core.env.Environment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private Environment environment;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    private long startupDate;

    private final AtomicBoolean active = new AtomicBoolean();

    private final AtomicBoolean closed = new AtomicBoolean();

    @Setter
    @Getter
    private ApplicationEventPublisher applicationEventPublisher;

    public void refresh() {
        postProcessBeanFactory(getBeanFactory());
        registerBeanPostProcessors(getBeanFactory());
        initApplicationEventPublisher();
        onRefresh();
        registerListeners();
        finishRefresh();
    }

    /**
     * 前置处理bean工厂
     *
     * @param beanFactory
     */
    abstract void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);


    /**
     * 注册bean后置处理器
     *
     * @param beanFactory
     */
    abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory);

    /**
     * 初始化事件发布器
     */
    abstract void initApplicationEventPublisher();

    /**
     * 应用上下文刷新
     */
    abstract void onRefresh();

    /**
     * 注册监听器
     */
    abstract void registerListeners();

    /**
     * 刷新后操作
     */
    abstract void finishRefresh();

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public String getApplicationName() {
        return "application";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        getBeanFactory().addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return getBeanFactory().getBeanPostProcessorCount();
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public boolean containsBean(String beanName) {
        return getBeanFactory().containsBean(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return getBeanFactory().isSingleton(beanName);
    }

    @Override
    public boolean isPrototype(String beanName) {
        return getBeanFactory().isPrototype(beanName);
    }

    @Override
    public Class<?> getType(String beanName) {
        return getBeanFactory().getType(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        getBeanFactory().registerSingleton(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return getBeanFactory().getSingleton(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return getBeanFactory().containsSingleton(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return getBeanFactory().getSingletonNames();
    }

}
