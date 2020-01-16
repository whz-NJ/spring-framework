package com.itguigu.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author whz
 * @create 2020-01-09 8:37
 * @desc TODO: add description here
 **/
public class MyTypeFilter implements TypeFilter {
  // metadataReader 当前扫描到的类信息
  // metadataReaderFactory 可以获取到其他任何类信息
  @Override public boolean match(MetadataReader metadataReader,
      MetadataReaderFactory metadataReaderFactory) throws IOException {
    // 获取当前类注解信息
    AnnotationMetadata annotationMetadata = metadataReader
        .getAnnotationMetadata();
    // 当前扫描类的类信息
    ClassMetadata classMetadata = metadataReader.getClassMetadata();
    String className = classMetadata.getClassName();
    if(className.contains("er")) {
      return true;
    }
    // 当前类只有信息（类路径）
    metadataReader.getResource();
    return false;
  }
}