package com.itguigu;

import com.itguigu.bean.Yellow;
import com.itguigu.config.MainConfigOfProfile;
import com.itguigu.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @author whz
 * @create 2020-01-09 14:27
 * @desc TODO: add description here
 **/
public class IOCTest_Profile {
  // 1. 使用命令行参数指定环境 -Dspring.profiles.active=test 激活环境
  // 2. 使用代码方式指定/激活某种环境
  @Test
  public void test01() {
    //1. 创建IOC容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
    String[] beanNamesForType = applicationContext
        .getBeanNamesForType(DataSource.class);
    for(String name : beanNamesForType) {
      System.out.println(name);
    }
    applicationContext.close();
  }

  @Test
  public void test02() {
    //1. 创建IOC容器（使用无参的方法，不传入配置类）
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    // 2. 设置需要激活的环境
    applicationContext.getEnvironment().setActiveProfiles("test");
    // 3. 设置主配置类（和test01调用的有参ApplicationContext构造方法步骤一致）
    applicationContext.register(MainConfigOfProfile.class);
    // 4. 启动刷新容器（和test01调用的有参ApplicationContext构造方法步骤一致）
    applicationContext.refresh();

    String[] beanNamesForType = applicationContext
        .getBeanNamesForType(DataSource.class);
    for(String name : beanNamesForType) {
      System.out.println(name);
    }
    Yellow bean = applicationContext.getBean(Yellow.class);
    System.out.println(bean);

    applicationContext.close();
  }

}