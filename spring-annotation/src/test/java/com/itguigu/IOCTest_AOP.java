package com.itguigu;

import com.itguigu.aop.MathCalculator;
import com.itguigu.config.MainConfigOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.itguigu.bean.Boss;
import com.itguigu.bean.Car;
import com.itguigu.bean.Color;
import com.itguigu.config.MainConfigOfAutowired;
import com.itguigu.dao.BookDao;
import com.itguigu.service.BookService;

/**
 * @author whz
 * @create 2020-01-09 14:27
 * @desc TODO: add description here
 **/
public class IOCTest_AOP {

  @Test
  public void test01() {
    //1. 创建IOC容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
    // 错误示例：
//    MathCalculator calculator = new MathCalculator();
//    calculator.div(1,2);
    MathCalculator calculator = applicationContext.getBean(MathCalculator.class);
    calculator.div(1,1);
    try {
      calculator.div(0, 0);
    } catch (Exception e) {

    }

    applicationContext.close();
  }
}