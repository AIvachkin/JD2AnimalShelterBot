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
     * Поле - обработчик команды /start
     */
    private final StartCommand startCommand;

    private final ShelterInfo shelterInfo;
    /**
     * Поле - конфигурация: для работы методов по получению имени бота и его токена
     */
    final BotConfiguration configuration;

    /**
     * Конструктор - создание нового объекта с определенным значением конфигурации
     *
     * @param startCommand  - объект обработчика команды /start
     * @param shelterInfo
     * @param configuration - конфигурация бота: имя и токен
     *                      дополнительно создается меню для бота
     *                      listOfCommands - лист, содержащий команды меню
     */
    public TelegramBot(@Lazy StartCommand startCommand, @Lazy ShelterInfo shelterInfo, BotConfiguration configuration) {
        this.startCommand = startCommand;
        this.shelterInfo = shelterInfo;
        this.configuration = configuration;
        setupTextMenu();
    }

    /**
     * Метод, создающий текстовое меню
     *
     * @throws TelegramApiException - ошибка в случае прихода неизвестной для бота команды
     */
    private void setupTextMenu() {

        List<BotCommand> listOfCommands = new ArrayList<>();

        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/about", "brief information about the shelter"));
        listOfCommands.add(new BotCommand("/location", "opening hours, address, directions"));
        listOfCommands.add(new BotCommand("/rules", "safety measures at the shelter"));
        listOfCommands.add(new BotCommand("/callback", "receiving and recording contact information"));
        listOfCommands.add(new BotCommand("/other", "volunteer call"));

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
     * метод, определяющий, что должен делать бот, когда ему поступает тот или иной запрос
     * предварительно проверяет, что мы получили сообщение, и что это сообщение содержит текст
     *
     * @param update - сообщение пользователя (содержит в т.ч. инфо о пользователе)
     */
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            /*
            Поле - текст из сообщения пользователя
             */
            String messageText = update.getMessage().getText();

            /*
              Поле - идентификатор чата, в который бот отправит ответ
             */
            long chatId = update.getMessage().getChatId();

// оператор выбора будет дописан позже после получения полного набора команд
            switch (messageText) {
                case "/start":
                    startCommand.startCallBack(chatId, update);
                    break;
                case "❓ Узнать информацию о приюте":
                    shelterInfo.createMenuShelterInfo(chatId);
                    break;
                default:
            }
        }
    }
}