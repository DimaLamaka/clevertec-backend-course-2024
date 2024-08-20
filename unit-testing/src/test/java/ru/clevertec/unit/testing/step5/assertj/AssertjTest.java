package ru.clevertec.unit.testing.step5.assertj;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.unit.testing.model.User;
import ru.clevertec.unit.testing.repository.Repository;

import java.util.List;
import java.util.UUID;

class AssertjTest {
    @Test
    void assertjTest() {
        //given
        User user = new User()
                .setName("name")
                .setRole(User.Role.ADMIN)
                .setUuid(UUID.randomUUID());
        List<Integer> ints = List.of(1, 2, 5, 7, 10, 34);

        Assertions.assertThat(user.getName())
                .as(()-> "Name must be start with -> na" )
                .startsWith("na")
                .as(()-> "Name must be end with -> me" )
                .endsWith("me")
                .isEqualToIgnoringCase("NAME");

        Assertions.assertThat(ints)
                .isNotEmpty()
                .contains(1, 10, 2)
                .containsSubsequence(2, 5, 7)
                .doesNotContainNull()
                .doesNotContainAnyElementsOf(List.of(11, 20, 35))
                .hasSizeLessThanOrEqualTo(6)
                .filteredOn(x -> x == 43)
                .isEmpty();

        Assertions.assertThat(user.getName().isBlank())
                .isFalse()
                .isNotNull();

        Assertions.assertThat(Repository.class)
                .isPublic()
                .hasNoSuperclass()
                .isInterface();
    }
}
