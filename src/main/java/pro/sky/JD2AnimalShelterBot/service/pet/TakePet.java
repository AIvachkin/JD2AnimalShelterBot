package pro.sky.JD2AnimalShelterBot.service.pet;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * Класс - интерфейс для обработки запросов пользователей, желающих получить информацию о подготовке
 * к приему питомца в свою семью
 */
public interface TakePet {

    void takePetCommandReceived(long chatId);

    ReplyKeyboardMarkup createMenuTakePet();
}
