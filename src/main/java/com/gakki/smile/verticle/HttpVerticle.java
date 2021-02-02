package com.gakki.smile.verticle;


import com.gakki.smile.core.annotation.web.RequestMethod;
import com.gakki.smile.core.factory.HandlerBean;
import com.gakki.smile.core.factory.VertxBeanFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;


import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.impl.logging.Logger;

public class HttpVerticle extends AbstractVerticle {

    private static final Logger LOGGER= LoggerFactory.getLogger(HttpVerticle.class);


    private static final Integer port = 9999;


//    public static PgPool pgPool;
//
//    private final PgConnectOptions options = new PgConnectOptions()
//            .setUser("postgres")
//            .setPassword("postgres")
//            .setDatabase("pet")
//            .setHost("47.114.137.215")
//            .setPort(5432);


    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);

        // 注册路由
        registerRoute(router);

        LOGGER.info("start");

        router.route().failureHandler(routingContext -> {
            routingContext.failure().printStackTrace();
        });

        vertx.createHttpServer().requestHandler(router).listen(port, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 9999");
            } else {
                startPromise.fail(http.cause());
            }
        });
    }


    private void registerRoute(Router router) {
        VertxBeanFactory.handlerMap.forEach((path, handlerBean) -> {
            RequestMethod requestMethod = handlerBean.getRequestMethod();
            if (requestMethod == RequestMethod.GET && handlerBean.isBlock()) {
                router.get(path).blockingHandler(handlerBean.getHandler());
            }
            if (requestMethod == RequestMethod.POST && handlerBean.isBlock()) {
                router.post(path).blockingHandler(handlerBean.getHandler());
            }
            if (requestMethod == RequestMethod.PUT && handlerBean.isBlock()) {
                router.put(path).blockingHandler(handlerBean.getHandler());
            }
            if (requestMethod == RequestMethod.DELETE && handlerBean.isBlock()) {
                router.delete(path).blockingHandler(handlerBean.getHandler());
            }
            if (requestMethod == RequestMethod.ALL && handlerBean.isBlock()) {
                router.route(path).blockingHandler(handlerBean.getHandler());
            }

            if (requestMethod == RequestMethod.GET && !handlerBean.isBlock()) {
                router.get(path).handler(handlerBean.getHandler());
            }
            if (requestMethod == RequestMethod.POST && !handlerBean.isBlock()) {
                router.post(path).handler(handlerBean.getHandler());
            }
            if (requestMethod == RequestMethod.PUT && !handlerBean.isBlock()) {
                router.put(path).handler(handlerBean.getHandler());
            }
            if (requestMethod == RequestMethod.DELETE && !handlerBean.isBlock()) {
                router.delete(path).handler(handlerBean.getHandler());
            }
            if (requestMethod == RequestMethod.ALL && !handlerBean.isBlock()) {
                router.route(path).handler(handlerBean.getHandler());
            }
        });
    }


}
