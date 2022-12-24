package pro.sky.JD2AnimalShelterBot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import pro.sky.JD2AnimalShelterBot.model.*;
import pro.sky.JD2AnimalShelterBot.service.volunteer.CorrespondenceService;
import pro.sky.JD2AnimalShelterBot.service.TrusteesReportsService;
import pro.sky.JD2AnimalShelterBot.service.pet.PetService;
import pro.sky.JD2AnimalShelterBot.service.user.UserService;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerControllerTest {

    @InjectMocks
    private VolunteerController out;

    @Mock
    private PetService petService;

    @Mock
    private UserService userService;

    @Mock
    private CorrespondenceService correspondenceService;

    @Mock
    private TrusteesReportsService trusteesReportsService;

    @BeforeEach
    public void initOut(){
        out = new VolunteerController(petService, userService, correspondenceService, trusteesReportsService);
    }

    @Test
    void getUnansweredMessagesTest() {
        Correspondence message1 = new Correspondence(12345L, 333L, LocalDateTime.now(), "Some text 1", false, "user", "dog");
        Correspondence message2 = new Correspondence(23456L, 444L, LocalDateTime.now(), "Some text 2", false, "user", "dog");
        when(correspondenceService.getUnansweredMessages())
                .thenReturn(List.of(message1, message2));
        List<Correspondence> actual = out.getUnansweredMessages();
        List<Correspondence> expected = List.of(message1, message2);
        assertEquals(expected, actual);
    }

    @Test
    void replyToMessagesTest() {

        doNothing().when(correspondenceService).replyToMessages(2222L, "Some Text");
        ResponseEntity responseEntity200 = out.replyToMessages(2222L, "Some Text");
        assertThat(responseEntity200.getStatusCodeValue()).isEqualTo(200);

        doThrow(new NotFoundException("Сообщение с таким ид не найдено")).when(correspondenceService).replyToMessages(3333L, "Some Text");
        ResponseEntity responseEntity404 = out.replyToMessages(3333L, "Some Text");
        assertThat(responseEntity404.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void sendMessageTest() {

        doNothing().when(correspondenceService).sendMessage(4444L, "Some Text");
        ResponseEntity responseEntity200 = out.sendMessage(4444L, "Some Text");
        assertThat(responseEntity200.getStatusCodeValue()).isEqualTo(200);

        doThrow(new NoSuchElementException()).when(correspondenceService).sendMessage(5555L, "Some Text");
        ResponseEntity responseEntity404 = out.sendMessage(5555L, "Some Text");
        assertThat(responseEntity404.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void getAllCorrespondenceWithUser() {

        Correspondence message1 = new Correspondence(12345L, 4444L, LocalDateTime.now(), "Some text 1", false, "user", "dog");
        Correspondence message2 = new Correspondence(23456L, 4444L, LocalDateTime.now(), "Some text 2", false, "user", "dog");
        when(correspondenceService.getAllCorrespondenceWithUser(4444L))
                .thenReturn(List.of(message1, message2));
        ResponseEntity<List<Correspondence>> responseEntity200 = out.getAllCorrespondenceWithUser(4444L);
        assertThat(responseEntity200.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity200.getBody()).isEqualTo(List.of(message1, message2));

        when(correspondenceService.getAllCorrespondenceWithUser(5555L))
                .thenThrow(NotFoundException.class);
        ResponseEntity responseEntity404 = out.getAllCorrespondenceWithUser(5555L);
        assertThat(responseEntity404.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void getAllDogUsers() {
        DogUser dogUser1 = new DogUser(123456, "Василий", "Теркин", "+79999999999", null);
        DogUser dogUser2 = new DogUser(123457, "Павел", "Корчагин", "+79999999998", null);
        when(userService.getAllDogUsers()).thenReturn(List.of(dogUser1, dogUser2));
        ResponseEntity<List<DogUser>> actual = out.getAllDogUsers();
        ResponseEntity<List<DogUser>> expected = ResponseEntity.ok(List.of(dogUser1, dogUser2));
        assertEquals(expected, actual);
    }

    @Test
    void getAllCatUsers() {
        CatUser catUser1 = new CatUser(123458, "Михаил", "Лермонтов", "+79999999996", null);
        CatUser catUser2 = new CatUser(123459, "Максим", "Горький", "+79999999997", null);
        when(userService.getAllCatUsers()).thenReturn(List.of(catUser1, catUser2));
        ResponseEntity<List<CatUser>> actual = out.getAllCatUsers();
        ResponseEntity<List<CatUser>> expected = ResponseEntity.ok(List.of(catUser1, catUser2));
        assertEquals(expected, actual);
    }

    @Test
    void getAllPetReports() {
        TrusteesReports trusteesReports1 = new TrusteesReports(1L, 1234321L, null, LocalDateTime.now(), "/path", 444L, null, "text1", "cat", false);
        TrusteesReports trusteesReports2 = new TrusteesReports(2L, 1234321L, null, LocalDateTime.now(), "/path", 333L, null,  "text2", "cat", false);
        when(trusteesReportsService.getAllPetReports(anyLong())).thenReturn(List.of(trusteesReports1, trusteesReports2));
        ResponseEntity<List<TrusteesReports>> actual = out.getAllPetReports(3L);
        ResponseEntity<List<TrusteesReports>> expected = ResponseEntity.ok(List.of(trusteesReports1, trusteesReports2));
        assertEquals(expected, actual);
    }

    @Test
    void sendWarning() {
        doAnswer(invocation -> null).when(trusteesReportsService).sendWarning(1L);
        doThrow(new NotFoundException()).when(trusteesReportsService).sendWarning(2L);
        doThrow(new NullPointerException()).when(trusteesReportsService).sendWarning(3L);

        ResponseEntity actual = out.sendWarning(1L);
        ResponseEntity expected = ResponseEntity.ok().build();
        assertEquals(expected, actual);

        ResponseEntity actual2 = out.sendWarning(2L);
        ResponseEntity expected2 = ResponseEntity.notFound().build();
        assertEquals(expected2, actual2);

        ResponseEntity actual3 = out.sendWarning(3L);
        ResponseEntity expected3 = ResponseEntity.badRequest().build();
        assertEquals(expected3, actual3);
    }

    @Test
    void extensionOfProbationPeriod() {
        ResponseEntity actual = out.extensionOfProbationPeriod(1L, 10);
        ResponseEntity expected = ResponseEntity.badRequest().build();
        assertEquals(expected, actual);

        when(petService.extensionOfProbationPeriod(1L, 14)).thenReturn(LocalDate.now().plusDays(14));
        ResponseEntity actual2 = out.extensionOfProbationPeriod(1L, 14);
        ResponseEntity expected2 = ResponseEntity.ok(LocalDate.now().plusDays(14));
        assertEquals(expected2, actual2);

        doThrow(new NotFoundException()).when(petService).extensionOfProbationPeriod(2L, 14);
        ResponseEntity actual3 = out.extensionOfProbationPeriod(2L, 14);
        ResponseEntity expected3 = ResponseEntity.notFound().build();
        assertEquals(expected3, actual3);

        doThrow(new NullPointerException()).when(petService).extensionOfProbationPeriod(3L, 14);
        ResponseEntity actual4 = out.extensionOfProbationPeriod(3L, 14);
        ResponseEntity expected4 = ResponseEntity.badRequest().build();
        assertEquals(expected4, actual4);
    }

    @Test
    void securingAnimalToCaregiver() {
        DogUser dogUser = new DogUser(123456, "Василий", "Теркин", "+79999999999", null);
        Pet pet = new Pet(1L, "name", 1, dogUser, null, LocalDate.MAX, true, "dog");
        when(petService.securingAnimalToCaregiver(1L, 1234321L)).thenReturn(pet);
        ResponseEntity actual = out.securingAnimalToCaregiver(1L, 1234321L);
        ResponseEntity expected = ResponseEntity.ok(pet);
        assertEquals(expected, actual);

        doThrow(new NotFoundException()).when(petService).securingAnimalToCaregiver(2L, 1234321L);
        ResponseEntity actual2 = out.securingAnimalToCaregiver(2L, 1234321L);
        ResponseEntity expected2 = ResponseEntity.notFound().build();
        assertEquals(expected2, actual2);

        doThrow(new BadRequestException()).when(petService).securingAnimalToCaregiver(3L, 1234321L);
        ResponseEntity actual3 = out.securingAnimalToCaregiver(3L, 1234321L);
        ResponseEntity expected3 = ResponseEntity.badRequest().build();
        assertEquals(expected3, actual3);
    }

    @Test
    void probationFailed() {
        doAnswer(invocation -> null).when(petService).probationFailed(1L, 1234321L);
        ResponseEntity actual = out.probationFailed(1L, 1234321L);
        ResponseEntity expected = ResponseEntity.ok().build();
        assertEquals(expected, actual);

        doThrow(new NotFoundException()).when(petService).probationFailed(2L, 1234321L);
        ResponseEntity actual2 = out.probationFailed(2L, 1234321L);
        ResponseEntity expected2 = ResponseEntity.notFound().build();
        assertEquals(expected2, actual2);

        doThrow(new BadRequestException()).when(petService).probationFailed(3L, 1234321L);
        ResponseEntity actual3 = out.probationFailed(3L, 1234321L);
        ResponseEntity expected3 = ResponseEntity.badRequest().build();
        assertEquals(expected3, actual3);
    }
}