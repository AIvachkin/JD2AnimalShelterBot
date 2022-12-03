package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.repository.CorrespondenceRepository;
import pro.sky.JD2AnimalShelterBot.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CorrespondenceServiceTest {

    @Mock
    private CorrespondenceRepository correspondenceRepository;

    @Mock
    private UserService userService;


    @InjectMocks
    private CorrespondenceService out;

    private Correspondence message1 = new Correspondence(1L, 12345L, LocalDateTime.now(), "Текст1", false, "user");
    private Correspondence message2 = new Correspondence(2L, 12345L, LocalDateTime.now(), "Текст2", false, "user");
    private Correspondence message3 = new Correspondence(3L, 12345L, LocalDateTime.now(), "Текст3", false, "user");
    private List<Correspondence> unansweredMessages = List.of(message1, message2, message3);


    @BeforeEach
    public void initOut(){
        out = new CorrespondenceService(correspondenceRepository, userService);
    }

    @Test
    void getUnansweredMessagesTest() {
        when(correspondenceRepository.getAllUnansweredMessages())
                .thenReturn(unansweredMessages);
        List<Correspondence> actual = out.getUnansweredMessages();
        List<Correspondence> expected = List.of(message1, message2, message3);
        assertEquals(expected, actual);
    }

    @Test
    void getMessagesById() {
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