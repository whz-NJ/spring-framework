package com.itguigu.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author whz
 * @create 2020-01-12 14:25
 * @desc 声明式事务
 * 环境搭建：
 * 1、导入依赖
 *     数据源（mysql-connector）、数据库驱动（c3p0）、Spring JDBC模块（对数据库操作的简化，以及事务控制的简化--jdbcTemplate）
 * 2、配置数据源、JdbcTemplate操作数据库
 * 3、给方法上标注 @Transactional ，就变成一个事务方法
 * 4、@EnableTransactionManagement 开启基于注解的事务管理功能
 * 5、配置基于平台的事务管理器来控制事务，加入到IOC容器（Spring JdbcTemplate/MyBatis事务整合，都用 DataSourceTransactionManager 这个事务模块）
 *    如果导入了Hibernate，还有orm/jta相关的TransactionManager。
 *
 * 原理：
 * 1、@EnableTransactionManagement 利用 TransactionManagementConfigurationSelector ，导入两个Bean，类型和@EnableTransactionManagement
 *   注解的 mode 属性有关（默认为PROXY），默认导入 AutoProxyRegistrar 和 ProxyTransactionManagementConfiguration 两种类型Bean
 * 2、AutoProxyRegistrar 给容器中注册 InfrastructureAdvisorAutoProxyCreator 组件，它和 AOP 里通过 @EnableAspectJAutoProxy 导入的
 *    AnnotationAwareAspectJAutoProxyCreator 一样，也是一个后置处理器（继承于 SmartInstantiationAwareBeanPostProcessor）
 *    InfrastructureAdvisorAutoProxyCreator作用：只是利用后置处理器机制，在业务对象创建以后包装对象，返回一个代理对象（有增强器），
 *    代理对象执行方法利用拦截器链进行调用（和之前AOP逻辑完全一样---相同的代码）
 * 3、ProxyTransactionManagementConfiguration
 *    a）transactionAdvisor() 给容器注册事务增强器
 *       1）这个增强器需要 @Transactional 注解属性信息：AnnotationTransactionAttributeSource
 *          transactionAdvisor() 给容器中注册事务增强器，并调用transactionInterceptor()生成TransactionInterceptor，在
 *          transactionAttributeSource()方法内调用transactionAttributeSource()生成 AnnotationTransactionAttributeSource，在 AnnotationTransactionAttributeSource构造方法里
 *          SpringTransactionAnnotationParser JtaTransactionAnnotationParser Ejb3TransactionAnnotationParser 3个 @Transactional 注解解析器。
 *       2）这个增强器需要一个事务拦截器：transactionInterceptor()，保存事务属性信息，还有事务管理器信息。
 *          它是一个 MethodInterceptor（和AOP里一样，各种通知方法也转换为 MethodInterceptor，然后调用业务对象方法时，会调用MethodInterceptor#invoke()）
 *          在目标方法执行的时候，调用代理对象调用  CglibAopProxy.CglibMethodInvocation#interceptor() 执行拦截器链，此时连接器链只有 TransactionInterceptor
 *          TransactionInterceptor#invokeWithinTransaction() 作用：
 *            a）获取事务属性：TransactionAttribute
 *            b）获取事务管理器：PlatformTransactionManager，如果@Transaction 没有添加指定任何 TransactionManager，根据类型，从IOC容器获取PlatformTransactionManager类型事务管理器。
 *            c）执行业务目标方法，如果抛出异常，获取事务管理器，利用事务管理器回滚数据库事务。
 *                              如果政策，利用事务管理器提交事务。
 *
 **/
@Configuration
@ComponentScan("com.itguigu.tx")
@EnableTransactionManagement
public class TxConfig {
    // 数据源
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
      ComboPooledDataSource dataSource = new ComboPooledDataSource();
      dataSource.setUser("root");
      dataSource.setPassword("MiGu@123");
      dataSource.setDriverClass("com.mysql.jdbc.Driver");
      dataSource.setJdbcUrl("jdbc:mysql://10.167.168.7:3306/testdb");
      return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
      // Spring对@Configuration 配置类的特殊处理：
      // 给容器中加组件的方法，多次调用，都只是从容器中找组件而已，不会创建多个Bean
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
      return jdbcTemplate;
    }

    @Bean //注册事务管理器在容器中
    public PlatformTransactionManager transactionManager()
        throws PropertyVetoException {
      return new DataSourceTransactionManager(dataSource()); //必须传入数据源
    }
}