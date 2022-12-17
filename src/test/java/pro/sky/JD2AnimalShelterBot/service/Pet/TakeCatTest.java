package pro.sky.JD2AnimalShelterBot.service.Pet;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.service.ExecuteMessage;
import pro.sky.JD2AnimalShelterBot.service.pet.TakeCat;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static pro.sky.JD2AnimalShelterBot.сonstants.CatConstants.RECOMM_FOR_CAT_COMMAND_LABEL;
import static pro.sky.JD2AnimalShelterBot.сonstants.DogConstants.*;
import static pro.sky.JD2AnimalShelterBot.сonstants.MainMenuConstants.*;

@ExtendWith(MockitoExtension.class)
public class TakeCatTest {

    @InjectMocks
    TakeCat takeCat;

    @Mock
    ExecuteMessage executeMessage;

    @Test
    void takePetCommandReceivedTest() {

        ReplyKeyboardMarkup keyboardMarkupTest = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowsTest = new ArrayList<>();


        KeyboardRow row1 = new KeyboardRow();
        row1.add(DATING_RULES_COMMAND_LABEL);
        row1.add(DOCUMENTS_COMMAND_LABEL);
        keyboardRowsTest.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(RECOMM_FOR_CAT_COMMAND_LABEL);
        row2.add(RECOMM_FOR_PET_INVALID_COMMAND_LABEL);
        row2.add(SHIPPING_COMMAND_LABEL);
        keyboardRowsTest.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(REASONS_FOR_REFUSAL_COMMAND_LABEL);
        row3.add(CALL_VOLUNTEER_COMMAND_LABEL);
        row3.add(MAIN_MENU_LABEL);
        keyboardRowsTest.add(row3);


        keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRowsTest);

        takeCat.takePetCommandReceived(6666L);
        verify(executeMessage).prepareAndSendMessage(6666L, MENU_COMMAND_SELECTION, keyboardMarkupTest);
    }

}
