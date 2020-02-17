package com.itguigu.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author whz
 * @create 2020-01-13 20:54
 * @desc TODO: add description here
 **/
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  @Override public void postProcessBeanFactory(
      ConfigurableListableBeanFactory beanFactory) throws BeansException {
    System.out.println("MyBeanFactoryPostProcessor#postProcessBeanFactory() ...");
    int count = beanFactory.getBeanDefinitionCount();
    String[] names = beanFactory.getBeanDefinitionNames();
    System.out.println("当前BeanFactory 中有" + count +"个Bean.");
    System.out.println(Arrays.asList(names));
  }
  public void MyBeanFactoryPostProcessor() {
    System.out.println("MyBeanFactoryPostProcessor");
  }
}