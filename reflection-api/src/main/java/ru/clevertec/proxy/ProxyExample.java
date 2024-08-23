package ru.clevertec.proxy;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyExample {
    public static void main(String[] args) {
        ServiceImpl realService = new ServiceImpl();
        Service proxyService = (Service) Proxy.newProxyInstance(
                ProxyExample.class.getClassLoader(),
                new Class[]{Service.class},
                new LoggerInvocationHandler(realService));

        proxyService.execute("default value");
        System.out.println(proxyService);
    }

    @RequiredArgsConstructor
    static class LoggerInvocationHandler implements InvocationHandler {
        private final Service target;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("toString")) {
                return "hello world";
            }
            if (method.getName().equals("executePrivate")) {
                System.out.println("hello world from private");
                return null;
            }

            Object[] newArgs = null;
            if (args != null) {
                newArgs = Arrays.copyOf(args, args.length);
            }
            if (method.getName().equals("execute") && newArgs[0] != null && newArgs[0].getClass().equals(String.class)) {
                newArgs[0] = "NEW VALUE FORM PROXY!!";
            }
            System.out.println("Call method with name: %s, args: %s".formatted(method.getName(), Arrays.toString(newArgs)));
            Object invokeResult = method.invoke(target, newArgs);
            System.out.println("Method with name: %s, returned result: %s".formatted(method.getName(), invokeResult));
            return invokeResult;
        }

        String json = """
                {
                 "id": 1,
                 "addresses":
                   [
                    {
                     "id": 1,
                     "title": "title"
                    }
                   ]
                }
                """;
    }

}
