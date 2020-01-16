package com.itguigu.dao;

import org.springframework.stereotype.Repository;

/**
 * @author whz
 * @create 2020-01-09 7:47
 * @desc TODO: add description here
 **/
@Repository
public class BookDao {
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  private String label = "1";

  @Override public String toString() {
    return "BookDao{" + "label='" + label + '\'' + '}';
  }
}
