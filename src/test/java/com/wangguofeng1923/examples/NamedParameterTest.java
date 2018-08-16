package com.wangguofeng1923.examples;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.DefaultParameterNameDiscoverer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.runtime.Desc;
import javassist.util.proxy.RuntimeSupport;

public class NamedParameterTest {
	public static void main(Integer[] bbb) {
		System.out.println("test");
	}
public static void main(String[] aaa) throws ReflectiveOperationException, SecurityException, IOException, NotFoundException {
//	Class<Company>clz=Company.class;
//	Method method=clz.getMethod("setName", String.class);
//	DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
//	String[] parameterNames = discover.getParameterNames(method);
//	System.out.println(Arrays.asList(parameterNames));
	StackTraceElement[] ex=Thread.currentThread() .getStackTrace();
	StackTraceElement sta=ex[ex.length-1];
	int lineNumber=sta.getLineNumber();
	System.out.println(lineNumber);
	String className=sta.getClassName();
	String methodName=sta.getMethodName();
//	System.out.println("methodName:"+methodName);
//	 System.out.println("Get method line number with javassist\n");
     ClassPool pool = ClassPool.getDefault();
     CtClass cc = pool.get(className);
     CtMethod []methods =cc.getDeclaredMethods();
     Map<String,Integer>methodInfoList=  Stream.of(methods).map(CtMethod::getMethodInfo).filter(m->{return m.getName().equals(methodName);})
     .collect(Collectors.toMap( MethodInfo::getDescriptor,minfo->minfo.getLineNumber(0)));
     
     Map<Method,Integer>methodList=   Stream.of(Class.forName(className).getMethods()).filter(m->m.getName().equals(methodName))
    		 .collect(Collectors.toMap(Function.identity(), m->{
    			 String desc=RuntimeSupport.makeDescriptor(m);
    			 return methodInfoList.get(desc);
    		 }));
     methodList.forEach((k,v)->{
    	 System.out.println(k.getName());
    	 System.out.println(RuntimeSupport.makeDescriptor(k));
    	 System.out.println(v);
    	 
    		DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
    		String[] parameterNames = discover.getParameterNames(k);
    		System.out.println(Arrays.asList(parameterNames));
    	 
    	 
     });
    
    }
    
  
     
 
// String methodDescriptor = methodDescriptorReference.get();
//
// if (methodDescriptor == null) {
//     throw new RuntimeException("Could not find line " + stackTraceLineNumber);
// }
}
