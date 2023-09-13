package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.handlers.Commands;

import java.util.ArrayList;
import java.util.List;

import static pro.sky.JD2AnimalShelterBot.constants.ShelterConstants.GREETING_INFO;

/**
 * Класс для обработки запросов пользователей, желающих получить информацию о приюте
 */
@Slf4j
@Service
public class ShelterInfo {
    private final ExecuteMessage executeMessage;
    protected ShelterInfo(ExecuteMessage executeMessage) {
        this.executeMessage = executeMessage;
    }

    /**
     * Метод для формирования клавиатуры
     *
     * @param chatId id текущего чата
     */
    public void shelterInfoCommandReceived(long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = createMenuShelterInfo();
        executeMessage.prepareAndSendMessage(chatId, GREETING_INFO, keyboardMarkup);
    }

    /**
     * Метод создания кнопочного меню
     */
    public ReplyKeyboardMarkup createMenuShelterInfo() {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Commands.SHELTER_INFO_COMMAND.getLabel()));
        row1.add(new KeyboardButton(Commands.SCHEDULE_ADDRESS_COMMAND.getLabel()));
        keyboardRows.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(Commands.SAFETY_RULES_COMMAND.getLabel()));
        row2.add(new KeyboardButton(Commands.CONTACT_DATA_COMMAND.getLabel()));
        keyboardRows.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton(Commands.CALL_VOLUNTEER_COMAND.getLabel()));
        row3.add(new KeyboardButton(Commands.MAIN_MENU_COMMAND.getLabel()));
        keyboardRows.add(row3);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);//Формирование клавиатуры

        log.info("Keyboard ShelterInfo has been added to bot");

        return keyboardMarkup;
    }
}