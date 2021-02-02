package com.gakki.smile.core.annotation.web;

import com.gakki.smile.core.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestRouting {

    String value() default "";

    boolean block() default true;

    RequestMethod method() default RequestMethod.ALL;

}
