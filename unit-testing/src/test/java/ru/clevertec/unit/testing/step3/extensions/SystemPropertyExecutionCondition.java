package ru.clevertec.unit.testing.step3.extensions;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SystemPropertyExecutionCondition implements ExecutionCondition {
    private static final String DEV = "DEV";
    private static final String TEST_PROPERTY = "test";

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        boolean existTagOnClass = context.getTags().contains(DEV);
        String testProperty = System.getProperty(TEST_PROPERTY);

        return DEV.equals(testProperty) && existTagOnClass
                ? ConditionEvaluationResult.enabled("Test enabled for DEV env")
                : ConditionEvaluationResult.disabled("Test disabled for not DEV end");
    }
}
