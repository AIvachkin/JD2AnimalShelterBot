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

        KeyboardRow row = new KeyboardRow();
        row.add("\uD83D\uDC36  правила знакомства с собакой");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDCDC  список необходимых документов");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDEFB  рекомендации по транспортировке животного");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83C\uDFE1  рекомендации по обустройству дома для щенка");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83C\uDFE1  рекомендации по обустройству дома для взрослой собаки");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83C\uDFE1  рекомендации по обустройству дома для собаки с ограниченными возможностями");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDCC3  советы кинолога по первичному общению с собакой");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83C\uDFC5  лучшие кинологи");
        keyboardRowsTest.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDEC7  причины, по которым Вам могут отказать в усыновлении животного");
        keyboardRowsTest.add(row);

        keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRowsTest);

        takePet.takePetCommandReceived(6666L);
        verify(executeMessage).prepareAndSendMessage(6666L, testString, keyboardMarkupTest);
    }
}

