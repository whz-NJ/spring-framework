package com.itguigu;

import com.itguigu.bean.Person;
import com.itguigu.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author whz
 * @create 2020-01-09 7:28
 * @desc TODO: add description here
 **/
public class MainTest {
  public static void main(String[] args) {
//    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//        "beans.xml");
//    Object person = applicationContext.getBean("person");
//    System.out.println(person);
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        MainConfig.class);
    Person bean = applicationContext.getBean(Person.class);
    System.out.println(bean);
    applicationContext.getBeanDefinitionNames();
    String[] names = applicationContext
        .getBeanNamesForType(Person.class);
    for(String name: names) {
      System.out.println(name);
    }

  }
}