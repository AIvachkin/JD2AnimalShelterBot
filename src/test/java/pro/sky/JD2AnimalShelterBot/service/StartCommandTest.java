package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static pro.sky.JD2AnimalShelterBot.сonstants.MainMenuConstants.*;


@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    private static final String GREETING = "\uD83D\uDE00 Привет ";
    private static final String WELCOME_MESSAGE = """

            Я могу ответить на твои вопросы о том, что нужно знать и уметь, чтобы забрать животное из приюта
            Помогу разобраться как с бюрократическими, так и с бытовыми вопросами.
            Выбери пункт меню ниже
            """;

    @InjectMocks
    StartCommand startCommand;

    @Mock
    UserService userService;

    @Mock
    ExecuteMessage executeMessage;


    @Test
    void startCallBackTest() {

        Update updateTest = new Update();
        Chat chat = new Chat();
        Message message = new Message();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(6666L);
        message.setChat(chat);
        updateTest.setMessage(message);
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(message);
        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        userService.createDogUser(6666L, update);

        startCommand.startCallBack(6666L, updateTest);

        verify(userService).createDogUser(6666L, update);

//        не получается сделать проверку вызова метода startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
//        соответственно, и проверка корректности передачи chatid не работает
    }

    @Test
    void startCommandReceived() {

        String testString = GREETING + "Maksim! " + WELCOME_MESSAGE;

        ReplyKeyboardMarkup keyboardMarkupTest = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsTest = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(INFORMATION_COMAND_LABEL);
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add(TAKE_PET_COMAND_LABEL);
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add(SEND_REPORT_COMAND_LABEL);
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add(CALL_VOLUNTEER_COMAND_LABEL);
        keyboardRowsTest.add(row);
        keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRowsTest);

        startCommand.startCommandReceived(6666L, "Maksim");
        verify(executeMessage).prepareAndSendMessage(6666L, testString, keyboardMarkupTest);

    }
}