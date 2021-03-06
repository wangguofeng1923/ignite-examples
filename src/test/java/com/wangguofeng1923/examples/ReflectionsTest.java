package com.wangguofeng1923.examples;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.base.Predicate;

public class ReflectionsTest {
	@MyAnnotation
public static void main(String[] args) {


	
	FilterBuilder filterBuilder = new FilterBuilder();

    for (String packName : "com.wangguofeng1923".split(",")) {
        filterBuilder = filterBuilder.includePackage(packName);//定义要扫描的包
    }
    Predicate<String> filter = filterBuilder;//过滤器

    //里面放的是这些包所在的资源路径
    Collection<URL> urlTotals = new ArrayList<URL>();
    for (String packName : "".split(",")) {
        Collection<URL> urls = ClasspathHelper.forPackage(packName);
        urlTotals.addAll(urls);
    }
	 Reflections reflections = new Reflections(new ConfigurationBuilder()

             //.filterInputsBy(filter)
             .setScanners(

                     new SubTypesScanner().filterResultsBy(filter),
                     new TypeAnnotationsScanner().filterResultsBy(filter),
//                     new FieldAnnotationsScanner().filterResultsBy(filter),
                     new MethodAnnotationsScanner().filterResultsBy(filter)
//                     new MethodParameterScanner().filterResultsBy(filter)

             ).setUrls(urlTotals));
	 
	 
	   //获取方法上待MyAnotation注解的所有的方法
//	 reflections.getTypesAnnotatedWith(annotation)
	 Set<Class<?>> clzSet= reflections.getTypesAnnotatedWith(MyAnnotation.class);
	for(Class<?> clz:clzSet) {
		System.out.println(clz);
		
		System.out.println(clz.isEnum());
		if(clz.isEnum()) {
			Field []fields=clz.getFields();
			for(Field f:fields) {
				System.out.println(f.getName());
			}
		}
	}

  Set<Method> methods = reflections.getMethodsAnnotatedWith(MyAnnotation.class);
  for(Method m : methods){
      System.out.println(m.getName());
  }
	
     
}
	public static void test(){
		
//		Reflections reflections = new Reflections("my.project");
//
//		Set<Class<? extends SomeType>> subTypes = reflections.getSubTypesOf(SomeType.class);
//
//		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(SomeAnnotation.class);
		
	}
}
