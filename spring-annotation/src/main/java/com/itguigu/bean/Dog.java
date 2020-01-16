package com.itguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author whz
 * @create 2020-01-09 15:13
 * @desc TODO: add description here
 **/
@Component
public class Dog implements ApplicationContextAware {
  private ApplicationContext applicationContext = null;
  public Dog() {
    System.out.println("Dog constructor ...");
  }
  //对象创建并赋值之后调用
  @PostConstruct
  public void init() {
    System.out.println("Dog @PostConstruct ...");
  }
  @PreDestroy
  public void destory() {
    System.out.println("Dog @PreDestroy ...");
  }

  @Override public void setApplicationContext(
      ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}