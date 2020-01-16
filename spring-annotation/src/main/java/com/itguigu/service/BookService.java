package com.itguigu.service;

import com.itguigu.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * @author whz
 * @create 2020-01-09 7:47
 * @desc TODO: add description here
 **/
@Service
public class BookService {
  // @Qualifier("bookDao")
  // @Autowired(required = false)
  // @Resource(name="bookDao2")
  @Inject
  private BookDao bookDao;

  public void print() {
    System.out.println(bookDao);
  }

  @Override public String toString() {
    return "BookService{" + "bookDao2=" + bookDao + '}';
  }
}