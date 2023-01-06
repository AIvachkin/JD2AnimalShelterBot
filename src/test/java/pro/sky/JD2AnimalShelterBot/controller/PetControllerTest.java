package pro.sky.JD2AnimalShelterBot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.service.pet.PetService;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @InjectMocks
    private PetController out;

    @Mock
    private PetService petService;
    private Pet pet = new Pet(1L, "name", 1, null, null, LocalDate.MAX, false, "dog");

    @BeforeEach
    public void initOut() {
        out = new PetController(petService);
    }

    @Test
    void createPetTest() {
        when(petService.createPet(pet)).thenReturn(pet);
        ResponseEntity actual = out.createPet(pet);
        ResponseEntity expected = ResponseEntity.ok(petService.createPet(pet));
        assertEquals(expected, actual);
    }

    @Test
    void getPetTest() {
        when(petService.getById(1L)).thenReturn(pet);
        ResponseEntity actual = out.getPet(1L);
        ResponseEntity expected = ResponseEntity.ok(pet);
        assertEquals(expected, actual);
    }
    @Test
    void getPetNullTest() {
        when(petService.getById(1L)).thenReturn(null);
        ResponseEntity actual = out.getPet(1L);
        ResponseEntity expected = ResponseEntity.notFound().build();
        assertEquals(expected, actual);
    }
    @Test
    void updatePetTest() {
        when(petService.updatePet(pet)).thenReturn(pet);
        ResponseEntity actual = out.updatePet(pet);
        ResponseEntity expected = ResponseEntity.ok(petService.updatePet(pet));
        assertEquals(expected, actual);
    }
    @Test
    void updatePetNullTest() {
        pet = null;
        ResponseEntity actual = out.updatePet(pet);
        ResponseEntity expected = ResponseEntity.badRequest().build();
        assertEquals(expected, actual);
    }
    @Test
    void deletePetTest() {
        petService.createPet(pet);
        ResponseEntity actual = out.deletePet(1L);
        ResponseEntity expected = ResponseEntity.ok().build();
        assertEquals(expected, actual);
    }
    @Test
    void deletePetNotFoundExceptionTest() {
        doThrow(new NotFoundException()).when(petService).delete(1L);
        ResponseEntity actual = out.deletePet(1L);
        ResponseEntity expected = ResponseEntity.notFound().build();
        assertEquals(expected, actual);
    }
    @Test
    void getAllPetsTest() {
        when(petService.getAllPets()).thenReturn(List.of(pet));
        List<Pet> actual = out.getAllPets();
        List<Pet> expected = List.of(pet);
        assertEquals(expected, actual);
    }
    @Test
    void assignPetToCaregiverTest() {
        doAnswer(invocation -> null).when(petService).assignPetToCaregiver(1L,1L);
        ResponseEntity actual = out.assignPetToCaregiver(1L,1L);
        ResponseEntity expected = ResponseEntity.ok().build();
        assertEquals(expected, actual);
    }
    @Test
    void detachPetFromCaregiverTest() {
        doAnswer(invocation -> null).when(petService).detachPetFromCaregiver(1L);
        ResponseEntity actual = out.detachPetFromCaregiver(1L);
        ResponseEntity expected = ResponseEntity.ok().build();
        assertEquals(expected, actual);
    }
}


