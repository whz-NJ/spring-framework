package com.itguigu.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author whz
 * @create 2020-01-09 10:28
 * @desc 自定义逻辑，返回需要导入的组件
 **/
public class MyImportSelector implements ImportSelector {

  // 返回值：导入到容器的组件的全类名列表
  // importingClassMetadata：当前标注@Import注解的类的所有注解信息
  @Override public String[] selectImports(
      AnnotationMetadata importingClassMetadata) {

    //不能返回null，否则报NPE
    return new String[] {"com.itguigu.bean.Blue", "com.itguigu.bean.Yellow"};
  }
}