package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс обрабатывает команду /start
 * Бот приветствует нового пользователя, рассказывает о себе
 */
@Service
@Slf4j
public class StartCommand {

    private final UserService userService;
    private final UserContext userContext;
    private final ExecuteMessage executeMessage;
    /**
     * Конструктор - создание нового объекта класса StartCommand для определенного бота
     *
     * @param userService    - объект для взаимодействия с командами сервиса
     * @param userContext    - объект для взаимодействия с контекстом юзера
     * @param executeMessage - объект для взаимодействия с методами класса, отвечающего за отправку ответа пользователям
     */
    public StartCommand(UserService userService, UserContext userContext, ExecuteMessage executeMessage) {
        this.userService = userService;
        this.userContext = userContext;
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

    private static final String SHOOSING_PET_MESSAGE = """
           Пожалуйста, выберите приют:
           
           """;

    public static final String DOG_BUTTON = "Приют для собак";

    public static final String CAT_BUTTON = "Приют для кошек";

    /**
     * Метод обработки команды /start и добавления в БД пользователя
     * @param chatId  id текущего чата
     * @param update  объект сообщения
     */
    public void startCallBack(long chatId, Update update){

        if (userContext.getUserContext(chatId).contains("dog")){
            userService.createDogUser(chatId, update);
        } else if (userContext.getUserContext(chatId).contains("cat")) {
            userService.createCatUser(chatId, update);
        }

        String firstname;
        if(update.getCallbackQuery() != null){
            firstname = update.getCallbackQuery().getMessage().getChat().getFirstName();
        } else {
            firstname = update.getMessage().getChat().getFirstName();
        }
        startCommandReceived(chatId, firstname);
    }

    /**
     * Метод отправки приветственного сообщения.
     * @param chatId  id текущего чата
     * @param firstName  имя пользователя
     */
    public void startCommandReceived(long chatId, String firstName) {
        String textToSend = GREETING + firstName + "! " + WELCOME_MESSAGE;

        //Добавление клавиатуры к собщению.

        ReplyKeyboardMarkup keyboardMarkup = createMenuStartCommand();

        executeMessage.prepareAndSendMessage(chatId,textToSend,keyboardMarkup);
        log.info("A welcome message has been sent to the user " + firstName + ", Id: " + chatId);

    }


    /**
     * Метод для создания кнопок для выбора типа приюта: собаки или кошки
     * @param chatId  id текущего чата
     */
    public void choosingTypeOfPet(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(SHOOSING_PET_MESSAGE);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText(DOG_BUTTON);
        button.setCallbackData(DOG_BUTTON);
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(button);
        rowsInline.add(rowInline);

        button = new InlineKeyboardButton();
        button.setText(CAT_BUTTON);
        button.setCallbackData(CAT_BUTTON);
        rowInline = new ArrayList<>();
        rowInline.add(button);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        executeMessage.executeMessage(message);

    }

    /**
     * Метод для создания клавиатуры стартового меню
     * */
    public ReplyKeyboardMarkup createMenuStartCommand() {


        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(Commands.INFORMATION_COMMAND.getLabel());
        row1.add(Commands.TAKE_PET_COMMAND.getLabel());
        keyboardRows.add(row1);


        KeyboardRow row2 = new KeyboardRow();
        row2.add(Commands.SEND_REPORT_COMMAND.getLabel());
        row2.add(Commands.CAR_PASS_COMMAND.getLabel());
        keyboardRows.add(row2);


        KeyboardRow row3 = new KeyboardRow();
        row3.add(Commands.SAFETY_PRECAUTIONS_COMMAND.getLabel());
        row3.add(Commands.CALL_VOLUNTEER_COMAND.getLabel());
        keyboardRows.add(row3);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);//Формирование клавиатуры

        return keyboardMarkup;
    }

    /**
     * Метод, возвращающий пользователя в стартовое меню
     * */
    public void returnToMainMenu(long chatId) {

        ReplyKeyboardMarkup keyboardMarkup = createMenuStartCommand();
        executeMessage.prepareAndSendMessage(chatId, "Возврат в основное меню выполнен", keyboardMarkup);
    }
}
