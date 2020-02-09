package com.itguigu.config;

import com.itguigu.bean.Car;
import com.itguigu.bean.Color;
import com.itguigu.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author whz
 * @create 2020-01-09 16:58
 * @desc 自动装配（Spring利用依赖注入，完成对IOC容器中各个组件的依赖关系赋值）
 * 1、 @Autowired：自动注入（Spring自己定义的注解）
 *   a) 默认优选按照类型在容器中找对应组件，applicationContext.getBean(BookDao.class)
 *   b) 如果这种类型的Bean有多个，再将属性名作为Bean Id在容器中查找：applicationContext.getBean("bookDao")
 *   c) 通过 @Qualifier("bookDao") 明确指定使用哪个名字的Bean来装配，而不是使用属性名称
 *   d) 如果找不到该类型的Bean，抛异常： No qualifying bean of type 'com.itguigu.dao.BookDao' available
 *   e) 默认一定要把这个属性值赋值好（不能赋值为null），可以用 @Autowired(required = false) 设置为不必须。
 *   f) @Primary，让Spring自动装配时，默认使用首选的Bean（如果@Autowired指定了@Qualifer，@Primary指定的首选组件就没有效果了）
 *     BookService {
 *         @Autowired
 *         BookDao bookDao;
 *     }
 * 2）Spring还支持使用：
 *    @Resource(JSR250 Java规范里定义)
 *    可以和@Autowire一样，实现自动装配，@Resource默认按变量名称来装配，也可以通过Name属性指定：@Resource(name="bookDao2")
 *    @Resource 注解如果按照名称找不到对应的类，就再按照类型来装配。
 *    它不能结合@Qualifier/@Primary这些Spring注解使用，也没有required=false功能
 *    @Inject(JSR330 Java规范里定义，使用该注解需要依赖 javax.inject 包)
 *    日志打印：'javax.inject.Inject' annotation found and supported for autowiring
 *    即@Inject和@Autowired功能一样，支持@Primary装配首选组件，不同的是@Inject没有required=false功能
 *    脱离了Spring框架，使用其他IOC框架，@Resource/@Inject注解还能用，但无法用@Autowired注解。
 *    推荐使用@Autowired，功能更强，日志打印 AutowiredAnnotationBeanPostProcessor，spring通过它支持@Autowired
 * 3）@Autowired 可以在构造器/方法/方法参数/属性 都可以使用。
 *    下面几种情况，入参都是从IOC容器获取参数对应组件值：
 *    a) @Autowired 标注在方法上，构建该对象时，会调用该方法，通常是@Bean修饰方法，需要的参数从容器获取，这里@Autowired可以省略
 *    b) @Autowired 标注在有参构造器上，如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略
 *    c) @Autowired 标注在普通方法入参上
 * 4）自定义组件想要使用Spring底层的组件（ApplicationContext,BeanFactory...）
 *    只需要让自定义组件实现 xxxAware 接口即可，它们有个公共的接口 Aware，看它的解释
 *    这样创建对象时，会调用相关接口注入相关组件。比如ApplicationContextAware/BeanFactoryAware/...
 *    这些xxxAware 有相应的 xxxAwareProcessor 处理，比如 ApplicationContextAwareProcessor，它们是
 *    通过实现 BeanPostProcessor 接口（后置处理器）实现的功能。
 *    原理分析：给 Red#setApplicationContext 方法加断点
 *    ApplicationContextAwareProcessor#postProcessBeforeInitialization -> invokeAwareInterfaces() -> Red#setApplicationContext
 * 5）
 *
 **/
@Configuration
@ComponentScan({"com.itguigu.service","com.itguigu.dao","com.itguigu.controller",
    "com.itguigu.bean"})
public class MainConfigOfAutowired {

  @Primary //首选用来装配的组件（Bean）
  @Bean("bookDao2")
  public BookDao bookDao() {
    BookDao bookDao = new BookDao();
    bookDao.setLabel("2");
    return bookDao;
  }
  // @Bean 标注的方法，创建对象时，方法参数的值从容器中获取
  // 这里的car 参数的@Autowired可以省略
  @Bean
  public Color color(Car car) {
    Color color = new Color();
    color.setCar(car);
    return color;
  }

}