package com.gakki.smile.core.annotation.web;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteRouting {

    @AliasFor(annotation = RequestRouting.class)
    String value() default "";


    @AliasFor(annotation = RequestRouting.class)
    boolean block() default true;

}
