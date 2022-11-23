package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@Slf4j
/*
 Класс обрабатывает комманду /start
 Бот приветствует нового пользователя, рассказывает о себе
 */
public class StartCommand {

    /*
    Инжекция бота
     */
    private final TelegramBot telegramBot;

    public StartCommand(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /*Приветствие*/
    private static final String GREETING = "\uD83D\uDE00 Привет ";

    /*Приветственное сообщение*/
    private static final String WELCOME_MESSAGE = "\n\nЯ могу ответить на твои вопросы о том, что нужно знать и уметь, " +
            "чтобы забрать животное из приюта\n" +
            "Помогу разобраться как с бюрократическими, так и с бытовыми вопросами.\n" +
            "Выбери пункт меню ниже";

    /*
     *Метод обработки команды /start.
     * @param chatId  id текущего чата
     * @param update  объект сообщения
     */
    public void startCallBack(long chatId, Update update){
        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
    }

    /*
     *Метод отправки приветственного сообщения.
     * @param chatId  id текущего чата
     * @param firstName  имя пользователя
     */
    public void startCommandReceived(long chatId, String firstName) {
        String textToSend = GREETING + firstName + "! " + WELCOME_MESSAGE;
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
        log.info("A welcome message has been sent to the user " + firstName + ", Id: " + chatId);
    }

    /*
    Проверка отправки сообщения на ошибки
     */
    public void executeMessage(SendMessage message){
        try{
            telegramBot.execute(message);
        } catch (TelegramApiException e){
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
