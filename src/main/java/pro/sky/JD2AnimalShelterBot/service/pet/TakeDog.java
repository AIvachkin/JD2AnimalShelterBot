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
 * к приему собаки в свою семью
 */
@Slf4j
@Service
public class TakeDog implements TakePet{


    public final TelegramBot bot;
    private final ExecuteMessage executeMessage;

    public TakeDog(TelegramBot bot, ExecuteMessage executeMessage) {
        this.bot = bot;
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

        log.info("Created a keyboard for the class TakeDog for ID: " + chatId);

    }

    /**
     * Метод для создания кнопок меню класса TakeDog
     * */
    @Override
    public ReplyKeyboardMarkup createMenuTakePet() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Commands.DOG_DATING_RULES_COMMAND.getLabel());
        row1.add(Commands.DOG_DOCUMENTS_COMMAND.getLabel());
        keyboardRows.add(row1);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(Commands.RECOMM_FOR_PUPPY_COMMAND.getLabel());
        row3.add(Commands.RECOMM_FOR_DOG_COMMAND.getLabel());
        row3.add(Commands.RECOMM_FOR_DOG_INVALID_COMMAND.getLabel());
        keyboardRows.add(row3);

        KeyboardRow row4 = new KeyboardRow();
        row4.add(Commands.CYNOLOGIST_INITIAL_ADVICE_COMMAND.getLabel());
        row4.add(Commands.RECOMMENDED_CYNOLOGIST_COMMAND.getLabel());
        row4.add(Commands.DOG_SHIPPING_COMMAND.getLabel());
        keyboardRows.add(row4);

        KeyboardRow row5 = new KeyboardRow();
        row5.add(Commands.REASONS_FOR_REFUSAL_COMMAND.getLabel());
        row5.add(Commands.CALL_VOLUNTEER_COMAND.getLabel());
        row5.add(Commands.MAIN_MENU_COMMAND.getLabel());
        keyboardRows.add(row5);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);

        return keyboardMarkup;

    }

}


