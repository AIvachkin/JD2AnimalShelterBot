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
import pro.sky.JD2AnimalShelterBot.handlers.Commands;
import pro.sky.JD2AnimalShelterBot.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;


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
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Commands.INFORMATION_COMMAND.getLabel());
        row1.add(Commands.TAKE_PET_COMMAND.getLabel());
        keyboardRows.add(row1);


        KeyboardRow row2 = new KeyboardRow();
        row2.add(Commands.SEND_REPORT_COMMAND.getLabel());
        row2.add(Commands.CAR_PASS_COMMAND.getLabel());
        keyboardRows.add(row2);


        KeyboardRow row3 = new KeyboardRow();
        row3.add(Commands.SAFETY_PRECAUTIONS_COMMAND.getLabel());
        row3.add(Commands.CALL_VOLUNTEER_COMAND.getLabel());
        keyboardRows.add(row3);

        keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRows);

        startCommand.startCommandReceived(6666L, "Maksim");
        verify(executeMessage).prepareAndSendMessage(6666L, testString, keyboardMarkupTest);

    }
}