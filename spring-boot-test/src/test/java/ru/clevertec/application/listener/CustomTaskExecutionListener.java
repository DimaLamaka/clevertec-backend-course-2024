package ru.clevertec.application.listener;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class CustomTaskExecutionListener implements TestExecutionListener {
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        System.out.println("before test method :" + testContext.getTestMethod().getName());
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        System.out.println("after test method :" + testContext.getTestMethod().getName());
    }

}
