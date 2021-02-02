package com.gakki.smile;

import com.gakki.smile.config.SpringConfig;
import com.gakki.smile.controller.TestController;
import com.gakki.smile.core.factory.VertxBeanFactory;
import com.gakki.smile.verticle.HttpVerticle;
import io.vertx.core.Vertx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.Set;


public class CatAndDogApplication {


    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringConfig.class);

        applicationContext.start();

        // 启动vertx
        Vertx vertx = Vertx.vertx();
        HttpVerticle httpVerticle = new HttpVerticle();
        vertx.deployVerticle(httpVerticle);
    }


}
