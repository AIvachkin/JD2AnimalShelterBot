package pro.sky.JD2AnimalShelterBot.service.pet;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface TakePet {

    void takePetCommandReceived(long chatId);

    ReplyKeyboardMarkup createMenuTakePet();
}
