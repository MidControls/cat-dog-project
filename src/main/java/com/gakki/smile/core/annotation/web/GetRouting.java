package com.gakki.smile.core.annotation.web;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestRouting(method = RequestMethod.GET)
public @interface GetRouting {

    @AliasFor(annotation = RequestRouting.class)
    String value() default "";

    @AliasFor(annotation = RequestRouting.class)
    boolean block() default true;



}
