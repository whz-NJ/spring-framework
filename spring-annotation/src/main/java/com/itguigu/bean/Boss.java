package com.itguigu.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author whz
 * @create 2020-01-09 19:00
 * @desc TODO: add description here
 **/
@Component
//默认如在IOC容器中的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作
public class Boss {
  public Car getCar() {
    return car;
  }
  //标在构造器位置，构造器入参也都是从IOC容器中获取
  // @Autowired
  // public Boss(Car car) {
  // public Boss(@Autowired Car car) { //标注在方法参数上，也会从IOC容器中获取对象
  public Boss(Car car) { // 只有一个有参构造器，这里的@Autowired可以省略
    this.car = car;
    System.out.println("Boss的有参构造器...");
  }
  // @Autowired
  // 标注在方法上，Spring容器创建该对象时，就会调用方法，完成赋值
  // 方法使用的参数，自定义类型值从IOC容器中获取
  public void setCar(Car car) {
    this.car = car;
  }
  // @Autowired
  private Car car;

  @Override public String toString() {
    return "Boss{" + "car=" + car + '}';
  }
}