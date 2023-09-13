package pro.sky.JD2AnimalShelterBot.service.volunteer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.handlers.Commands;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.repository.CorrespondenceRepository;
import pro.sky.JD2AnimalShelterBot.service.ExecuteMessage;
import pro.sky.JD2AnimalShelterBot.service.user.UserContext;
import pro.sky.JD2AnimalShelterBot.service.user.UserService;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Класс реализует логику по взаимодействию с объектами Correspondence
 */
@Service
@Slf4j
public class CorrespondenceService {

    /**
     * Поле - для инжекции в класс репозитория для возможности взаимодействия с БД
     */
    private final CorrespondenceRepository correspondenceRepository;


    private final UserService userService;

    private final ExecuteMessage executeMessage;
    private final UserContext userContext;

    /**
     * Константа - отбивка о получении сообщения пользователя для волонтера
     */
    private static final String TEXT_TO_SEND = """

            Ваше сообщение отправлено волонтеру приюта.
            Он ответит на Ваше сообщение или свяжется с Вами в ближайшее время.
            """;

    /**
     * Конструктор - создание нового объекта - репозитория - для работы с его методами для взаимодействия с БД
     */
    public CorrespondenceService(CorrespondenceRepository correspondenceRepository,
                                 UserService userService,
                                 ExecuteMessage executeMessage, UserContext userContext) {
        this.correspondenceRepository = correspondenceRepository;
        this.userService = userService;
        this.executeMessage = executeMessage;
        this.userContext = userContext;
    }


    /**
     * Метод, запрашивающий из БД все неотвеченные сообщения.
     */
    public List<Correspondence> getUnansweredMessages() {
        return correspondenceRepository.getAllUnansweredMessages();
    }

    /**
     * Метод, запрашивающий из БД сообщение по id
     */
    public Correspondence getMessagesById(long messageId) {
        return correspondenceRepository.getCorrespondenceByMessageId(messageId);
    }

    /**
     * Метод для ответа волонтера на сообщение пользователя
     * @param messageId id сообщения, на которое дается ответ
     * @param text текст ответного сообщения
     */
    public void replyToMessages(Long messageId, String text) throws NotFoundException  {
        Correspondence message = this.getMessagesById(messageId);
        if(message == null){
            throw new NotFoundException("Сообщение с таким ид не найдено");
        }
        message.setAnswered(true);
        correspondenceRepository.save(message);
        Correspondence reply = new Correspondence();
        reply.setAnswered(true);
        reply.setChatId(message.getChatId());
        reply.setDateTime(LocalDateTime.now());
        reply.setText(text);
        reply.setWhoSentIt("volunteer");
        reply.setTypeOfPet(message.getTypeOfPet());
        correspondenceRepository.save(reply);

        String replyText = "Вы писали:\n" + message.getText() + "\nОтвет волонтера: \n" + text;
        executeMessage.prepareAndSendMessage(message.getChatId(), replyText, null);
    }

    /**
     * Метод для отправки сообщения пользователю
     * @param chatId id пользователя
     * @param text текст сообщения
     */
    public void sendMessage(long chatId, String text) throws NoSuchElementException {
        userService.getDogUserById(chatId);// проверка наличия такого chatId
        Correspondence reply = new Correspondence();
        reply.setAnswered(true);
        reply.setChatId(chatId);
        reply.setDateTime(LocalDateTime.now());
        reply.setText(text);
        reply.setWhoSentIt("volunteer");
        correspondenceRepository.save(reply);

        String replyText = "Сообщение от волонтера приюта: \n" + text;
        executeMessage.prepareAndSendMessage(chatId, replyText, null);
    }

    /**
     * Метод для отправки сообщения от пользователя волонтеру
     * @param chatId id пользователя
     * @param text текст сообщения
     */
    public void sendMessageToVolunteer(long chatId, String text) {
        Correspondence reply = new Correspondence();
        reply.setAnswered(false);
        reply.setChatId(chatId);
        reply.setDateTime(LocalDateTime.now());
        reply.setText(text);
        reply.setWhoSentIt("user");
        if(userContext.getUserContext(chatId).contains("dog")){
            reply.setTypeOfPet("dog");
        } else if(userContext.getUserContext(chatId).contains("cat")) {
            reply.setTypeOfPet("cat");
        }
        correspondenceRepository.save(reply);

        //Добавление клавиатуры к сообщению
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(Commands.INFORMATION_COMMAND.getLabel());
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(Commands.TAKE_PET_COMMAND.getLabel());
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(Commands.SEND_REPORT_COMMAND.getLabel());
        keyboardRows.add(row);
        row = new KeyboardRow();
        row.add(Commands.CALL_VOLUNTEER_COMAND.getLabel());
        keyboardRows.add(row);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);//Формирование клавиатуры

        executeMessage.prepareAndSendMessage(chatId,TEXT_TO_SEND,keyboardMarkup);
        log.info("A message to the volunteer from user " + chatId + " has been saved to the database.");

    }

    /**
     * Метод, запрашивающий из БД всю переписку с конкретным пользователем.
     */
    public List<Correspondence> getAllCorrespondenceWithUser(long chatId) throws NotFoundException {
        List<Correspondence> correspondences = correspondenceRepository.getAllCorrespondenceWithUser(chatId);
        if(correspondences.size() > 0){
            return correspondences;
        } else{
            throw new NotFoundException();
        }
    }
}
