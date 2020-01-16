package com.itguigu.aop;

/**
 * @author whz
 * @create 2020-01-09 21:34
 * @desc 业务逻辑类
 **/
public class MathCalculator {
  public int div(int i, int j) {
    System.out.println("enter div()");
    return i/j;
  }
}