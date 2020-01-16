package com.itguigu.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author whz
 * @create 2020-01-09 9:35
 * @desc TODO: add description here
 **/
public class LinuxCondition implements Condition {

  // context: 判断条件能使用的上下文环境
  // metadata: 当前标注了@Conditional 注解的注解信息
  @Override public boolean matches(ConditionContext context,
      AnnotatedTypeMetadata metadata) {
    // context.getBeanFactory(); //当前IOC使用的Bean工厂
    // context.getClassLoader(); // 获取类加载器
    Environment environment = context.getEnvironment();// 获取当前环境信息
    // context.getRegistry(); // 获取到Bean定义的注册类（通过它查有哪些Bean定义，也可以注册一个Bean定义）
    String osName = environment.getProperty("os.name");
    if(osName.contains("Linux")) {
      return true;
    }
    return false;
  }
}