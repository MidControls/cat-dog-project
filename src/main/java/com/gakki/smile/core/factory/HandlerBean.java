package com.gakki.smile.core.factory;

import com.gakki.smile.core.annotation.web.RequestMethod;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;


/**
 * 封装处理器
 *
 * @author: mid_control
 * @date:  2021-02-01 下午9:18
 */
public class HandlerBean {

    private Handler<RoutingContext> handler;

    private RequestMethod requestMethod;

    private boolean block;

    public HandlerBean(){

    }

    public HandlerBean(Handler<RoutingContext> handler, RequestMethod requestMethod,boolean block) {
        this.handler = handler;
        this.requestMethod = requestMethod;
        this.block=block;
    }

    public Handler<RoutingContext> getHandler() {
        return handler;
    }

    public void setHandler(Handler<RoutingContext> handler) {
        this.handler = handler;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}
