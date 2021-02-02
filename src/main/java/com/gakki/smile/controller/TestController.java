package com.gakki.smile.controller;

import com.gakki.smile.core.annotation.VertxController;
import com.gakki.smile.core.annotation.web.GetRouting;
import com.gakki.smile.entity.Book;
import com.gakki.smile.service.UserService;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;




@VertxController("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @GetRouting(value = "/user")
    public Book user(RoutingContext routingContext) {
        System.out.println("bbb");
        System.out.println(Thread.currentThread().getName());
        Book book=userService.findBookById();
        return book;
    }


}
