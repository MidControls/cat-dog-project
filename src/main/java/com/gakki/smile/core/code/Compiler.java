package com.gakki.smile.core.code;

public interface Compiler {


    Class<?> compile(String code, ClassLoader classLoader);

}
