package ru.clevertec.starter.condition;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class ExcludeProfileCondition implements Condition {
    private static final String EXCLUDE_PROFILES = "excludeProfiles";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        log.info("ExcludeProfileCondition worked!!!");
        MultiValueMap<String, Object> allAnnotationAttributes = metadata.getAllAnnotationAttributes(ExcludeProfileConditional.class.getName());
        return Optional.ofNullable(allAnnotationAttributes)
                .map(attributes -> attributes.get(EXCLUDE_PROFILES))
                .map(objects -> this.anyMatch(context.getEnvironment(), objects))
                .orElse(true);
    }

    private boolean anyMatch(Environment environment, List<Object> objects) {
        return objects.stream()
                .map(String[].class::cast)
                .anyMatch(Predicate.not(environment::matchesProfiles));
    }
}
