package pro.sky.JD2AnimalShelterBot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

/**
 * Класс, инициализирующий бота
 */
@Slf4j
@Component
public class BotInitializer {

    /**
     * Поле типа TelegramBot для подключения нашего приложения к telegram
     */
    private final TelegramBot bot;

    public BotInitializer(TelegramBot bot) {
        this.bot = bot;
    }


    /**
     * метод, инициализирующий бота
     *
     * @throws TelegramApiException - исключение в случае прихода неизвестной для бота команды
     */
    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot((LongPollingBot) bot);
        } catch (TelegramApiException e) {
            log.error("Ошибка при вводе команды: " + e.getMessage());
        }
    }
}
