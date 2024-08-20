package ru.clevertec.unit.testing.step3.depenencyinjection;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.unit.testing.model.User;

import java.lang.reflect.Parameter;
import java.util.Optional;
import java.util.UUID;

public class InjectUserResolverExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        InjectUser annotation = parameter.getAnnotation(InjectUser.class);
        return User.class.equals(parameter.getType()) && annotation != null;
    }

    @Override
    public User resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return Optional.ofNullable(parameterContext.getParameter())
                .map(parameter -> parameter.getAnnotation(InjectUser.class))
                .map(InjectUser::name)
                .map(this::getUser)
                .orElseThrow(RuntimeException::new);
    }

    private User getUser(String name) {
        return new User()
                .setRole(User.Role.GUEST)
                .setUuid(UUID.randomUUID())
                .setName(name);
    }
}
