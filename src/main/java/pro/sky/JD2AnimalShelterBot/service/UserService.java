package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.model.User;
import pro.sky.JD2AnimalShelterBot.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
/**
 * Класс реализует логику по взаимодействию с объектами User
 */
public class UserService {

    /**
     * Поле - для инжекции в класс репозитория для возможности взаимодействия с БД
     */
    private final UserRepository userRepository;

    private final ExecuteMessage executeMessage;

    private static final String WELCOME_MESSAGE = "Чтобы связаться с волонтером, пожалуйста, отправьте нам свои контакты." +
            "\n\nДля этого нажмите кнопку внизу и подтвердите передучу контактов.";

    private static final String SHARE_PHONE_NUMBER = "\uD83D\uDC49 Поделиться контактами \uD83D\uDC48";

    /**
     * Конструктор - создание нового объекта - репозитория - для работы с его методами для взаимодействия с БД
     */
    public UserService(UserRepository userRepository, ExecuteMessage executeMessage) {
        this.userRepository = userRepository;
        this.executeMessage = executeMessage;
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

    /**
     * Метод получает из базы тедефон пользователя по ИД
     * @param chatId ИД пользователя
     */
    public String getUserPhone(long chatId) {
        return userRepository.getUserPhoneById(chatId);
    }

    public void requestContactDetails(Long chatId) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(WELCOME_MESSAGE);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(SHARE_PHONE_NUMBER);
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        executeMessage.executeMessage(sendMessage);
    }

    public void setUserPhone(Message msg) {
        User user = userRepository.findById(msg.getChatId()).orElseThrow();
        user.setPhoneNumber(msg.getContact().getPhoneNumber());
        userRepository.save(user);
        log.info("Users " +  msg.getChatId() + " saved");

    }
}
