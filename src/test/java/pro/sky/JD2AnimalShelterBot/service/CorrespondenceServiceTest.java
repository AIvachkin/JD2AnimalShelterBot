package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.repository.CorrespondenceRepository;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CorrespondenceServiceTest {

    @Mock
    private CorrespondenceRepository correspondenceRepository;

    @Mock
    private UserService userService;

    @Mock
    private ExecuteMessage executeMessage;

    @InjectMocks
    private CorrespondenceService out;

    private final Correspondence message1 = new Correspondence(1L, 12345L, LocalDateTime.now(), "Текст1", false, "user");
    private final Correspondence message2 = new Correspondence(2L, 12345L, LocalDateTime.now(), "Текст2", false, "user");
    private final Correspondence message3 = new Correspondence(3L, 12345L, LocalDateTime.now(), "Текст3", false, "user");
    private final List<Correspondence> unansweredMessages = List.of(message1, message2, message3);


    @BeforeEach
    public void initOut(){
        out = new CorrespondenceService(correspondenceRepository, userService, executeMessage);
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
    void getMessagesByIdTest() {
        when(correspondenceRepository.getCorrespondenceByMessageId(3))
                .thenReturn(message3);
        Correspondence actual = out.getMessagesById(3);
        Correspondence expected = message3;
        assertEquals(expected, actual);
    }

    @Test
    void replyToMessagesTest() {
        assertThrows(NotFoundException.class, () -> out.replyToMessages(0L, "SomeString"));
    }

    @Test
    void sendMessageTest() {
        when(userService.getUser(anyLong()))
                .thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> out.sendMessage(0L, "SomeString"));
    }

    @Test
    void getAllCorrespondenceWithUser() {
        when(correspondenceRepository.getAllCorrespondenceWithUser(12345))
                .thenReturn(unansweredMessages);
        when(correspondenceRepository.getAllCorrespondenceWithUser(54321))
                .thenThrow(NotFoundException.class);
        List<Correspondence> actual = out.getAllCorrespondenceWithUser(12345);
        List<Correspondence> expected = List.of(message1, message2, message3);
        assertEquals(expected, actual);
        assertThrows(NotFoundException.class, () -> out.getAllCorrespondenceWithUser(54321));
    }
}