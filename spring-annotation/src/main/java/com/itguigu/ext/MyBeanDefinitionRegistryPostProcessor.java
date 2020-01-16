package com.itguigu.ext;

import com.itguigu.bean.Blue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

/**
 * @author whz
 * @create 2020-01-13 21:13
 * @desc TODO: add description here
 **/
@Component
public class MyBeanDefinitionRegistryPostProcessor implements
    BeanDefinitionRegistryPostProcessor {

  // BeanFactoryPostProcessor 接口的方法（比 BeanDefinitionRegistryPostProcessor 后执行）
  @Override public void postProcessBeanFactory(
      ConfigurableListableBeanFactory beanFactory) throws BeansException {
    System.out.println("MyBeanDefinitionRegistryPostProcessor#postProcessBeanFactory()...Bean数量"+beanFactory.getBeanDefinitionCount());
  }

  // registry：Bean定义信息的保存中心（以后的BeanFactory就是按照BeanDefinitionRegistry里保存的每个Bean定义信息，创建Bean实例）
  // BeanDefinitionRegistryPostProcessor 接口的方法（在所有BeanDefinition加载到 BeanFactoryPostProcessor 前先执行）
  @Override public void postProcessBeanDefinitionRegistry(
      BeanDefinitionRegistry registry) throws BeansException {
    System.out.println("MyBeanDefinitionRegistryPostProcessor#postProcessBeanDefinitionRegistry()...Bean数量"+registry.getBeanDefinitionCount());
    //添加一个新的Bean定义（下面两个方法效果一样）
//    RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
    AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
        .rootBeanDefinition(Blue.class).getBeanDefinition();
    registry.registerBeanDefinition("hello", beanDefinition);
  }
}