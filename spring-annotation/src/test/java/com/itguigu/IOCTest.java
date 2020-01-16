package com.itguigu;

import com.itguigu.bean.Blue;
import com.itguigu.bean.Person;
import com.itguigu.config.MainConfig;
import com.itguigu.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Map;

/**
 * @author whz
 * @create 2020-01-09 7:48
 * @desc TODO: add description here
 **/
public class IOCTest {
  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
      MainConfig2.class);

  @Test
  public void testImport() {
    printBeans(applicationContext);
    Blue bean = applicationContext.getBean(Blue.class);
    System.out.println(bean);

    // 工厂Bean获取的是调用getObject方法创建的对象
    Object bean2 = applicationContext.getBean("colorFactoryBean");
    Object bean3 = applicationContext.getBean("colorFactoryBean");
    System.out.println("Bean的类型"+bean2.getClass());
    System.out.println(bean2 == bean3);
    Object bean4 = applicationContext.getBean("&colorFactoryBean"); //这里返回FactoryBean
    System.out.println(bean4);

  }
  private void printBeans(AnnotationConfigApplicationContext applicationContext) {
    String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
    for(String name : beanDefinitionNames) {
      System.out.println(name);
    }

  }
  @Test
  @SuppressWarnings("resources")
  public void test01() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        MainConfig.class);
    String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
    for(String name: beanDefinitionNames) {
      System.out.println(name);
    }

  }

  @Test
  @SuppressWarnings("resources")
  public void test02() {
    // 单实例时在Spring容器创建时，就把Bean创建出来了
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        MainConfig2.class);
    System.out.println("Spring容器创建完成...");
    String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
    for(String name: beanDefinitionNames) {
      System.out.println(name);
    }
    //Bean默认是单实例，如果Scope是多实例，在每次获取时创建Bean对象
    Object person = applicationContext.getBean("person");
    Object person2 = applicationContext.getBean("person");
    System.out.println(person == person2);

  }

  /**
   * 如果系统是windows，放bill gates ，如果是Linux，放linus
   */
  @Test
  public void test03() {
    ConfigurableEnvironment environment = applicationContext.getEnvironment();
    String osName = environment.getProperty("os.name");
    System.out.println(osName);

    String[] beanNamesForType = applicationContext
        .getBeanNamesForType(Person.class);
    for(String name : beanNamesForType) {
      System.out.println(name);
    }
    Map<String, Person> beansOfType = applicationContext
        .getBeansOfType(Person.class);
    System.out.println(beansOfType);
  }

}