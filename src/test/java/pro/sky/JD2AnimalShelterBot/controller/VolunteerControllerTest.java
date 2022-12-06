package pro.sky.JD2AnimalShelterBot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.service.CorrespondenceService;
import pro.sky.JD2AnimalShelterBot.service.PetService;
import pro.sky.JD2AnimalShelterBot.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    void getUnansweredMessages() {
        Correspondence message1 = new Correspondence(12345L, 333L, LocalDateTime.now(), "Some text 1", false, "user");
        Correspondence message2 = new Correspondence(23456L, 444L, LocalDateTime.now(), "Some text 2", false, "user");

        when(correspondenceService.getUnansweredMessages())
                .thenReturn(List.of(message1, message2));
        List<Correspondence> actual = out.getUnansweredMessages();
        List<Correspondence> expected = List.of(message1, message2);
        assertEquals(expected, actual);
    }

    @Test
    void replyToMessages() {
    }

    @Test
    void sendMessage() {
    }

    @Test
    void getAllCorrespondenceWithUser() {
    }
}