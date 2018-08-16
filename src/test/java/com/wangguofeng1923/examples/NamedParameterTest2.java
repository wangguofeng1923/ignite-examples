package com.wangguofeng1923.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class NamedParameterTest2 {

	private static String getParameters(CtMethod cm) throws NotFoundException {

		if(cm.getName().startsWith("lambda$")) {
			return null;
		}
	     MethodInfo methodInfo = cm.getMethodInfo();
	     CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
	     LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
	     String[] paramNames = new String[cm.getParameterTypes().length];
	     int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
	     
	     
	     String methodName=cm.getName();
	  
	     
	    List<String>parameterList=new ArrayList<>();
	 	for (int i = 0; i < paramNames.length; i++) {
	 		CtClass ptype=cm.getParameterTypes()[i];
	 		String[]strs=new String[2];
	 		strs[0]=ptype.getName();
	 	//	 System.out.print(ptype.getName());
//	 		String desc=attr.descriptor(i);
//	 		System.out.println(desc);
	 		//System.out.println(Desc.getType(desc));
	 		
	 		//parameterNames[attr.index(i)] = attr.variableName(i);
	 		
	 		
			String paramName = attr.variableName(i + pos);
			strs[1]=paramName;
			parameterList.add(String.join( " ",strs));
			//System.out.print(paramName);
	 	}
	 	
	    StringBuffer sb=new StringBuffer()
	    		 .append(methodName)
	    		 .append("(")
	    		 .append(String.join(",", parameterList.toArray(new String[0])))
	    		 .append(")");
	    		 ;
	    
	 	  return sb.toString();

	}
public static void main(String[] args) throws ReflectiveOperationException, SecurityException, IOException, NotFoundException {
//	Class<Company>clz=Company.class;
//	Method method=clz.getMethod("setName", String.class);
//	DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
//	String[] parameterNames = discover.getParameterNames(method);
//	System.out.println(Arrays.asList(parameterNames));
	StackTraceElement[] ex=Thread.currentThread() .getStackTrace();
	StackTraceElement sta=ex[ex.length-1];
	int lineNumber=sta.getLineNumber();
	
	//System.out.println(lineNumber);
	String className=sta.getClassName();
	String methodName=sta.getMethodName();
//	System.out.println("methodName:"+methodName);
//	 System.out.println("Get method line number with javassist\n");
     ClassPool pool = ClassPool.getDefault();
     CtClass cc = pool.get(sta.getClassName());
     CtMethod []methods =cc.getDeclaredMethods();
     Stream.of(methods).forEach(cm->{
    	 try {
    		 
    		 if(cm.getMethodInfo().getName().equals(methodName)) {
        		 
    			 String method=	getParameters(cm);
        		 Integer line=cm.getMethodInfo().getLineNumber(0);
        		 
        		 if(method!=null&&lineNumber>=line) {
            		 
            		 System.out.println(method+" Line:["+line+"]");
        		 }
    		 }

    		 
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
    	 
     });
    
    }
public static void main(Integer[] bbb,Long cc) {
	System.out.println("test");
}
}
