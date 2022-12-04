package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.JD2AnimalShelterBot.model.User;
import pro.sky.JD2AnimalShelterBot.repository.UserRepository;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    @Test
    void createUser() {

        User userExpected = new User();
        userExpected.setChatId(6666L);
        userExpected.setFirstname("Maksim");
        userExpected.setLastname("Petrov");

        Chat chat = new Chat();
        Message message = new Message();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(6666L);
        message.setChat(chat);

        when(userRepository.save(any())).thenReturn(userExpected);

        userService.createUser(message);

        verify(userRepository).save(userExpected);
    }

    @Test
    void getUser() {
    }
}