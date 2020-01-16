package com.itguigu.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author whz
 * @create 2020-01-09 14:12
 * @desc TODO: add description here
 **/
public class ColorFactoryBean implements FactoryBean<Color> {

  // 返回一个Color对象，它将被注册到容器里
  @Override public Color getObject() throws Exception {
    System.out.println("生成一个Color Bean");
    return new Color();
  }

  @Override public Class<?> getObjectType() {
    return Color.class;
  }

  //返回true，表示Bean是单实例
  @Override public boolean isSingleton() {
    return true;
  }
}