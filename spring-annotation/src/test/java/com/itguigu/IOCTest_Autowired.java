package com.itguigu;

import com.itguigu.bean.Boss;
import com.itguigu.bean.Car;
import com.itguigu.bean.Color;
import com.itguigu.config.MainConfigOfAutowired;
import com.itguigu.dao.BookDao;
import com.itguigu.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.itguigu.config.MainConfigOfLifeCycle;

/**
 * @author whz
 * @create 2020-01-09 14:27
 * @desc TODO: add description here
 **/
public class IOCTest_Autowired {

  @Test
  public void test01() {
    //1. 创建IOC容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
    BookService bookService = applicationContext.getBean(BookService.class);
    System.out.println(bookService); // BookService里默认注入的是label="1"的Bean，没有用配置类里的"bookDao2"这个Bean

    String[] beanNamesForType = applicationContext
        .getBeanNamesForType(BookDao.class);
    for(String name: beanNamesForType) {
      System.out.println(name);
    }
    Boss boss = applicationContext.getBean(Boss.class);
    System.out.println(boss);
    Car car = applicationContext.getBean(Car.class);
    System.out.println(car);

    Color color = applicationContext.getBean(Color.class);
    System.out.println(color);
    System.out.println(applicationContext);
    applicationContext.close();
  }
}