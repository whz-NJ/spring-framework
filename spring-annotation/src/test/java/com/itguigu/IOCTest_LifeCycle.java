package com.itguigu;

import com.itguigu.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author whz
 * @create 2020-01-09 14:27
 * @desc TODO: add description here
 **/
public class IOCTest_LifeCycle {

  @Test
  public void test01() {
    //1. 创建IOC容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
    System.out.println("IOC容器创建完成");
//    Object bean = applicationContext.getBean("car");
//    System.out.println(bean);
    applicationContext.close();
  }
}