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
    private Pet pet = new Pet(1L, "name", 1, null, LocalDate.MAX, false);

    @BeforeEach
    public void initOut() {
        out = new PetController(petService);
    }

    @Test
    void createPetTest() {
//        Pet pet1 = new Pet();
//        Pet pet2 = new Pet();
//        User user = new User(1L, "firstname", "lastname", "phoneNumber", List.of(pet1, pet2));
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


//
//
//
//
//
//
//    @Test
//    void getUnansweredMessagesTest() {
//        Correspondence message1 = new Correspondence(12345L, 333L, LocalDateTime.now(), "Some text 1", false, "user");
//        Correspondence message2 = new Correspondence(23456L, 444L, LocalDateTime.now(), "Some text 2", false, "user");
//        when(correspondenceService.getUnansweredMessages())
//                .thenReturn(List.of(message1, message2));
//        List<Correspondence> actual = out.getUnansweredMessages();
//        List<Correspondence> expected = List.of(message1, message2);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void replyToMessagesTest() {
//
//        doNothing().when(correspondenceService).replyToMessages(2222L, "Some Text");
//        ResponseEntity responseEntity200 = out.replyToMessages(2222L, "Some Text");
//        assertThat(responseEntity200.getStatusCodeValue()).isEqualTo(200);
//
//        doThrow(new NotFoundException("Сообщение с таким ид не найдено")).when(correspondenceService).replyToMessages(3333l, "Some Text");
//        ResponseEntity responseEntity404 = out.replyToMessages(3333L, "Some Text");
//        assertThat(responseEntity404.getStatusCodeValue()).isEqualTo(404);
//    }
//
//    @Test
//    void sendMessageTest() {
//
//        doNothing().when(correspondenceService).sendMessage(4444L, "Some Text");
//        ResponseEntity responseEntity200 = out.sendMessage(4444L, "Some Text");
//        assertThat(responseEntity200.getStatusCodeValue()).isEqualTo(200);
//
//        doThrow(new NoSuchElementException()).when(correspondenceService).sendMessage(5555L, "Some Text");
//        ResponseEntity responseEntity404 = out.sendMessage(5555L, "Some Text");
//        assertThat(responseEntity404.getStatusCodeValue()).isEqualTo(404);
//    }
//
//    @Test
//    void getAllCorrespondenceWithUser() {
//
//        Correspondence message1 = new Correspondence(12345L, 4444L, LocalDateTime.now(), "Some text 1", false, "user");
//        Correspondence message2 = new Correspondence(23456L, 4444L, LocalDateTime.now(), "Some text 2", false, "user");
//        when(correspondenceService.getAllCorrespondenceWithUser(4444L))
//                .thenReturn(List.of(message1, message2));
//        ResponseEntity<List<Correspondence>> responseEntity200 = out.getAllCorrespondenceWithUser(4444L);
//        assertThat(responseEntity200.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity200.getBody()).isEqualTo(List.of(message1, message2));
//
//        when(correspondenceService.getAllCorrespondenceWithUser(5555L))
//                .thenThrow(NotFoundException.class);
//        ResponseEntity responseEntity404 = out.getAllCorrespondenceWithUser(5555L);
//        assertThat(responseEntity404.getStatusCodeValue()).isEqualTo(404);
//    }
//}