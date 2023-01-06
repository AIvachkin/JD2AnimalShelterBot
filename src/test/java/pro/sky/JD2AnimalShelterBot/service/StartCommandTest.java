package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.handlers.Commands;
import pro.sky.JD2AnimalShelterBot.service.user.UserContext;
import pro.sky.JD2AnimalShelterBot.service.user.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pro.sky.JD2AnimalShelterBot.service.StartCommand.*;


@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    private static final String GREETING = "\uD83D\uDE00 Привет ";
    private static final String WELCOME_MESSAGE = """

            Я могу ответить на твои вопросы о том, что нужно знать и уметь, чтобы забрать животное из приюта
            Помогу разобраться как с бюрократическими, так и с бытовыми вопросами.
            Выбери пункт меню ниже
            """;


    private static final String SHOOSING_PET_MESSAGE_TEST = """
            Пожалуйста, выберите приют:
                       
            """;

    @InjectMocks
    StartCommand startCommand;


    @Mock
    UserService userService;

    @Mock
    ExecuteMessage executeMessage;

    @Mock
    UserContext userContext;

    private ReplyKeyboardMarkup keyboardMarkupTest;

    @BeforeEach
    void initKeyboardMarkupTest() {

        keyboardMarkupTest = new ReplyKeyboardMarkup();
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

    }


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
        when(userContext.getUserContext(any())).thenReturn(Collections.singleton("dog"));

        startCommand.startCallBack(6666L, updateTest);

        verify(userService).createDogUser(6666L, updateTest);

    }

    @Test
    void startCommandReceivedTest() {

        String testString = GREETING + "Maksim! " + WELCOME_MESSAGE;

        startCommand.startCommandReceived(6666L, "Maksim");
        verify(executeMessage).prepareAndSendMessage(6666L, testString, keyboardMarkupTest);

    }

    @Test
    void choosingTypeOfPetTest() {

        SendMessage messageTest = new SendMessage();
        messageTest.setChatId(String.valueOf(6666L));
        messageTest.setText(SHOOSING_PET_MESSAGE_TEST);

        InlineKeyboardMarkup markupInlineTest = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInlineTest = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText(DOG_BUTTON);
        button.setCallbackData(DOG_BUTTON);
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(button);
        rowsInlineTest.add(rowInline);

        button = new InlineKeyboardButton();
        button.setText(CAT_BUTTON);
        button.setCallbackData(CAT_BUTTON);
        rowInline = new ArrayList<>();
        rowInline.add(button);
        rowsInlineTest.add(rowInline);

        markupInlineTest.setKeyboard(rowsInlineTest);
        messageTest.setReplyMarkup(markupInlineTest);

        startCommand.choosingTypeOfPet(6666L);
        verify(executeMessage).executeMessage(messageTest);

    }

    @Test
    void createMenuStartCommandTest() {

        assertEquals(keyboardMarkupTest, startCommand.createMenuStartCommand());

    }

    @Test
    void returnToMainMenuTest() {

        startCommand.returnToMainMenu(6666L);
        verify(executeMessage).prepareAndSendMessage(6666L, "Возврат в основное меню выполнен", keyboardMarkupTest);

    }

}