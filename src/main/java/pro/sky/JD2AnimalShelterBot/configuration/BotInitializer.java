package pro.sky.JD2AnimalShelterBot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
/**
 * Класс, инициализирующий бота
 */
public class BotInitializer {

    /**
     * Поле типа TelegramBot для подключения нашего приложения к telegram
     */
    @Autowired
    TelegramBot bot;


    /**
     * метод, инициализирующий бота
     *
     * @throws TelegramApiException - исключение в случае прихода неизвестной для бота команды
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot((LongPollingBot) bot);
        } catch (TelegramApiException e) {
            log.error("Ошибка при вводе команды: " + e.getMessage());
        }
    }
}