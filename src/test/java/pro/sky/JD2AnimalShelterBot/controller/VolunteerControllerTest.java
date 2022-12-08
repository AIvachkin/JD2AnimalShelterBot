package pro.sky.JD2AnimalShelterBot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.service.CorrespondenceService;
import pro.sky.JD2AnimalShelterBot.service.PetService;
import pro.sky.JD2AnimalShelterBot.service.UserService;

import javax.ws.rs.NotFoundException;
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

    @BeforeEach
    public void initOut(){
        out = new VolunteerController(petService, userService, correspondenceService);
    }

    @Test
    void getUnansweredMessagesTest() {
        Correspondence message1 = new Correspondence(12345L, 333L, LocalDateTime.now(), "Some text 1", false, "user");
        Correspondence message2 = new Correspondence(23456L, 444L, LocalDateTime.now(), "Some text 2", false, "user");
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

        doThrow(new NotFoundException("Сообщение с таким ид не найдено")).when(correspondenceService).replyToMessages(3333l, "Some Text");
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

        Correspondence message1 = new Correspondence(12345L, 4444L, LocalDateTime.now(), "Some text 1", false, "user");
        Correspondence message2 = new Correspondence(23456L, 4444L, LocalDateTime.now(), "Some text 2", false, "user");
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
}