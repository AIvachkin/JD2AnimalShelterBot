package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс обрабатывает комманду /start
 * Бот приветствует нового пользователя, рассказывает о себе
 */
@Service
@Slf4j
public class StartCommand {

    /**
     * Поле взамодействия с ботом
     */
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final ExecuteMessage executeMessage;
    /**
     * Конструктор - создание нового объекта класса StartCommand для определенного бота
     *
     * @param telegramBot    - объект взаимодействия с ботом
     * @param userService    - объект для взаимодействия с командами сервиса
     * @param executeMessage
     */
    public StartCommand(TelegramBot telegramBot, UserService userService, ExecuteMessage executeMessage) {
        this.telegramBot = telegramBot;
        this.userService = userService;
        this.executeMessage = executeMessage;
    }

    /**
     * Приветствие
     */
    private static final String GREETING = "\uD83D\uDE00 Привет ";

    /**
     * Приветственное сообщение
     */
    private static final String WELCOME_MESSAGE = """

            Я могу ответить на твои вопросы о том, что нужно знать и уметь, чтобы забрать животное из приюта
            Помогу разобраться как с бюрократическими, так и с бытовыми вопросами.
            Выбери пункт меню ниже
            """;

    /**
     * Метод обработки команды /start и добавления в БД пользователя
     * @param chatId  id текущего чата
     * @param update  объект сообщения
     */
    public void startCallBack(long chatId, Update update){
        userService.createUser(update.getMessage());
        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
    }

    /**
     * Метод отправки приветственного сообщения.
     * @param chatId  id текущего чата
     * @param firstName  имя пользователя
     */
    public void startCommandReceived(long chatId, String firstName) {
        String textToSend = GREETING + firstName + "! " + WELCOME_MESSAGE;

        //Добавление клавиатуры к собщению.

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(Commands.INFORMATION_COMAND.getLabel());
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(Commands.TAKE_PET_COMAND.getLabel());
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(Commands.SEND_REPORT_COMAND.getLabel());
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(Commands.CALL_VOLUNTEER_COMAND.getLabel());
        keyboardRows.add(row);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);//Формирование клавиатуры

       executeMessage.prepareAndSendMessage(chatId,textToSend,keyboardMarkup);
        log.info("A welcome message has been sent to the user " + firstName + ", Id: " + chatId);

    }
}
