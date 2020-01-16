package com.itguigu.condition;

import com.itguigu.bean.Rainbow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author whz
 * @create 2020-01-09 11:24
 * @desc TODO: add description here
 **/
public class MyImportBeanDefinitionRegistrar implements
    ImportBeanDefinitionRegistrar {

  // importingClassMetadata 当前类的注解信息
  // registry BeanDefinition的注册类（包含所有Bean定义）
  // 把所有需要添加到容器的Bean，调用registerBeanDefinition方法，可以手动注册进来
  @Override public void registerBeanDefinitions(
      AnnotationMetadata importingClassMetadata,
      BeanDefinitionRegistry registry) {
    boolean red = registry.containsBeanDefinition("com.itguigu.bean.Red");
    boolean blue = registry.containsBeanDefinition("com.itguigu.bean.Blue");
    if(red && blue) {
      RootBeanDefinition beanDefinition = new RootBeanDefinition(
          Rainbow.class);
      registry.registerBeanDefinition("rainbow", beanDefinition);
    }

  }
}