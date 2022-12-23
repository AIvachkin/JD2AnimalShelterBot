package pro.sky.JD2AnimalShelterBot.service.pet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.service.Commands;
import pro.sky.JD2AnimalShelterBot.service.ExecuteMessage;
import pro.sky.JD2AnimalShelterBot.service.TelegramBot;

import java.util.ArrayList;
import java.util.List;

import static pro.sky.JD2AnimalShelterBot.сonstants.MainMenuConstants.MENU_COMMAND_SELECTION;


/**
 * Класс обрабатывает запросы пользователей, желающих получить информацию о подготовке
 * к приему кошки в свою семью
 */
@Slf4j
@Service
public class TakeCat implements TakePet{

    private final ExecuteMessage executeMessage;

    public TakeCat(ExecuteMessage executeMessage) {
        this.executeMessage = executeMessage;
    }

    /**
     * Метод для формирования клавиатуры
     *
     * @param chatId id текущего чата
     */
    @Override
    public void takePetCommandReceived(long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = createMenuTakePet();

        executeMessage.prepareAndSendMessage(chatId, MENU_COMMAND_SELECTION, keyboardMarkup);

        log.info("Created a keyboard for the class TakeCat for ID: " + chatId);
    }

    /**
     * Метод для создания кнопок меню класса TakeCat
     * */
    @Override
    public ReplyKeyboardMarkup createMenuTakePet() {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Commands.CAT_DATING_RULES_COMMAND.getLabel());
        row1.add(Commands.CAT_DOCUMENTS_COMMAND.getLabel());
        keyboardRows.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(Commands.RECOMM_FOR_CAT_COMMAND.getLabel());
        row2.add(Commands.RECOMM_FOR_CAT_INVALID_COMMAND.getLabel());
        row2.add(Commands.CAT_SHIPPING_COMMAND.getLabel());
        keyboardRows.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(Commands.REASONS_FOR_REFUSAL_COMMAND.getLabel());
        row3.add(Commands.CALL_VOLUNTEER_COMAND.getLabel());
        row3.add(Commands.MAIN_MENU_COMMAND.getLabel());
        keyboardRows.add(row3);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);

        return keyboardMarkup;

    }
}
