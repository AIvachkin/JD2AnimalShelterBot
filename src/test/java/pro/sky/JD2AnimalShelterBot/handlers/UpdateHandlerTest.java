package pro.sky.JD2AnimalShelterBot.handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.JD2AnimalShelterBot.service.ExecuteMessage;
import pro.sky.JD2AnimalShelterBot.service.StartCommand;
import pro.sky.JD2AnimalShelterBot.service.user.UserContext;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Stream;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pro.sky.JD2AnimalShelterBot.constants.MainMenuConstants.START_COMAND;

@ExtendWith(MockitoExtension.class)
class UpdateHandlerTest {

    @InjectMocks
    UpdateHandler handler;

    @Mock
    UserContext userContext;

    @Mock
    StartCommand startCommand;

    @Mock
    ExecuteMessage executeMessage;


    public static Stream<Arguments> provideParamsForTests() {
        return Stream.of(
                Arguments.of(START_COMAND, 6666L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    void handle(String text, Long chatId) throws TelegramApiException, IOException {


        Update updateTest = new Update();
        Chat chatTest = new Chat();
        Message message = new Message();
        chatTest.setFirstName("Maksim");
        chatTest.setLastName("Petrov");
        chatTest.setId(chatId);
        message.setChat(chatTest);
        message.setText(text);
        updateTest.setMessage(message);


        when(userContext.getUserContext(chatId)).thenReturn(Collections.singleton("dog"));

        handler.handle(updateTest);

        verify(startCommand).startCallBack(6666L, updateTest);

    }

    @Test
    void returnToStartMenu() {

        Update updateTest = new Update();
        Chat chatTest = new Chat();
        Message message = new Message();
        chatTest.setFirstName("Maksim");
        chatTest.setLastName("Petrov");
        chatTest.setId(6666L);
        message.setChat(chatTest);
        message.setReplyMarkup(null);
        message.setText("Возврат в начальное меню");
        updateTest.setMessage(message);


        handler.returnToStartMenu(updateTest);
        verify(executeMessage).executeMessage(any());
        verify(startCommand).choosingTypeOfPet(6666L);

    }

    @Test
    void processingOfButtonsTest() {

        Update updateTest = new Update();
        CallbackQuery callbackQueryTest = new CallbackQuery();
        callbackQueryTest.setData("Test");
        updateTest.setCallbackQuery(callbackQueryTest);
        Message message = new Message();
        Chat chatTest = new Chat();
        chatTest.setId(6666L);
        message.setChat(chatTest);
        callbackQueryTest.setMessage(message);
        updateTest.setMessage(message);

        handler.processingOfButtons(updateTest);
        verify(startCommand).startCallBack(6666L, updateTest);


    }
}