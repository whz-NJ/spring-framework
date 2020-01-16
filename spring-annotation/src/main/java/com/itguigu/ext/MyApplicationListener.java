package com.itguigu.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author whz
 * @create 2020-01-14 6:56
 * @desc TODO: add description here
 **/
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
  //当容器中发布此事件后，该方法触发
  @Override public void onApplicationEvent(ApplicationEvent event) {
    System.out.println("收到事件：" + event);
  }
}
