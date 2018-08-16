package com.wangguofeng1923.examples.bytebuddy;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.jetbrains.annotations.TestOnly;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;

public class Snippet {
	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		Field[]fields=BookInfo.class.getDeclaredFields();
		DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
				  .subclass(UserInfo.class).name("dynamic.UserInfo")
				  .annotateType(new TestAnnotation() {
					@Override
					public Class<? extends Annotation> annotationType() {
						return TestAnnotation.class;
					}})
				  .defineProperty("hahaProperty", String.class)
				 .defineField("dynamicField", String.class, 1)
				 .defineMethod("dynamicMethod", String.class, 1)
//				 .intercept(implementation)
				 .intercept(MethodDelegation.to(UserInfo.class))
//				 .intercept(FixedValue.value("transformed"))
				 // .defineMethod("dynamicMethod", String.class, 1).withParameter(Void.TYPE).
				  .make();
		
		Class<?>clz=dynamicType.load(Snippet.class.getClassLoader()).getLoaded();
		Object obj=clz.newInstance();
		System.out.println(obj.getClass());
		System.out.println(obj instanceof UserInfo);
		System.out.println(obj.getClass().getAnnotations().length);
		dynamicType.saveIn(new File("d:\\temp"));
		
	}
}

