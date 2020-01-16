package com.itguigu.config;

import com.itguigu.bean.Person;
import com.itguigu.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.ComponentScan.Filter;

/**
 * @author whz
 * @create 2020-01-09 7:30
 * @desc TODO: add description here
 **/
@Configuration
// @ComponentScan(value="com.itguigu")
//@ComponentScan(value="com.itguigu", excludeFilters = {
//    @Filter(type=FilterType.ANNOTATION, classes={Controller.class, Service.class})
//@ComponentScan(value="com.itguigu", includeFilters = {
//    @Filter(type=FilterType.ANNOTATION, classes={Controller.class})},
//    useDefaultFilters = false)
//@ComponentScans(value = {
//    @ComponentScan(value="com.itguigu", includeFilters = {
//        @Filter(type=FilterType.ANNOTATION, classes={Controller.class})},
//        useDefaultFilters = false)
//})
@ComponentScan(value="com.itguigu", includeFilters = {
//    @Filter(type=FilterType.ANNOTATION, classes={Controller.class}),
//    @Filter(type=FilterType.ASSIGNABLE_TYPE, classes={BookService.class}),
    @Filter(type=FilterType.CUSTOM, classes={MyTypeFilter.class})
    },
    useDefaultFilters = false)
public class MainConfig {
  // 默认方法名作为bean名称
  @Bean("person")
  public Person person1() {
    return new Person("lisi", 20);
  }
}