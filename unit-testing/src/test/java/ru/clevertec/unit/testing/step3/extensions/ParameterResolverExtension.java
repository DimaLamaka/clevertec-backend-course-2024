package ru.clevertec.unit.testing.step3.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.Random;

public class ParameterResolverExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        CustomRandom annotation = parameter.getAnnotation(CustomRandom.class);

        return parameter.getType().equals(Integer.class) && Objects.nonNull(annotation);
    }

    @Override
    public Integer resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Random random = extensionContext
                .getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(Random.class, classInstance -> new Random(), Random.class);
        return random.nextInt();
    }
}
