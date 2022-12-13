package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Класс отправки сообщений
 */
@Component
@Slf4j
public class ExecuteMessage {
    private TelegramBot telegramBot;

    public ExecuteMessage(@Lazy TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Метод проверки отправки сообщения на ошибки
     * @param message  сообщение
     */
    public void executeMessage(SendMessage message){
        try{
            telegramBot.execute(message);
        } catch (TelegramApiException e){
            log.error("Error occurred: " + e.getMessage());
        }
    }
    /**
     * Метод, формирующий ответное сообщение для отправки его пользователю
     */
    public void prepareAndSendMessage(long chatId, String textToSend, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        if (keyboardMarkup != null) {
            message.setReplyMarkup(keyboardMarkup);
        }
        executeMessage(message);
    }
}
