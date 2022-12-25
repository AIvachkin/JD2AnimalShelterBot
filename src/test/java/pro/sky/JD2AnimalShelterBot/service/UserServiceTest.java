package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.repository.CatUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;
import pro.sky.JD2AnimalShelterBot.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    DogUserRepository dogUserRepository;

    @Mock
    CatUserRepository catUserRepository;

    @Mock
    ExecuteMessage executeMessage;

    @InjectMocks
    UserService userService;

    private DogUser dogUser;
    private CatUser catUser;

    @BeforeEach
    void initUser() {
        dogUser = new DogUser(123454321L, "Maksim", "Petrov", null, null);
        catUser = new CatUser(123454321L, "Maksim", "Petrov", null, null);
    }

    @Test
    void createDogUser() {
        doAnswer(invocation -> null).when(dogUserRepository).save(any());
        Chat chat = new Chat();
        Message message = new Message();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(123454321L);
        message.setChat(chat);
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(message);
        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        userService.createDogUser(123454321L, update);
        verify(dogUserRepository).save(dogUser);
    }

    @Test
    void createCatUser() {
        doAnswer(invocation -> null).when(catUserRepository).save(any());
        Chat chat = new Chat();
        Message message = new Message();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(123454321L);
        message.setChat(chat);
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(message);
        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        userService.createCatUser(123454321L, update);
        verify(catUserRepository).save(catUser);
    }

    @Test
    void getDogUserById() {
        when(dogUserRepository.findById(1L)).thenReturn(Optional.of(dogUser));
        when(dogUserRepository.findById(2L)).thenThrow(new NoSuchElementException());
        DogUser actual = userService.getDogUserById(1L);
        DogUser expected = dogUser;
        assertEquals(expected, actual);
        assertThrows(NoSuchElementException.class, () -> userService.getDogUserById(2L));
    }

    @Test
    void getCatUserById() {
        when(catUserRepository.findById(1L)).thenReturn(Optional.of(catUser));
        when(catUserRepository.findById(2L)).thenThrow(new NoSuchElementException());
        CatUser actual = userService.getCatUserById(1L);
        CatUser expected = catUser;
        assertEquals(expected, actual);
        assertThrows(NoSuchElementException.class, () -> userService.getCatUserById(2L));
    }

    @Test
    void getDogUserPhone() {
        when(dogUserRepository.getUserPhoneById(anyLong())).thenReturn("+79999999999");
        String actual = userService.getDogUserPhone(1L);
        String expected = "+79999999999";
        assertEquals(expected, actual);
    }

    @Test
    void getCatUserPhone() {
        when(catUserRepository.getUserPhoneById(anyLong())).thenReturn("+79999999999");
        String actual = userService.getCatUserPhone(1L);
        String expected = "+79999999999";
        assertEquals(expected, actual);
    }

    @Test
    void requestContactDetails() {
        doAnswer(invocation -> null).when(executeMessage).executeMessage(any());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(1L);
        sendMessage.setText("""
                Чтобы связаться с волонтером, пожалуйста, отправьте нам свои контакты.

                Для этого нажмите кнопку внизу и подтвердите передачу контактов.""");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("\uD83D\uDC49 Поделиться контактами \uD83D\uDC48");
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        userService.requestContactDetails(1L);
        verify(executeMessage).executeMessage(sendMessage);
    }

    @Test
    void setDodUserPhone() {
        doAnswer(invocation -> null).when(dogUserRepository).save(any());
        when(dogUserRepository.findById(123454321L)).thenReturn(Optional.of(dogUser));
        Message message = new Message();
        Contact contact = new Contact("+79999999999", "Maksim", "Petrov", 123454321L, null);
        message.setContact(contact);
        Chat chat = new Chat();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(123454321L);
        message.setChat(chat);
        userService.setDodUserPhone(message);
        verify(dogUserRepository).save(dogUser);
        when(dogUserRepository.findById(2L)).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> userService.getDogUserById(2L));
    }

    @Test
    void setCatUserPhone() {
        doAnswer(invocation -> null).when(catUserRepository).save(any());
        when(catUserRepository.findById(123454321L)).thenReturn(Optional.of(catUser));
        Message message = new Message();
        Contact contact = new Contact("+79999999999", "Maksim", "Petrov", 123454321L, null);
        message.setContact(contact);
        Chat chat = new Chat();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(123454321L);
        message.setChat(chat);
        userService.setCatUserPhone(message);
        verify(catUserRepository).save(catUser);
        when(catUserRepository.findById(2L)).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> userService.getCatUserById(2L));
    }

    @Test
    void getAllDogUsers() {
        when(dogUserRepository.findAll()).thenReturn(List.of(dogUser));
        List<DogUser> actual = userService.getAllDogUsers();
        List<DogUser> expected = List.of(dogUser);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCatUsers() {
        when(catUserRepository.findAll()).thenReturn(List.of(catUser));
        List<CatUser> actual = userService.getAllCatUsers();
        List<CatUser> expected = List.of(catUser);
        assertEquals(expected, actual);
    }
}