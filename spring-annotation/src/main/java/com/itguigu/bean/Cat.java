package com.itguigu.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author whz
 * @create 2020-01-09 15:00
 * @desc TODO: add description here
 **/
@Component
public class Cat implements InitializingBean, DisposableBean {

  public Cat() {
    System.out.println("cat constructor...");
  }

  @Override public void destroy() throws Exception {
    System.out.println("cat destroy...");
  }

  @Override public void afterPropertiesSet() throws Exception {
    System.out.println("cat afterPropertiesSet...");
  }
}