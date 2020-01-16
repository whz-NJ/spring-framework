package com.itguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author whz
 * @create 2020-01-09 15:23
 * @desc 在Bean初始化前后进行处理，将后置处理器加入到容器（Bean后置处理器）
 **/
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

  @Override public Object postProcessBeforeInitialization(Object bean,
      String beanName) throws BeansException {
    System.out.println("postProcessBeforeInitialization..." + beanName +
        "=>" + bean);
    // 不包装bean，直接返回
    return bean;
  }

  @Override public Object postProcessAfterInitialization(Object bean,
      String beanName) throws BeansException {
    System.out.println("postProcessAfterInitialization..." + beanName +
        "=>" + bean);
    // 不包装bean，直接返回
    return bean;
  }
}