package pro.sky.JD2AnimalShelterBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import pro.sky.JD2AnimalShelterBot.model.User;
import pro.sky.JD2AnimalShelterBot.repository.UserRepository;


@Service
/**
 * Класс реализует логику по взаимодействию с объектами User
 */
public class UserService {

    /**
     * Поле - для инжекции в класс репозитория для возможности взаимодействия с БД
     */
    private final UserRepository userRepository;


    /**
     * Конструктор - создание нового объекта - репозитория - для работы с его методами для взаимодействия с БД
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Метод, создающий объект User и сохраняющий его в БД
     */
    public void createUser(Message message) {

        if (userRepository.findById(message.getChatId()).isEmpty()) {
/**
 * Временные переменные для получения данных для создания объекта типа User
 */
            Long chatId = message.getChatId();
            Chat chat = message.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstname(chat.getFirstName());
            user.setLastname(chat.getLastName());
            userRepository.save(user);


        }
    }

    /**
     * Метод для полученеия сущности пользователя по ИД
     *
     * @param chatId ИД пользователя
     * @return - возвращает пользователя из БД
     */
    public User getUser(long chatId) {
        return userRepository.findById(chatId).orElseThrow();
    }
}
