package pro.sky.JD2AnimalShelterBot.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserContextTest {

    @InjectMocks
    private UserContext userContext;

    @Test
    void setUserContext() {
        userContext.setUserContext(2L, "cat");
        assertEquals(Set.of("cat"), userContext.getUserContext(2L));
    }

    @Test
    void getUserContext() {
        userContext.setUserContext(1L, "dog");
        assertEquals(Set.of("dog"), userContext.getUserContext(1L));
    }

    @Test
    void deleteUserContext() {
        userContext.setUserContext(3L, "dog");
        userContext.setUserContext(3L, "sendReport");
        userContext.deleteUserContext(3L, "sendReport");
        assertEquals(Set.of("dog"), userContext.getUserContext(3L));
    }
}