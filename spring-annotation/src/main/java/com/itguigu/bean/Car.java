package com.itguigu.bean;

import org.springframework.stereotype.Component;

/**
 * @author whz
 * @create 2020-01-09 14:25
 * @desc TODO: add description here
 **/
@Component
public class Car {

  public Car() {
    System.out.println("Car constructor ...");
  }
  public void init() {
    System.out.println("car init ...");
  }
  public void destroy() {
    System.out.println("car destroy ...");
  }
}