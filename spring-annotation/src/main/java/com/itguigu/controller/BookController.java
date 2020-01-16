package com.itguigu.controller;

import com.itguigu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author whz
 * @create 2020-01-09 7:48
 * @desc TODO: add description here
 **/
@Controller
public class BookController {
    @Autowired
    private BookService bookService;
}