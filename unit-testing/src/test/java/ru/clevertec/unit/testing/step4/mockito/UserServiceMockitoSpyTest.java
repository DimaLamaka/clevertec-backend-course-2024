package ru.clevertec.unit.testing.step4.mockito;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.unit.testing.model.User;
import ru.clevertec.unit.testing.repository.UserRepository;
import ru.clevertec.unit.testing.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith({MockitoExtension.class})
class UserServiceMockitoSpyTest {
    @InjectMocks
    private UserService userService;

    @Spy
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<UUID> argumentCaptor;

    @Test
    @DisplayName("Should success add user to repository and returned user with generated uuid")
    void shouldSuccessAddWithSpy() {
        System.out.println("spyUserRepository" + userRepository);
        //given
        User user = new User().setName("name").setRole(User.Role.USER);
        //when
        User actual = userService.add(user);
        Collection<User> users = userService.getAll();
        //then
        Assertions.assertEquals(1, users.size());
        Mockito.verify(userRepository, Mockito.times(1)).add(user);
        Mockito.verify(userRepository, Mockito.times(1)).getAll();
    }

    @Test
    @DisplayName("Should success get all with spy mocked method")
    void shouldSuccessAddWithMockSpy() {
        System.out.println("spyUserRepository" + userRepository);
        //given
        User mockUser = new User().setUuid(UUID.randomUUID()).setName("name").setRole(User.Role.GUEST);
        Mockito.doReturn(List.of(mockUser)).when(userRepository).getAll();
        //when
        Collection<User> users = userService.getAll();
        //then
        Assertions.assertEquals(1, users.size());
        Mockito.verify(userRepository, Mockito.times(1)).getAll();
    }

    @Test
    @DisplayName("Should success return all with spy")
    void shouldSuccessReturnAllMockSpy() {
        System.out.println("spyUserRepository" + userRepository);
        //given
        Mockito.doReturn(Collections.emptyList()).when(userRepository).getAll();
        //when
        Collection<User> all = userService.getAll();
        //then
        Assertions.assertEquals(0, all.size());
    }
}
