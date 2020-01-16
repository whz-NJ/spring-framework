package com.itguigu.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author whz
 * @create 2020-01-09 7:15
 * @desc TODO: add description here
 **/
public class Person {
  // 使用@Vallue标签给属性赋值
  // 1. 基本赋值
  // 2. SpEL表达式： #{20-2}
  // 3. 可以用${}，取出配置文件【properties】中的值（环境变量中的值）
  @Value("zhangsan")
private String name;

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
  @Value("${person.nickName}")
  private String nickName;

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  @Value("#{20-2}")
  private Integer age;

  public Person(String name, Integer age) {
    this.name = name;
    this.age = age;
  }

  public Person() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override public String toString() {
    return "Person{" + "name='" + name + '\'' + ", nickName='" + nickName + '\''
        + ", age=" + age + '}';
  }
}