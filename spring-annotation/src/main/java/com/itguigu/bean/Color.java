package com.itguigu.bean;

/**
 * @author whz
 * @create 2020-01-09 10:20
 * @desc TODO: add description here
 **/
public class Color {
private Car car;

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  @Override public String toString() {
    return "Color{" + "car=" + car + '}';
  }
}