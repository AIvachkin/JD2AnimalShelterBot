package pro.sky.JD2AnimalShelterBot.service.Pet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;
import pro.sky.JD2AnimalShelterBot.service.pet.PetService;

import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService out;
    @Mock
    private PetRepository petRepository;
    @Mock
    private DogUserRepository userRepository;

    private Pet pet = new Pet(1L, "name", 1, null, null, LocalDate.MAX, false, "dog");
    private DogUser user = new DogUser(1L, "firstname", "lastname", "phoneNumber", null);

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
}