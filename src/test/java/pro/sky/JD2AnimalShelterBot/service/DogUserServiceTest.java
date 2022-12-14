package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;


import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DogUserServiceTest {

    DogUser dogUserExpected = new DogUser();


    @Mock
    DogUserRepository dogUserRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void initUser() {
        dogUserExpected.setChatId(6666L);
        dogUserExpected.setFirstname("Maksim");
        dogUserExpected.setLastname("Petrov");
    }


    @Test
    void createUser() {

        Chat chat = new Chat();
        Message message = new Message();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(6666L);
        message.setChat(chat);
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(message);
        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        userService.createDogUser(6666L, update);

        verify(dogUserRepository).save(dogUserExpected);
    }

    @Test
    void getUser() {

        when(dogUserRepository.findById(6666L)).thenReturn(Optional.of(dogUserExpected));
        DogUser actual = userService.getDogUserById(6666L);
        assertEquals(dogUserExpected, actual);

    }
}