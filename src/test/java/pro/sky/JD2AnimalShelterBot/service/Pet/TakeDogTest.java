package pro.sky.JD2AnimalShelterBot.service.Pet;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.service.ExecuteMessage;
import pro.sky.JD2AnimalShelterBot.service.pet.TakeDog;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static pro.sky.JD2AnimalShelterBot.constants.DogConstants.*;
import static pro.sky.JD2AnimalShelterBot.constants.MainMenuConstants.CALL_VOLUNTEER_COMMAND_LABEL;
import static pro.sky.JD2AnimalShelterBot.constants.MainMenuConstants.MAIN_MENU_LABEL;


@ExtendWith(MockitoExtension.class)
class TakeDogTest {


    @InjectMocks
    TakeDog takeDog;

    @Mock
    ExecuteMessage executeMessage;

    @Test
    void takePetCommandReceivedTest() {

        String testString = "Привет, выбери команду из меню \u2B07";

        ReplyKeyboardMarkup keyboardMarkupTest = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsTest = new ArrayList<>();


        KeyboardRow row1 = new KeyboardRow();
        row1.add(DATING_RULES_COMMAND_LABEL);
        row1.add(DOCUMENTS_COMMAND_LABEL);
        keyboardRowsTest.add(row1);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(RECOMM_FOR_PUPPY_COMMAND_LABEL);
        row3.add(RECOMM_FOR_DOG_COMMAND_LABEL);
        row3.add(RECOMM_FOR_PET_INVALID_COMMAND_LABEL);
        keyboardRowsTest.add(row3);

        KeyboardRow row4 = new KeyboardRow();
        row4.add(CYNOLOGIST_INITIAL_ADVICE_COMMAND_LABEL);
        row4.add(RECOMMENDED_CYNOLOGIST_COMMAND_LABEL);
        row4.add(SHIPPING_COMMAND_LABEL);
        keyboardRowsTest.add(row4);

        KeyboardRow row5 = new KeyboardRow();
        row5.add(REASONS_FOR_REFUSAL_COMMAND_LABEL);
        row5.add(CALL_VOLUNTEER_COMMAND_LABEL);
        row5.add(MAIN_MENU_LABEL);
        keyboardRowsTest.add(row5);

        keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRowsTest);

        takeDog.takePetCommandReceived(6666L);
        verify(executeMessage).prepareAndSendMessage(6666L, testString, keyboardMarkupTest);
    }
}

