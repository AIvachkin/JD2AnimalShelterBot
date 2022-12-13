package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    SendMessage sendMessage;

    @Mock
    TelegramBot telegramBot;

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

    }

//    @Test
//    void startCommandReceived() {
//
//    }

    @Test
    void executeMessage() throws TelegramApiException {

        sendMessageTest.setText("Test message");

        startCommand.executeMessage(sendMessageTest);
        verify(telegramBot).execute(sendMessageTest);
    }
}