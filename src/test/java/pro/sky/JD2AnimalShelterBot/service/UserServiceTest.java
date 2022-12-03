package pro.sky.JD2AnimalShelterBot.service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
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

import javax.validation.constraints.Null;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    UserRepository userRepository;

    @Mock
    Message message;

    @Mock
    Chat chat;

    @InjectMocks
    UserService userService;


    @BeforeEach
    public void initChat() {
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(6666L);
        message.setChat(chat);

    }


    @Test
    void createUser() {

        User userExpected = new User();
        userExpected.setChatId(6666L);
        userExpected.setFirstname("Maksim");
        userExpected.setLastname("Petrov");

        when(userRepository.save(any())).thenReturn(userExpected);

        when(chat.getFirstName()).thenReturn("Maksim");
        when(chat.getLastName()).thenReturn("Petrov");
        when(message.getChatId()).thenReturn(6666L);
        when(message.getChat()).thenReturn(chat);

        User actual = userService.createUser(message);

        assertThat(actual).isEqualTo(userExpected);

    }

    @Test
    void getUser() {
    }
}