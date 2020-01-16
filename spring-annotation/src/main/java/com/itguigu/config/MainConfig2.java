package com.itguigu.config;

import com.itguigu.bean.Color;
import com.itguigu.bean.ColorFactoryBean;
import com.itguigu.bean.Person;
import com.itguigu.bean.Red;
import com.itguigu.condition.LinuxCondition;
import com.itguigu.condition.MyImportBeanDefinitionRegistrar;
import com.itguigu.condition.MyImportSelector;
import com.itguigu.condition.WindowsCondition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * @author whz
 * @create 2020-01-09 9:05
 * @desc TODO: add description here
 **/
@Configuration
// @Conditional 不仅可以放在方法上，也可以放在类上
// 表示满足条件时，这个类注册的所有Bean才会生效
@Conditional({WindowsCondition.class})
// @Import(Color.class) //bean名称默认为类全名
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class })
public class MainConfig2 {
  @Bean("person")
  // 单实例Bean可以进行懒加载（容器启动时不创建对象，在第一次获取Bean时再创建对象，并进行初始化）
  @Lazy
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) //多实例时，是在getBeans时创建实例
  // @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) //Spring容器创建就会建Bean实例
  public Person person1() {
    System.out.println("添加Bean");
    return new Person("zhangsan", 25);
  }

  /**
   * @Conditional 按照一定条件判断，满足条件，给容器注册Bean
   */
  @Bean("bill")
  @Conditional({WindowsCondition.class})
  public Person person01() {
    return new Person("Bill Gates", 62);
  }

  @Bean("linus")
  @Conditional({LinuxCondition.class})
  public Person person02() {
    return new Person("linus", 48);
  }
  /**
   * 给容器中注册组件：
   * 1）包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）（对于开发人员自己写的类可以）
   * 2）@Bean（导入第三方包里的组件，new 对象）
   * 3）@Import（直接快速地给容器中导入一个组件）
   *     a）@Import(要导入到容器中的组件类名)，容器中会自动注册该组件，id默认为全类名
   *     b）@Import(Selector) 返回需要导入组件的全类名数组
   *     c）@Import(BeanDefinitionRegistry)
   * 4）使用Spring提供的FactoryBean
   *     a) 默认获取的是工厂Bean的getObject()创建的对象
   *     b) 获取工厂Bean本身，需要给bean id前面加&符号
   */
  @Bean //这里返回的Bean类型不是ColorFactoryBean，
  // 而是ColorFactoryBean.getObject()返回的对象类型
  public ColorFactoryBean colorFactoryBean() {
    return new ColorFactoryBean();
  }
}
