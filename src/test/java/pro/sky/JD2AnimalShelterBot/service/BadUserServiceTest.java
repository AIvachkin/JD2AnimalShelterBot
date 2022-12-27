package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.JD2AnimalShelterBot.model.BadUser;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.repository.BadUserRepository;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BadUserServiceTest {
    @Mock
    BadUserRepository badUserRepository;
    @InjectMocks
    BadUserService out;
    Long id = 1L;
    DogUser dogUser = new DogUser(id, "firstName", "lastName", "phoneNumber", null, null);
    CatUser catUser = new CatUser(id, "firstName", "lastName", "phoneNumber", null, null);
    BadUser badUser = new BadUser(id, dogUser, null);

    @Test
    public void getByIdTest() {
        when(badUserRepository.findById(id)).thenReturn(Optional.of(badUser));
        when(badUserRepository.findById(2L)).thenReturn(Optional.empty());

        BadUser actual = out.getById(1L);
        BadUser expected = badUser;
        assertEquals(expected, actual);

        actual = out.getById(2L);
        assertEquals(null, actual);
    }

    @Test
    public void createDogBadUserTest() {
        when(badUserRepository.save(any())).thenReturn(badUser);
        BadUser actual = out.createBadUser(dogUser);
        assertEquals(badUser, actual);
    }

    @Test
    public void createCatBadUserTest() {
        when(badUserRepository.save(any())).thenReturn(badUser);
        BadUser actual = out.createBadUser(catUser);
        assertEquals(badUser, actual);
    }

    @Test
    void deleteThrowTest() {
        when(badUserRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            out.delete(1L);
        } catch (Exception actual) {
            assertEquals(NotFoundException.class, actual.getClass());
        }
    }

    @Test
    void updateBadUserTest() {
        when(badUserRepository.save(badUser)).thenReturn(badUser);
        BadUser actual = out.updateBadUser(badUser);
        assertEquals(badUser, actual);
    }

    @Test
    void getAllBadUsersTest() {
        when(badUserRepository.findAll()).thenReturn(List.of(badUser));
        List<BadUser> actual = out.getAllBadUsers();
        assertEquals(List.of(badUser), actual);
    }
}
