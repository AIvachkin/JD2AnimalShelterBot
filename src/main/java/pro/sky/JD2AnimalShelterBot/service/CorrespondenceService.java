package pro.sky.JD2AnimalShelterBot.service;

import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.repository.CorrespondenceRepository;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
/**
 * Класс реализует логику по взаимодействию с объектами Correspondence
 */
public class CorrespondenceService {

    /**
     * Поле - для инжекции в класс репозитория для возможности взаимодействия с БД
     */
    private final CorrespondenceRepository correspondenceRepository;

    /**
     * Поле - для инжекции UserService
     */
    private final UserService userService;

    /**
     * Конструктор - создание нового объекта - репозитория - для работы с его методами для взаимодействия с БД
     */
    public CorrespondenceService(CorrespondenceRepository correspondenceRepository, UserService userService) {
        this.correspondenceRepository = correspondenceRepository;
        this.userService = userService;
    }


    /**
     * Метод, запрашивающий из БД все неотвеченные сообщения.
     */
    public List<Correspondence> getUnansweredMessages() {
        return correspondenceRepository.getAllUnansweredMessages();
    }

    /**
     * Метод, запрашивающий из БД сообщение по ИД.
     */
    public Correspondence getMessagesById(long messageId) {
        return correspondenceRepository.getCorrespondenceByMessageId(messageId);
    }

    /**
     * Метод для ответа волонтера на сообщение пользователя
     * @param messageId ИД сообщения, на которое дается ответ
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
        correspondenceRepository.save(reply);
    }

    /**
     * Метод для отправки сообщения пользователю
     * @param chatId ИД пользователя
     * @param text текст сообщения
     */
    public void sendMessage(long chatId, String text) throws NoSuchElementException {
        userService.getUser(chatId);// проверка наличия такого chatId
        Correspondence reply = new Correspondence();
        reply.setAnswered(true);
        reply.setChatId(chatId);
        reply.setDateTime(LocalDateTime.now());
        reply.setText(text);
        reply.setWhoSentIt("volunteer");
        correspondenceRepository.save(reply);
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
