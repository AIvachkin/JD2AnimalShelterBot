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
import static pro.sky.JD2AnimalShelterBot.constants.MainMenuConstants.*;
import static pro.sky.JD2AnimalShelterBot.constants.ShelterConstants.*;

@ExtendWith(MockitoExtension.class)
class ShelterInfoTest {

   @InjectMocks
   ShelterInfo shelterInfo;

    @Mock
    ExecuteMessage executeMessage;

    @Test
    void shelterInfoCommandReceivedTest() {

        ReplyKeyboardMarkup keyboardMarkupTest = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(SHELTER_INFO_COMMAND_LABEL);
        row1.add(SCHEDULE_ADDRESS_COMMAND_LABEL);
        keyboardRows.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(SAFETY_RULES_COMMAND_LABEL);
        row2.add(CONTACT_DATA_COMMAND_LABEL);
        keyboardRows.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(CALL_VOLUNTEER_COMMAND_LABEL);
        row3.add(MAIN_MENU_LABEL);
        keyboardRows.add(row3);

        keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRows);

        shelterInfo.shelterInfoCommandReceived(6666L);
        verify(executeMessage).prepareAndSendMessage(6666L, GREETING_INFO, keyboardMarkupTest);

    }


}