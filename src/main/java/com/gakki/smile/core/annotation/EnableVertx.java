package com.gakki.smile.core.annotation;

import com.gakki.smile.core.registrar.VertxConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(VertxConfigRegistrar.class)
public @interface EnableVertx {


    String[] basePackages() default {};

    String[] value() default {};

    Class<?>[] basePackageClasses() default {};





}
