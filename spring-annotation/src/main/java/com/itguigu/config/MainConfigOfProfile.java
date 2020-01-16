package com.itguigu.config;

import com.itguigu.bean.Yellow;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author whz
 * @create 2020-01-09 20:00
 * @desc Spring为我们提供的根据当前环境，动态地激活/切换一系列组件
 * 比如：开发环境、测试环境、生产环境，连的数据源是不同的，不同环境不希望改代码
 * @Profile ：指定组件在哪个环境情况下才能被注册到容器中，不指定，任何环境下都能注册该组件
 * 1）加了 @Profile修饰的Bean，只有这个环境被激活时，才能注册到容器中，默认是default环境：@Profile("default")
 * 2）@Profile不仅可以写在@Bean修饰方法上，也可以放在配置类上，只有是指定环境，整个配置类所有配置才能开始生效
 *    此时不管配置类里各个Bean如何配置，只要它们所在配置类的@Profile没有激活（配置类没加载），这些Bean都不会创建
 * 3）没有标注环境标识的Bean在任何环境下都是加载的。
 *
 *
 **/
@Component
@PropertySource("classpath:/dbconfig.properties")
// @Profile("test")
public class MainConfigOfProfile implements EmbeddedValueResolverAware {
  @Value("${db.user}")
  private String user;
  private String driverClass;

  private StringValueResolver valueResolver;

  @Bean
  public Yellow yellow() {
    return new Yellow();
  }

  @Bean("testDataSource")
  @Profile("test")
  public DataSource dataSourceTest(@Value("${db.password}") String passwd) throws PropertyVetoException {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setUser(user);
    dataSource.setPassword(passwd);
    dataSource.setJdbcUrl("jdbc://mysql://10.167.168.7:3306/ai_proxy_db");
    dataSource.setDriverClass(driverClass);
    return dataSource;
  }



  @Profile("dev")
  @Bean("devDataSource")
  public DataSource dataSourceDev(@Value("${db.password}") String passwd) throws PropertyVetoException {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setUser(user);
    dataSource.setPassword(passwd);
    dataSource.setJdbcUrl("jdbc://mysql://10.167.168.7:3306/dev");
    dataSource.setDriverClass(driverClass);
    return dataSource;
  }

  @Profile("prod")
  @Bean("prodDataSource")
  public DataSource dataSourceProd(@Value("${db.password}") String passwd) throws PropertyVetoException {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setUser(user);
    dataSource.setPassword(passwd);
    dataSource.setJdbcUrl("jdbc://mysql://10.167.168.7:3306/prod");
    dataSource.setDriverClass(driverClass);
    return dataSource;
  }

  @Override public void setEmbeddedValueResolver(StringValueResolver resolver) {
    this.valueResolver = resolver;
    driverClass = valueResolver.resolveStringValue("${db.driverClass}");
  }
}