package pro.sky.JD2AnimalShelterBot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

/**
 * Обработчик сообщений
 */
public interface InputMessageHandler {

    void handle (Update update) throws IOException;

}
