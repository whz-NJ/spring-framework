package com.itguigu.ext;

import com.itguigu.bean.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author whz
 * @create 2020-01-13 20:51
 * @desc 拓展原理
 *
 * 1、BeanPostProcessor（前面说过：Bean后置处理器，在Bean创建对象初始化前后进行拦截工作）
 *    BeanFactoryPostProcessor：BeanFactory的后置处理器，调用时机（看代码注释）：BeanFactory
 *    标准初始化后（所有Bean定义已经被加载，但没有Bean实例还没有被初始化）。
 *    在 MyBeanFactoryPostProcessor#postProcessBeanFactory() 方法加断点，分析流程：
 *    1）IOC容器构建对象
 *    2）invokeBeanFactoryPostProcessors()执行 BeanFactoryPostProcessor
 *       a）如何找到 BeanFactoryPostProcessor 并执行它们方法：在IOC容器中找，和BeanPostProcessor类似，分成三类：
 *          实现 PriorityOrdered 接口、实现 Ordered 接口、普通BeanFactoryPostProcessor，按照类型顺序执行。
 *       b） AbstractApplicationContext#refresh()先调用 invokeBeanFactoryPostProcessors，最后再调用 finishBeanFactoryInitialization
 *           初始化单实例Bean，所以 BeanFactoryPostProcessor 是在初始化创建其他Bean组件前执行。
 * 2、BeanDefinitionRegistryPostProcessor 扩展了 BeanFactoryPostProcessor 接口，看代码注释，是在所有Bean定义信息将要（不是已经）
 *    被加载到 BeanFactoryPostProcessor 前执行，此时Bean实例还未创建。它优先于 BeanFactoryPostProcessor 执行，通过它可以给容器中额外添加一些组件。
 *    原理：
 *      a）IOC创建容器
 *      b）refresh() -> invokeBeanFactoryPostProcessors()
 *         1）从容器中找到所有的 BeanDefinitionRegistryPostProcessor 组件，依次触发它们的postProcessBeanDefinitionRegistry方法
 *         2）再调用 BeanDefinitionRegistryPostProcessor 调用它们的 postProcessBeanFactory()
 *      c）invokeBeanFactoryPostProcessors() -> invokeBeanFactoryPostProcessors() 执行其他 BeanFactoryPostProcessor#postProcessBeanFactory()
 * 3、ApplicationListener：监听容器发布的事件，事件驱动模型开发
 *    public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 *    监听 ApplicationEvent 及其子事件
 *    步骤：
 *    a）写一个事件监听器（ApplicationListener实现类），监听 ApplicationEvent 及其子类事件
 *       也可以通过 @EventListener 注解，让任意一个方法都可以监听到事件。
 *       查看 @EventListener 代码注释，是使用 EventListenerMethodProcessor 处理器解析方法上的 @EventListener 注解。
 *       EventListenerMethodProcessor 实现了 SmartInitializingSingleton 接口。
 *       SmartInitializingSingleton 执行时机：
 *          查看代码注释，它是在容器创建完成所有单实例Bean后执行（类似 ContextRefreshedEvent 的监听器）
 *          给  EventListenerMethodProcessor#afterSingletonsInstantiated 打断点，观察其调用过程：
 *          1）IOC容器创建对象并刷新容器 refresh()
 *          2）finishBeanFactoryInitialization() -> preInstantiateSingletons() 初始化剩下的单实例Bean。
 *             a）调用getBean()方法，创建所有的单实例Bean
 *             b）获取所有创建好的单实例Bean，判断是否是实现了 SmartInitializingSingleton 接口，如果是，调用其 afterSingletonsInstantiated() 方法
 *                该方法调用 processBean() 扫描所有Bean，找到带有 @EventListener 注解的方法，封装成 ApplicationListener，注册到容器。
 *          3）refresh() -> finishRefresh() 发布 ContextRefreshedEvent 事件（容器创建完成）
 *
 *
 *
 *
 *    b）把监听器加入到IOC容器
 *    c）只要容器中有相关事件的发布，我们就能监听到这个事件
 *       ContextRefreshedEvent：容器刷新完成，所有bean创建完成，会发布这个事件
 *       ContextClosedEvent：关闭容器发布这个事件
 *    d）发布自定义事件步骤： applicationContext.publishEvent
 *    f）容器关闭会发布ContextClosedEvent事件
 *    事件发布原理：
 *       ContextRefreshedEvent、IOCTest_Ext$1[source=我发布的事件]、ContextClosedEvent
 *       给事件监听器回调方法加断点。
 *       ContextRefreshedEvent 的监听：
 *       1）容器创建对象，refresh()
 *       2）finishRefresh() -> publishEvent(new ContextRefreshedEvent(this)
 *          事件发布流程：
 *            a）获取事件的多播器（EventMulticaster、派发器），getApplicationEventMulticaster()
 *            b）multicastEvent() 派发事件：
 *              for (final ApplicationListener<?> listener : getApplicationListeners(event, type))
 *              1）如果有Executor，可以支持使用线程池进行异步派发（SyncTaskExecutor / AsyncTaskExecutor）
 *              2）否则以同步方式直接调用 invokeListener() -> 调用listener.onApplicationEvent()
 *       IOCTest_Ext$1[source=我发布的事件] 的监听过程和 ContextRefreshedEvent 一样。
 *    【事件多播器(派发器)】
 *      1）容器创建对象refresh()
 *      2）初始化事件多播器： initApplicationEventMulticaster()初始化 ApplicationEventMulticaster
 *         检查容器是否有 applicationEventMulticaster Bean，如果有用它
 *         否则 new 一个 SimpleApplicationEventMulticaster 组件，注册到容器，我们就可以在其他组件要派发事件，自动注入 ApplicationEventMulticaster 。
 *    【容器中有哪些监听器】
 *      容器创建对象 refresh()
 *      registerListeners() -> getBeanNamesForType(ApplicationListener.class)
 *      将Listener注册到事件派发器中： getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName)
 **/
@Configuration
@ComponentScan("com.itguigu.ext")
public class ExtConfig {
   @Bean
   public Blue blue() {
     return new Blue();
   }
}