package com.wangguofeng1923.examples;


public abstract class  TestEnum<T> {
	protected <E>E[] values() {
		return null;
		
	}
	abstract  T dd();
}
class Test extends TestEnum<Object>{

	@Override
	Object dd() {
		// TODO Auto-generated method stub
		return null;
	}
}