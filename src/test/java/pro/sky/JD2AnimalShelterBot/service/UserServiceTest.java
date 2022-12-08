package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.JD2AnimalShelterBot.model.User;
import pro.sky.JD2AnimalShelterBot.repository.UserRepository;


import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    User userExpected = new User();


    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void initUser() {
        userExpected.setChatId(6666L);
        userExpected.setFirstname("Maksim");
        userExpected.setLastname("Petrov");
    }


    @Test
    void createUser() {

        Chat chat = new Chat();
        Message message = new Message();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(6666L);
        message.setChat(chat);

        userService.createUser(message);

        verify(userRepository).save(userExpected);
    }

    @Test
    void getUser() {

        when(userRepository.findById(6666L)).thenReturn(Optional.of(userExpected));
        User actual = userService.getUser(6666L);
        assertEquals(userExpected, actual);

    }
}