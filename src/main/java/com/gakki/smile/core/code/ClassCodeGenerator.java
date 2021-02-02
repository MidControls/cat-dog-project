package com.gakki.smile.core.code;

import com.gakki.smile.core.code.javassist.JavassistCompiler;
import com.gakki.smile.core.factory.VertxBeanFactory;
import com.gakki.smile.utils.ClassUtils;

import java.lang.reflect.Method;

/**
 * 用于自动生成class的类
 *
 * @author: mid_control
 * @date: 2021-02-01 上午10:05
 */
public class ClassCodeGenerator {


    private static final String HANDLER_PACKAGE = "package com.gakki.smile.core.Handler;\n";

    private static final String IMPORT_CLASS =
            "import io.vertx.core.buffer.Buffer;\n" +
            "import io.vertx.core.http.HttpHeaders;\n" +
            "import io.vertx.core.http.HttpServerResponse;\n" +
            "import io.vertx.core.json.Json;\n" +
            "import io.vertx.ext.web.RoutingContext;\n";


    private static final String CODE_CLASS_DECLARATION = "public class %s implements io.vertx.core.Handler<RoutingContext> {\n";


    private static final String METHOD_NAME = "public void handle(RoutingContext routingContext) {\n";

    private static final String METHOD_CODE_PRE = "HttpServerResponse response = routingContext.request().response();\n";

    private static final String METHOD_CODE_OBJ = "%s obj = (%s) com.gakki.smile.core.factory.VertxBeanFactory.classObjectMap.get(\"%s\");\n";

    private static final String METHOD_CODE_END = "Buffer buffer = Json.encodeToBuffer(obj.%s(routingContext));\n" +
            "        response .putHeader(HttpHeaders.CONTENT_TYPE, \"application/json; charset=utf-8\");\n" +
            "        response.end(buffer);\n";


    private final String currClassName;

    private final String className;

    private final String methodName;

    public ClassCodeGenerator(String currClassName, String className, String methodName) {
        this.currClassName = currClassName;
        this.className = className;
        this.methodName = methodName;
    }


    /**
     * 生成方法
     *
     * @return classCode
     */
    public String generate() {
        return HANDLER_PACKAGE +
                IMPORT_CLASS +
                String.format(CODE_CLASS_DECLARATION, currClassName) +
                METHOD_NAME +
                METHOD_CODE_PRE +
                String.format(METHOD_CODE_OBJ, className, className, className) +
                String.format(METHOD_CODE_END, methodName) +
                "}\n" +
                "}";
    }

    private static ClassLoader findClassLoader() {

        return ClassUtils.getClassLoader(ClassCodeGenerator.class);
    }

    public static void main(String[] args) {
        ClassCodeGenerator classCodeGenerator = new ClassCodeGenerator("AutoHandler1", "com.gakki.smile.controller.TestController", "user1");
        String code = classCodeGenerator.generate();

        JavassistCompiler javassistCompiler = new JavassistCompiler();
        Class<?> compile = javassistCompiler.compile(code, findClassLoader());



        System.out.println(code);
    }


}
