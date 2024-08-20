package ru.clevertec.unit.testing.step3.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class TestInstancePostProcessorExtension implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        System.out.println("test instance post processor " + testInstance);
    }
}
