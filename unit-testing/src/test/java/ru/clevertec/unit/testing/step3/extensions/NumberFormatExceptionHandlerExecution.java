package ru.clevertec.unit.testing.step3.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class NumberFormatExceptionHandlerExecution implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (NumberFormatException.class.equals(throwable.getClass())) {
            System.out.println("Handle NumberFormatException");
            return;
        }
        throw throwable;
    }
}
