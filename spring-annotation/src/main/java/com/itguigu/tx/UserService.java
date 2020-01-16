package com.itguigu.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author whz
 * @create 2020-01-12 14:37
 * @desc TODO: add description here
 **/
@Service
public class UserService {
  @Autowired
  private UserDao userDao;

  @Transactional //添加事务控制
  public void insertUser() {
    userDao.insert();
    System.out.println("插入完成...");
    //抛异常，如果没有事务控制机制，记录还是插入了。
    // int i = 10/0;
    throw new RuntimeException("test");
  }

}