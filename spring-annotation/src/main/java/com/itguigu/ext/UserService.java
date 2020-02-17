package com.itguigu.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author whz
 * @create 2020-01-14 7:40
 * @desc TODO: add description here
 **/
@Service("userService2")
public class UserService {
   @EventListener(classes={ApplicationEvent.class})
   public void listen(ApplicationEvent event) {
     System.out.println("UserService 监听到的事件：" +event);

   }
}