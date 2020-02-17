package com.itguigu;

import com.itguigu.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.itguigu.aop.MathCalculator;
import com.itguigu.config.MainConfigOfAOP;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * @author whz
 * @create 2020-01-09 14:27
 * @desc TODO: add description here
 **/
public class IOCTest_Ext {

  @Test
  public void test01() {
    //1. 创建IOC容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
    //发布事件
    applicationContext.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
      @Override public Object getSource() {
        return super.getSource();
      }
    });

    applicationContext.close();
  }

  public static void main(String[] args) {
    new IOCTest_Ext().test01();
  }
}