package com.itguigu.config;

import com.itguigu.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author whz
 * @create 2020-01-09 16:33
 * @desc TODO: add description here
 **/
@Configuration
// 使用@Vallue标签给属性赋值
// 1. 基本赋值
// 2. SpEL表达式： #{20-2}
// 3. 可以用${}，取出配置文件【properties】中的值（环境变量中的值）
//加载外部配置文件中的属性，保存到运行时环境变量中
//然后使用${}取出配置文件中的值
@PropertySource({"classpath:/person.properties"})
// 也可以用 @PropertySources，添加多个@PropertySource
public class MainConfigOfPropertyValues {
  @Bean
  public Person person() {
    return new Person();
  }
}