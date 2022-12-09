package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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

        startCommand.startCallBack(6666L, updateTest);

        verify(userService).createUser(message);

//        не получается сделать проверку вызова метода startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
//        соответственно, и проверка корректности передачи chatid не работает
    }

    @Test
    void startCommandReceived() {

        String testString = GREETING + "Maksim! " + WELCOME_MESSAGE;

        ReplyKeyboardMarkup keyboardMarkupTest = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsTest = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("❓ Узнать информацию о приюте");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDC36️ Как взять собаку из приюта");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDDD3 Прислать отчет о питомце");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83E\uDDD1\u200D\uD83C\uDF3E️ Позвать волонтера");
        keyboardRowsTest.add(row);
        keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRowsTest);

        startCommand.startCommandReceived(6666L, "Maksim");
        verify(executeMessage).prepareAndSendMessage(6666L, testString, keyboardMarkupTest);

    }
}