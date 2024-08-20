package ru.clevertec.unit.testing.step4.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.clevertec.unit.testing.model.User;
import ru.clevertec.unit.testing.repository.UserRepository;
import ru.clevertec.unit.testing.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

class UserServiceMockitoTest {
    private UserService userService;
    private UserRepository mockUserRepository;

    @BeforeEach
    void setup() {
        mockUserRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(mockUserRepository);
    }

    @Test
    @DisplayName("Should success add user to repository and returned user with generated uuid")
    void shouldSuccessAdd() {
        //given
        User user = new User().setName("name").setRole(User.Role.ADMIN);
        User mockUser = new User().setName("name").setRole(User.Role.ADMIN).setUuid(UUID.randomUUID());

        Mockito.doReturn(mockUser).when(mockUserRepository).add(user);
        //when
        User actual = userService.add(user);
        //then
        Assertions.assertEquals("name", actual.getName());
        Assertions.assertEquals(User.Role.ADMIN, actual.getRole());
        Assertions.assertNotNull(actual.getUuid());
    }

    @Test
    @DisplayName("Should success delete user by id from repository")
    void shouldSuccessDeleteById() {
        //given
        Mockito.doNothing().doNothing().when(mockUserRepository).deleteById(Mockito.argThat(Objects::nonNull));
        //when
        userService.deleteById(UUID.randomUUID());
        userService.deleteById(UUID.randomUUID());
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        //then
        Mockito.verify(mockUserRepository, Mockito.times(2)).deleteById(Mockito.isA(UUID.class));
        Mockito.verify(mockUserRepository, Mockito.atLeast(2)).deleteById(Mockito.isA(UUID.class));

        Mockito.verify(mockUserRepository, Mockito.after(10_000).times(2)).deleteById(Mockito.any(UUID.class));
        Mockito.verify(mockUserRepository, Mockito.timeout(10_000).times(2)).deleteById(Mockito.any(UUID.class));


        Mockito.verify(mockUserRepository, Mockito.atLeastOnce()).deleteById(argumentCaptor.capture());
        System.out.println(argumentCaptor.getValue());
    }

    @Test
    @DisplayName("Should success return empty list")
    void shouldSuccessGetAll() {
        //given
        Mockito.doReturn(Collections.emptyList()).when(mockUserRepository).getAll();
        //when
        Collection<User> users = userService.getAll();
        //then
        Mockito.verify(mockUserRepository, Mockito.never()).deleteById(Mockito.isA(UUID.class));
        Mockito.verify(mockUserRepository, Mockito.atMostOnce()).getAll();

        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    @DisplayName("Should return error if user not found ")
    void shouldErrorGetUserById() {
        //given
        UUID uuid = UUID.randomUUID();
        Mockito.doThrow(new RuntimeException()).when(mockUserRepository).getById(Mockito.any(UUID.class));
        //then
        Assertions.assertThrows(RuntimeException.class, () -> userService.getById(uuid));
    }


    @Test
    @DisplayName("Should success return user by id")
    void shouldSuccessGetUserById() {
        User user = new User()
                .setUuid(UUID.randomUUID())
                .setRole(User.Role.ADMIN)
                .setName("name");
        //given
        Mockito.doAnswer(invocation -> user).when(mockUserRepository).getById(Mockito.any(UUID.class));
        //when
        User actual = userService.getById(UUID.randomUUID());
        //then
        Assertions.assertNotNull(actual);
    }

}
