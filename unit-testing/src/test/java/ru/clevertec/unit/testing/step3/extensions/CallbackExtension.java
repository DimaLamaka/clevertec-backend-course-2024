package ru.clevertec.unit.testing.step3.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class CallbackExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        System.out.println("After test execution callback");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        System.out.println("Before test execution callback");
    }
}
