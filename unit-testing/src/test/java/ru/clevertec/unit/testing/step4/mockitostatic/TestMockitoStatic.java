package ru.clevertec.unit.testing.step4.mockitostatic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.unit.testing.model.User;
import ru.clevertec.unit.testing.repository.UserRepository;
import ru.clevertec.unit.testing.service.UserService;
import ru.clevertec.unit.testing.util.UuidUtil;

import java.util.UUID;

@ExtendWith({MockitoExtension.class})
class TestMockitoStatic {

    @InjectMocks
    private UserService userService;
    @Spy
    private UserRepository userRepository;

    @Test
    void testMockStaticMethod() {
        UUID defaultUuid = UUID.randomUUID();
        try (MockedStatic<UuidUtil> uuidUtilMockedStatic = Mockito.mockStatic(UuidUtil.class)) {
            uuidUtilMockedStatic.when(UuidUtil::generateUuid).thenReturn(defaultUuid);
            User add = userService.add(new User().setRole(User.Role.ADMIN));

//            Assertions.assertEquals(defaultUuid, UuidUtil.generateUuid()); //working
//            Assertions.assertEquals(defaultUuid, add.getUuid());
            Mockito.verify(userRepository, Mockito.times(1)).add(Mockito.any(User.class));
        }
        Assertions.assertEquals(defaultUuid,UuidUtil.generateUuid()); //not working
    }

    @BeforeEach
    void tearDown() {
        userRepository.getAll().clear();
        System.out.println();
    }
}
