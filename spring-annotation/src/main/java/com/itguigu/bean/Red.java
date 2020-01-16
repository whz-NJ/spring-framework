package com.itguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author whz
 * @create 2020-01-09 10:25
 * @desc TODO: add description here
 **/
@Component
public class Red implements ApplicationContextAware, BeanNameAware,EmbeddedValueResolverAware {
  private ApplicationContext applicationContext;
  @Override public void setApplicationContext(
      ApplicationContext applicationContext) throws BeansException {
    System.out.println("传入的IOC" + applicationContext);
    this.applicationContext = applicationContext;

  }

  @Override public void setBeanName(String name) {
    System.out.println("Red 对象的Bean名称/ID=" + name);
  }

  // 用来解析占位符字符串，返回解析后的值
  @Override public void setEmbeddedValueResolver(StringValueResolver resolver) {
    String resolveStringValue = resolver.resolveStringValue("你好 ${os.name}, 我是#{20+2}");
    System.out.println(resolveStringValue);

  }
}