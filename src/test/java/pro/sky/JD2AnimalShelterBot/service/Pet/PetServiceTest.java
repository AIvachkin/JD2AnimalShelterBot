package pro.sky.JD2AnimalShelterBot.service.Pet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.repository.CatUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;
import pro.sky.JD2AnimalShelterBot.service.ExecuteMessage;
import pro.sky.JD2AnimalShelterBot.service.pet.PetService;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService out;
    @Mock
    private PetRepository petRepository;
    @Mock
    private DogUserRepository userRepository;
    @Mock
    private CatUserRepository catUserRepository;
    @Mock
    private ExecuteMessage executeMessage;
    private Pet pet = new Pet(1L, "name", 1, null, null, LocalDate.MAX, false, "dog");
    private DogUser user = new DogUser(1L, "firstname", "lastname", "phoneNumber", null, null);


    @Test
    void getByIdTest() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        Pet actual = out.getById(1L);
        Pet expected = pet;
        assertEquals(expected, actual);
    }

    @Test
    void getByIdNullTest() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        Pet actual = out.getById(1L);
        assertEquals(null, actual);
    }

    @Test
    void createPetTest() {
        when(petRepository.save(pet)).thenReturn(pet);
        Pet actual = out.createPet(pet);
        assertEquals(pet, actual);
    }

    @Test
    void deleteThrowTest() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            out.delete(1L);
        } catch (Exception actual) {
            assertEquals(NotFoundException.class, actual.getClass());
        }
    }
    @Test
    void updatePetTest() {
        when(petRepository.save(pet)).thenReturn(pet);
        Pet actual = out.updatePet(pet);
        assertEquals(pet, actual);
    }
    @Test
    void getAllPetsTest() {
        when(petRepository.findAll()).thenReturn(List.of(pet));
        List<Pet> actual = out.getAllPets();
        assertEquals(List.of(pet), actual);
    }

    @Test
    void assignPetToCaregiverTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        out.assignPetToCaregiver(1L,1L);
        assertEquals(user, pet.getDogUser());
    }
    @Test
    void detachPetFromCaregiverTest() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        out.detachPetFromCaregiver(1L);
        assertEquals(null, pet.getDogUser());
    }

    @Test
    void extensionOfProbationPeriodIfNotFoundTest() {
        when(petRepository.findById(1L)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> petRepository.findById(1L));
    }

    @Test
    void extensionOfProbationPeriod() {
        DogUser user1 = new DogUser(2L, "firstname", "lastname", "phoneNumber", null, null);
        Pet pet1 = new Pet(2L, "name", 1, user1, null, LocalDate.now(), false, "dog");
        when(petRepository.findById(2L)).thenReturn(Optional.of(pet1));
        doAnswer(invocation -> null).when(petRepository).save(any());
        assertEquals(LocalDate.now().plusDays(14), out.extensionOfProbationPeriod(2L, 14));
    }

    @Test
    void securingAnimalToCaregiver() {
        Pet pet2 = new Pet(3L, "name", 1, user, null, LocalDate.now(), false, "dog");
        when(petRepository.findById(3L)).thenReturn(Optional.of(pet2));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doAnswer(invocation -> null).when(executeMessage).prepareAndSendMessage(anyLong(), anyString(), any());
        pet2.setFixed(true);
        assertEquals(pet2, out.securingAnimalToCaregiver(3L, 1L));
    }

    @Test
    void probationFailed() {
        Pet pet3 = new Pet(3L, "name", 1, user, null, LocalDate.now(), false, "dog");
        when(petRepository.findById(3L)).thenReturn(Optional.of(pet3));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        String text = "Сообщение от волонтера приюта: \n" + """
            Вы не справились с испытательным сроком.
            Вам необходимо в течении трех дней вернуть животное в приют.
            По всем вопросам Вы можете проконсультироваться с волонтером приюта.
            """;
        out.probationFailed(3L, 1L);
        verify(executeMessage).prepareAndSendMessage(1L, text, null);
    }
}