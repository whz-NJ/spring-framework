package com.itguigu;

import com.itguigu.tx.TxConfig;
import com.itguigu.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author whz
 * @create 2020-01-12 14:52
 * @desc TODO: add description here
 **/
public class IOCTest_Tx {
  @Test
  public void test01() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        TxConfig.class);
    UserService userService = applicationContext.getBean(UserService.class);
    userService.insertUser();
    applicationContext.close();
  }

}