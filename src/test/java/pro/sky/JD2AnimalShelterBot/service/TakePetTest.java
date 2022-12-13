package pro.sky.JD2AnimalShelterBot.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class TakePetTest {


    @InjectMocks
    TakePet takePet;

    @Mock
    ExecuteMessage executeMessage;

    @Test
    void takePetCommandReceivedTest() {

        String testString = "Привет, выбери команду из меню \u2B07";

        ReplyKeyboardMarkup keyboardMarkupTest = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsTest = new ArrayList<>();


        KeyboardRow row1 = new KeyboardRow();
        row1.add("\uD83D\uDC36  Правила знакомства с собакой");
        row1.add("\uD83D\uDCDC  Список необходимых документов");
        keyboardRowsTest.add(row1);

        KeyboardRow row3 = new KeyboardRow();
        row3.add("\uD83C\uDFE1  Дом для щенка");
        row3.add("\uD83C\uDFE1  Дом для взрослой собаки");
        row3.add("\uD83C\uDFE1  Дом для собаки-инвалида");
        keyboardRowsTest.add(row3);

        KeyboardRow row4 = new KeyboardRow();
        row4.add("\uD83D\uDCC3  Советы кинолога");
        row4.add("\uD83C\uDFC5  Лучшие кинологи");
        row4.add("\uD83D\uDEFB  Транспортировка животного");
        keyboardRowsTest.add(row4);

        KeyboardRow row5 = new KeyboardRow();
        row5.add("\uD83D\uDEC7  Почему Вам могут отказать?");
        row5.add("\uD83E\uDDD1\u200D\uD83C\uDF3E️ Позвать волонтера");
        keyboardRowsTest.add(row5);

                keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRowsTest);

        takePet.takePetCommandReceived(6666L);
        verify(executeMessage).prepareAndSendMessage(6666L, testString, keyboardMarkupTest);
    }
}

