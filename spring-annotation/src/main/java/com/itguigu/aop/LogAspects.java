package com.itguigu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @author whz
 * @create 2020-01-10 6:36
 * @desc 切面类
 **/
@Aspect //告诉Spring当前类是切面类
public class LogAspects {
  // 抽取公共的切入点表达式
  // 1. 本切面类自己使用：只写方法执行时机注解+切入点方法名
  // 2. 其他切面类引用：要写方法执行时机注解+提供切入点切面类名+切入点方法名
  @Pointcut("execution(public int com.itguigu.aop.MathCalculator.*(..))") //带两个参数的
  public void pointCut() {

  }
  //指定在哪个方法执行时切入
  // @Before("public int com.itguigu.aop.MathCalculator.div(int, int)")
  // @Before("public int com.itguigu.aop.MathCalculator.*(...)")
  // 如果需要拿到方法更详细信息，需要在切面方法加入 JoinPoint 类型参数，并且JoinPoint必须作为第一个参数出现
  @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
      // System.out.println("除法运行...@Before 参数列表:");
    String methodName = joinPoint.getSignature().getName();
    System.out.println("@Before: " + methodName + "运行，参数："+ Arrays.asList(joinPoint.getArgs()));
  }
  @After("com.itguigu.aop.LogAspects.pointCut()")
  public void logEnd(JoinPoint joinPoint) {
    System.out.println("@After: ");
  }
  // JoinPoint类型参数一定要放在参数表的第一位，否则Spring不识别
  @AfterReturning(value="pointCut()",returning = "result") //指定哪个参数接收返回值
  public void logReturn(JoinPoint joinPoint, Object result) {
    System.out.println("@AfterReturning: " + joinPoint.getSignature().getName() + "return=" + result);
  }
  @AfterThrowing(value = "pointCut()", throwing = "exp")
  public void logException(JoinPoint joinPoint, Exception exp) {
    System.out.println("@AfterThrowing: " + joinPoint.getSignature().getName() + " throws " + exp);
  }
}