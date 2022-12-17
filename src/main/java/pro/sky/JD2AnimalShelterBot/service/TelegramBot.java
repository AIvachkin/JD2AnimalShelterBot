package pro.sky.JD2AnimalShelterBot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.JD2AnimalShelterBot.configuration.BotConfiguration;
import pro.sky.JD2AnimalShelterBot.handlers.UpdateHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
/**
 * Класс реалиует взаимодействие с Телеграмом
 * Расширяет класс TelegramLongPollingBot, позволяющий боту самостоятельно проверять,
 * пришло ли от пользователя что-то
 */
public class TelegramBot extends TelegramLongPollingBot {

    /**
     * Поле - конфигурация: для работы методов по получению имени бота и его токена
     */
    private final BotConfiguration configuration;

    /**
     * Поле - обработчик: для вызова метода по обработке входящих команд пользователя
     */
    private final UpdateHandler updateHandler;

    /**
     * Конструктор - создание нового объекта с определенным значением конфигурации
     *
     * @param configuration - конфигурация бота: имя и токен
     *                      дополнительно создается меню для бота
     *                      listOfCommands - лист, содержащий команды меню
     * @param updateHandler - сущность для вызова метода обработчика
     */
    public TelegramBot(BotConfiguration configuration, UpdateHandler updateHandler) {
        this.configuration = configuration;
        this.updateHandler = updateHandler;
        setupTextMenu();
    }

    /**
     * Метод, создающий текстовое меню
     *
     * @throws TelegramApiException - ошибка в случае прихода неизвестной для бота команды
     */
    private void setupTextMenu() {

        List<BotCommand> listOfCommands = new ArrayList<>();

        listOfCommands.add(new BotCommand("/start", "Главное меню"));
        listOfCommands.add(new BotCommand("/choose_a_shelter", "Выбрать приют"));

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Ошибка ввода команды: " + e.getMessage());
        }
    }

    /**
     * метод, предоставляющий имя бота
     *
     * @return возвращает имя бота
     */
    @Override
    public String getBotUsername() {
        return configuration.getBotName();
    }

    @Override
    public String getBotToken() {
        return configuration.getToken();
    }

    /**
     * метод проверяет, что мы получили сообщение, и что это сообщение содержит текст,
     * после чего направляет сообщение в обработчик
     *
     * @param update - сообщение пользователя (содержит в т.ч. инфо о пользователе)
     */
    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.handle(update);
    }
}