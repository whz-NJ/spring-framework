package com.itguigu;

import com.itguigu.bean.Person;
import com.itguigu.config.MainConfigOfLifeCycle;
import com.itguigu.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author whz
 * @create 2020-01-09 16:34
 * @desc TODO: add description here
 **/
public class IOCTest_PropertyValue {
  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
      MainConfigOfPropertyValues.class);

  @Test public void test01() {
    printBeans(applicationContext);
    System.out.println("======================");
    Person person = (Person)applicationContext.getBean("person");
    System.out.println(person);
    ConfigurableEnvironment environment = applicationContext.getEnvironment();
    String property = environment.getProperty("person.nickName");
    System.out.println(property);

    applicationContext.close();
  }
  private void printBeans(AnnotationConfigApplicationContext applicationContext) {
    String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
    for(String name : beanDefinitionNames) {
      System.out.println(name);
    }

  }
}