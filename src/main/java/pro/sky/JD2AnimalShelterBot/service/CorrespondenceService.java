package pro.sky.JD2AnimalShelterBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.model.User;
import pro.sky.JD2AnimalShelterBot.repository.CorrespondenceRepository;
import pro.sky.JD2AnimalShelterBot.repository.UserRepository;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;

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
     * Конструктор - создание нового объекта - репозитория - для работы с его методами для взаимодействия с БД
     */
    public CorrespondenceService(CorrespondenceRepository correspondenceRepository) {
        this.correspondenceRepository = correspondenceRepository;
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
}
