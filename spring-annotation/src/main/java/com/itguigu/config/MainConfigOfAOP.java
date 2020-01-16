package com.itguigu.config;

import com.itguigu.aop.LogAspects;
import com.itguigu.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author whz
 * @create 2020-01-09 21:29
 * @desc AOP:能在程序运行期间，动态地将某段代码注入到指定方法指定位置运行的编程方式
 *           底层是动态代理
 *       1. 导入AOP模块（spring-aspects）
 *       2. 创建业务逻辑类(MathCalculator)，在业务逻辑运行时，在方法运行前/后/异常打印日志
 *       3. 定义一个日志切面类（LogAspects），需要感知业务逻辑MathCalculator.div运行到哪一步，然后执行切面的通知方法。
 *          通知方法：
 *              前置通知：业务逻辑执行前执行：@Before
 *              后置通知：业务逻辑执行结束后执行（不论方法正常结束，还是异常结束，都调用）：@After
 *              返回通知：业务逻辑正常返回时执行：@AfterReturning
 *              异常通知：业务逻辑出现异常时执行：@AfterThrowing
 *              环绕通知：动态代理，手动推进业务逻辑运行（joinPoint.procced()）：@Aroud
 *       4. 给切面类方法添加通知方法执行时机注解（@Before/@After...）
 *       5. 将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 *       6. 必须告诉Spring哪个类是切面类（给切面类加注解@Aspect）
 *       7. 给配置类中加入 @EnableAspectJAutoProxy （启用基于AspectJ注解的AOP功能）和：<aop:aspectj-autoproxy></aop:aspectj-autoproxy> 配置类似
 *          在Spring里，有很多的@EnableXXX功能，都是开启某一项功能
 *
 *       概括起来有三步：
 *       1）业务逻辑组件和切面类都加入IOC容器，并告诉Spring哪个是切面类（@Aspect）
 *       2）切面类上每个通知方法，标注通知注解，告诉Spring注解何时运行（切入点表达式，参考官方文档）
 *       3）开启基于注解的AOP模式
 *
 *       AOP原理【看给容器中注册了什么组件，什么时候工作，工作时的功能】：
 *           @EnableAspectJAutoProxy
 *       1）@EnableAspectJAutoProxy 注解是什么？
 *          @Import(AspectJAutoProxyRegistrar.class) 它实现了 ImportBeanDefinitionRegistrar 接口，给容器中注册Bean
 *          给 AspectJAutoProxyRegistrar#registerBeanDefinitions 打断点，debug
 *          发现给容器中注册bean：internalAutoProxyCreator ==> AnnotationAwareAspectJAutoProxyCreator
 *       2） AnnotationAwareAspectJAutoProxyCreator 类结构：
 *             --> AspectJAwareAdvisorAutoProxyCreator
 *               --> AbstractAdvisorAutoProxyCreator
 *                 --> AbstractAutoProxyCreator
 *                   --> 实现了 SmartInstantiationAwareBeanPostProcessor 和 BeanFactoryAware 接口
 *           BeanPostProcessor：在Bean初始化完成前后做实现 BeanFactoryAware：注入BeanFactory
 *       3）在基类 AbstractAutoProxyCreator.setBeanFactory AbstractAutoProxyCreator#postProcessBeforeInitialization
 *           子类 AbstractAdvisorAutoProxyCreator 重写了 setBeanFactory 打断点-> initBeanFactory
 *           子类 AspectJAwareAdvisorAutoProxyCreator 中没有和 BeanPostProcessor 和 BeanFactoryAware 相关方法
 *           子类 AnnotationAwareAspectJAutoProxyCreator 重写了 initBeanFactory 打断点
 *           并给 MainConfigOfAOP.mathCalculator() 和 MainConfigOfAOP.logAspects 打断点
 *       流程：
 *           执行用例，执行到 AbstractAdvisorAutoProxyCreator#setBeanFactory()方法端点，分析堆栈：
 *           a）测试用例代码：传入配置类（注册类里有@EnableAspectJAutoProxy 注解，注册了internalAutoProxyCreator Bean定义，以及其他系统定义的后置处理器定义），创建IOC容器
 *           b）注册配置类 ，刷新容器
 *           c）AbstractApplicationContext#registerBeanPostProcessors -> PostProcessorRegistrationDelegate.registerBeanPostProcessors
 *              1) 先获取IOC容器中已经定义了的需要创建对象的所有BeanPostProcessor
 *              2）给容器（beanFactory）中加入其他的BeanPostProcessor
 *              3）优先注册实现类了PriorityOrdered的BeanPostProcessor
 *              4）在给容器中注册实现了Ordered接口的BeanPostProcessor，具体过程：
 *                 a）doCreateBean() 注册BeanPostProcessor就是创建BeanPostProcessor对象，保存在IOC容器里：
 *                    1）调用  beanFactory.getBean() 创建Bean实例（这里就是 internalAutoProxyCreator -> AnnotationAwareAspectJAutoProxyCreator 这个Bean）
 *                    2）populateBean，给Bean各个属性赋值
 *                    3）initialzeBean，初始化Bean（BeanPostProcessor就是在初始化Bean前后工作的）
 *                       a） invokeAwareMethods()处理Aware接口的方法回调
 *                       b） applyBeanPostProcessorsBeforeInitialization()，调用所有Bean后置处理器的 postProcessBeforeInitialization() 方法
 *                       c） invokeInitMethods()，执行当前Bean自定义的init-method初始化方法
 *                       d） applyBeanPostProcessorsAfterInitialization()，调用所有Bean后置处理器的 postProcessAfterInitialization()
 *                    4） BeanPostProcessor（AnnotationAwareAspectJAutoProxyCreator）创建完成，调用initBeanFactory方法，创建 aspectJAdvisorsBuilder 成员
 *                 b）sortPostProcessors ，给BeanPostProcessor排序
 *                 c) registerBeanPostProcessors，把所有的 beanPostProcess 注册到IOC容器里
 *              5）注册没有实现优先级接口的 BeanPostProcessor
 *  *******以上是创建和注册 AnnotationAwareAspectJAutoProxyCreator 的过程，它是一个BeanPostProcess，在后续其他组件创建时，可以拦截创建过程******
 *  下面看AnnotationAwareAspectJAutoProxyCreator拦截其他Bean创建过程，重新debug，来到断点 AbstractAutoProxyCreator#postProcessBeforeInstantiation()
 *  注意和 BeanPostProcessor.postProcessBeforeInitialization 不同的（Initialization ----普通BeanPostProcessor 和 Instantiation---给普通Bean生成代理的BeanPostProcessor）
 *  容器refresh()方法后续调用：
 *           d) finishBeanFactoryInitialization，完成BeanFactory初始化工作：创建剩下的单实例Bean
 *             1）preInstantiateSingletons 遍历获取容器中所有Bean，依次创建对象
 *                getBean -> doGetBean -> getSigleton
 *                  a）从缓存中获取当前Bean，如果能获取到，说明bean之前被创建过，直接使用，否则再创建
 *                     只要是创建好的Bean，都会被缓存起来（保证只有单实例的Bean）
 *                  b）createBean：创建bean：AnnotationAwareAspectJAutoProxyCreator 会在任何Bean创建之前先尝试返回Bean的实例
 *                    【BeanPostProcessor是在Bean对象创建完成前后调用的】
 *                    【InstantiationAwareBeanPostProcessor 是在创建Bean实例之前先尝试用后置处理器返回对象】
 *                    1）resolveBeforeInstantiation，createBean里在该方法调用前有注释：
 *                       希望后置处理器在此返回一个代理对象，如果有返回代理对象，就使用，否则执行2）步骤
 *                       a）后置处理器先尝试返回对象：
 *                          bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName)
 *                              拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor类型，则调用 postProcessBeforeInstantiation 方法
 *                    2）doCreateBean，真正的创建一个Bean，流程和前面分析一致。
 *********************下面分析 AbstractAutoProxyCreator#postProcessBeforeInstantiation **********
 * AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】作用：
 * 1）每个Bean创建之前调用 postProcessBeforeInstantiation 方法，
 *    我们只关心 mathCalculator 和 logAspects 这两个Bean创建时postProcessBeforeInstantiation方法的处理，其他Bean时，断点放行。
 *    a）判断当前Bean是否在 advisedBeans 成员变量里（用来保存需要增强的Bean集合---需要切面增强的业务Bean）
 *    b）AnnotationAwareAspectJAutoProxyCreator.isInfrastructureClass 判断是否是基础bean：Pointcut、Advice、Advisor 或者是否是切面：this.aspectJAdvisorFactory.isAspect
 *    c）shouldSkip：
 *        1）获取候选的增强器Advisor（切面里的方法）
 *           判断每个增强器是否是 AspectJPointcutAdvisor 类型（当前不是，调用父类shouldSkip，直接返回false）
 *        2）对于业务Bean mathCalculator，shouldSkip返回false
 *    d）postProcessBeforeInstantiation 返回null
 * 2）创建当前业务Bean对象
 *    调用 AbstractAutoProxyCreator#postProcessAfterInitialization，
 *        return wrapIfNecessary(bean, beanName, cacheKey)
 *        a) 获取当前业务bean的增强器（通知方法）
 *           1） 找到所有候选增强器
 *           2） 找到能在当前Bean使用的增强器（就是哪些通知方法需要切入当前Bean）
 *           3） 给增强器排序
 *        b) 保存当前Bean到advisedBeans里，
 *        c） 如果当前Bean需要增强，创建当前Bean的代理对象（AbstractAutoProxyCreator#createProxy）
 *            1）获取所有增强器，保存到proxyFactory
 *            2) 创建代理对象，Spring自动决定（DefaultAopProxyFactory#createAopProxy）
 *              JdkDynamicAopProxy（---业务类有实现接口）
 *              ObjenesisCglibAopProxy（---业务没有实现接口）
 *        d）wrapIfNecessary()给容器中返回使用cglib创建的增强后的代理对象
 *        f）以后容器中获取的就是组件的代理对象，执行代理对象的通知方法流程
 * 3）目标方法执行
 *    在测试用例的 calculator.div 调用处加断点，执行到断点，观察calculator类型（是cglib增强后的代理对象），这个对象保存了增强器信息，目标对象，等等。
 *    再在 CglibAopProxy.DynamicAdvisedInterceptor#intercept() 设置断点：
 *      a) getInterceptorsAndDynamicInterceptionAdvice ，根据advised（ProxyFactory类型）获取拦截器链chain，
 *         1）创建 interceptorList （一个默认的ExposeInvocationInterceptor，以及4个用户自定义的增强器）
 *         2）遍历所有增强器，转换为 MethodInterceptor （使用 AdvisorAdapter 将增强器转换为MethodInterceptor）。
 *      b）如果chain为空，调用ReflectiveMethodInvocation#invokeJoinpoint()直接执行业务目标方法
 *      c）如果chain不为空，把需要执行目标对象/目标方法/chain等信息传入创建的CglibMethodInvocation 对象，并调用其proceed方法执行。
 *    ReflectiveMethodInvocation#proceed() 方法分析：
 *         a) 通过 currentInterceptorIndex 获取当前Interceptor，如果没有Interceptor了，就调用 invokeJoinpoint ，调用业务方法。
 *         b) 如果有Interceptor，则调用Interceptor的invoke方法（依次有 ExposeInvocationInterceptor/AspectJAfterThrowingAdvice/AfterReturningAdviceInterceptor/AspectJAfterAdvice/MethodBeforeAdviceInterceptor/）
 *            这些Advice的Invoke方法会先或后调用自己advice的方法，再调用 ReflectiveMethodInvocation#proceed() 调用下一个Interceptor方法。
 *            AspectJAfterThrowingAdvice 里有try/catch，AfterReturningAdviceInterceptor里没有try/catch，所以业务方法有异常抛出时，会被AspectJAfterThrowingAdvice的invoke方法里捕获，执行异常通知方法，通知后，继续把异常往上抛。*
 *   总结：
 *    1） 用@EnableAspectJAutoProxy注解，开启AOP功能
 *    2） @EnableAspectJAutoProxy 会给容器注册一个 AnnotationAwareAspectJAutoProxyCreator 类型Bean
 *    3) AnnotationAwareAspectJAutoProxyCreator 是个后置处理器
 *    4）容器创建过程：
 *       a） registerBeanPostProcessors 注册后置处理器，创建 AnnotationAwareAspectJAutoProxyCreator 对象
 *       b） finishBeanFactoryInitialization 初始化剩下的单实例Bean
 *           1）创建业务逻辑Bean和切面Bean
 *           2）AnnotationAwareAspectJAutoProxyCreator 拦截业务Bean组件创建过程
 *           3）在组件创建完成之后，判断业务Bean是否需要增强 postProcessAfterInitialization
 *              如果是，把切面通知方法包装成增强器（Advisor），给业务Bean创建一个代理对象（包含所有增强器）
 *    5）执行目标方法
 *      a）代理对象执行目标方法
 *      b）CglibAopProxy.interceptor()
 *         1）得到目标方法的拦截器链（增强器包装为拦截器MethodInterceptor）
 *         2）利用拦截器的链式机制，依次进入每个拦截器执行
 *         3）效果
 *            业务方法如果正常执行：前置通知 -> 目标方法 -> 后置通知 -> 返回通知
 *            如果业务方法抛出异常：前置通知 -> 目标方法 -> 后置通知 -> 异常通知
 *
 **/
@Configuration
@EnableAspectJAutoProxy
public class MainConfigOfAOP {
  @Bean //业务逻辑类加入容器
  public MathCalculator mathCalculator() {
    return new MathCalculator();
  }

  @Bean //切面类加入容器
  public LogAspects logAspects() {
    return new LogAspects();
  }

}