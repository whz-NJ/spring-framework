package com.itguigu.config;

import com.itguigu.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author whz
 * @create 2020-01-09 14:22
 * @desc 生命周期：创建---初始化---销毁，容器管理Bean的生命周期
 * 我们可以自定义初始化和销毁方法，容器在Bean运行到初始化/销毁方法，会调用我们自定义的方法
 * 构造（创建对象）
 *     单实例：在容器启动时创建对象
 *     多实例：在每次获取Bean的时候创建
 * 初始化：
 *    对象创建完成，并赋值好，调用初始化方法
 * 销毁：
 *    单实例Bean：容器关闭时调用
 *    多实例Bean：不管理，不调用销毁方法，由用户自己负责销毁
 *
 * 1）指定初始化/销毁方法
 *     a) 在@Bean里指定（init-method/destroy-method）
 * 2）通过让Bean实现InitializingBean接口（定义初始化逻辑），
 *             实现DisposibleBean接口（定义销毁接口）
 * 3）可以使用JSR250规范里两个注解
 *     @PostConstructor： 在Bean创建完成，并且属性赋值完成，调用
 *     @PreDestory： 在Bean将要从容器中移除前调用
 * 4）BeanPostProcessor： Bean的后置处理器
 *     在Bean的初始化前后进行处理
 *     postProcessBeforeInitialization：初始化之前工作
 *     postProcessAfterInitialization：在初始化之后调用
 *     原理： 在AbstractAutowireCapableBeanFactory#initializeBean()中调用 AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization
 *           遍历容器中所有的BeanPostProcessor，诶个执行，但一旦某个后置处理器返回null，就跳出，不执行后面的BeanPostProcessor
 *     AbstractAutowireCapableBeanFactory#doCreateBean {
 *       populateBean(beanName, mbd, instanceWrapper); //给Bean进行属性赋值(setter方法)
 *       initializeBean(beanName, exposedObject, mbd) {
 *         applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *         invokeInitMethods(beanName, wrappedBean, mbd);
 *         applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *       }
 *     }
 *    Spring 底层大量使用 BeanPostProcessor（这个接口，Spring有很多实现类，比如 ApplicationContextAwareProcessor BeanValidationPostProcessor ...
 *    BeanValidationPostProcessor 在Web编程，页面提交的值，构造的对象通过它来校验。
 *    InitDestroyAnnotationBeanPostProcessor 实现了 @PostConstruct 和 @PreDestroy 标签功能
 *    AutowiredAnnotationBeanPostProcessor 处理 @Autowired 注解标注的所有属性
 *    AsyncAnnotationBeanPostProcessor 处理 @Async 修饰的方法
 *
 **/
@Configuration
@ComponentScan("com.itguigu.bean")
public class MainConfigOfLifeCycle {

  // @Scope("prototype")
  @Bean(initMethod = "init", destroyMethod = "destroy")
  public Car car() {
    return new Car();
  }


}