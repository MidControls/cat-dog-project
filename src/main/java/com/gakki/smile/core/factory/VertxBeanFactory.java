package com.gakki.smile.core.factory;


import com.gakki.smile.core.annotation.VertxController;
import com.gakki.smile.core.annotation.web.RequestMethod;
import com.gakki.smile.core.annotation.web.RequestRouting;
import com.gakki.smile.core.code.ClassCodeGenerator;
import com.gakki.smile.core.code.Compiler;
import com.gakki.smile.core.code.jdk.JdkCompiler;
import com.gakki.smile.utils.ClassUtils;



import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class VertxBeanFactory implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    public static final Map<String, String> beanNameMap = new ConcurrentHashMap<>();

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static Map<String, Object> classObjectMap = new ConcurrentHashMap<>(256);

    //  public static final Map<String, Function<RoutingContext, Future<Object>>> functionMap = new ConcurrentHashMap<>(256);

    public static final Map<String, HandlerBean> handlerMap = new ConcurrentHashMap<>(256);

    private static final Compiler compiler = new JdkCompiler();

    public static void doFilterClass(Class<?> clazz) throws Exception {
        //获取取得注解的clazz
        if (clazz.isAnnotationPresent(VertxController.class)) {
            VertxController clazzDeclaredAnnotation = clazz.getDeclaredAnnotation(VertxController.class);
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                RequestRouting requestRouting = AnnotatedElementUtils.findMergedAnnotation(declaredMethod, RequestRouting.class);
                if (requestRouting != null) {
                    //  RequestRouting methodAnnotation = declaredMethod.getAnnotation(RequestRouting.class);
                    String clazzValue = clazzDeclaredAnnotation.value();
                    String methodValue = requestRouting.value();
                    String path = "/";
                    if (!clazzValue.isBlank()) {
                        path = path + clazzValue.replaceAll("/", "") + "/" + methodValue.replaceAll("/", "");
                    } else {
                        path = path + methodValue.replaceAll("/", "");
                    }

                    String clazzName = clazz.getName();
                    String methodName = declaredMethod.getName();
                    Handler<RoutingContext> handler = doCreateBean(path, clazzName, methodName);

                    // 封装对象
                    HandlerBean handlerBean = new HandlerBean(handler, requestRouting.method(), requestRouting.block());
                    // save map
                    handlerMap.putIfAbsent(path, handlerBean);
                }
            }
        }

    }


    private static ClassLoader findClassLoader() {
        return ClassUtils.getClassLoader(VertxBeanFactory.class);
    }


    private static Handler<RoutingContext> doCreateBean(String path, String clazzName, String methodName) throws Exception {
        if (handlerMap.containsKey(path)) {
            throw new RuntimeException("path 重复");
        }

        int num = atomicInteger.incrementAndGet();
        char[] chars = methodName.toCharArray();
        chars[0] = (char) (chars[0] - 32);
        String createClassName = new String(chars) + num;
        ClassCodeGenerator codeGenerator = new ClassCodeGenerator(createClassName, clazzName, methodName);
        String code = codeGenerator.generate();
        Class<?> clazz = compiler.compile(code, findClassLoader());
        Handler<RoutingContext> handler = (Handler<RoutingContext>) clazz.getDeclaredConstructor().newInstance();
        return handler;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Set<Map.Entry<String, String>> entries = beanNameMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            Object bean = applicationContext.getBean(key);
            classObjectMap.put(value, bean);
            try {
                doFilterClass(bean.getClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
