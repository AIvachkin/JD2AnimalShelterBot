package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
public class CommunicationWithVolunteer {

    /**
     * Иинъекция класса контекста пользователя
     */
    private final UserContext userContext;
    private final TelegramBot telegramBot;
    private final CorrespondenceService correspondenceService;
    private final UserService userService;

    /**
     * Сообщение для пользователя, запросившего связь с волонтером
     */
    private static final String CALL_VOLUNTEER_MESSAGE = """

            Напишите, пожалуйста, ниже Ваш вопрос или сообщение волонтеру.
            Волонтер свяжется с Вами в ближайшее время. 👇✍️
            """;

    public CommunicationWithVolunteer(UserContext userContext, TelegramBot telegramBot, CorrespondenceService correspondenceService, UserService userService) {
        this.userContext = userContext;
        this.telegramBot = telegramBot;
        this.correspondenceService = correspondenceService;
        this.userService = userService;
    }

    /**
     * Метод возвращает пользователю сообщение после нажатия на кнопку "Позвать волонтера"
     * @param update объект входящего сообщения от Телеграмм
     */
    public void volunteerButtonHandler(Update update) {
        var chatId = update.getMessage().getChatId();

        if ((userService.getDogUserPhone(chatId) == null && userContext.getUserContext(chatId).contains("dog")) ||
            (userService.getCatUserPhone(chatId) == null && userContext.getUserContext(chatId).contains("cat"))) {
                userService.requestContactDetails(chatId);
                return;
        }

        userContext.setUserContext(chatId, "messageToVolunteer");
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(CALL_VOLUNTEER_MESSAGE);
        message.setReplyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());//Убираем клавиатуру
        executeMessage(message);
        log.info("A call volunteer message has been sent to the user " + update.getMessage().getChat().getFirstName() + ", Id: " + chatId);

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
     * Метод обрабатывает сообщения пользователя волонтеру
     * @param update объект сообщения
     *
     */
    public void volunteerTextHandler(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();
        userContext.deleteUserContext(chatId, "messageToVolunteer");
        correspondenceService.sendMessageToVolunteer(chatId, text);
    }
}
