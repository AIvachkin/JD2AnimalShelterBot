package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.repository.CatUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;

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
    private final DogUserRepository dogUserRepository;
    private final CatUserRepository catUserRepository;

    private final ExecuteMessage executeMessage;

    private static final String WELCOME_MESSAGE = "Чтобы связаться с волонтером, пожалуйста, отправьте нам свои контакты." +
            "\n\nДля этого нажмите кнопку внизу и подтвердите передучу контактов.";

    private static final String SHARE_PHONE_NUMBER = "\uD83D\uDC49 Поделиться контактами \uD83D\uDC48";

    /**
     * Конструктор - создание нового объекта - репозитория - для работы с его методами для взаимодействия с БД
     */
    public UserService(DogUserRepository dogUserRepository, CatUserRepository catUserRepository, ExecuteMessage executeMessage) {
        this.dogUserRepository = dogUserRepository;
        this.catUserRepository = catUserRepository;
        this.executeMessage = executeMessage;
    }


    /**
     * Метод, создающий объект DogUser и сохраняющий его в БД
     */
    public void createDogUser(long chatId, Update update) {
        if (dogUserRepository.findById(chatId).isEmpty()) {
            Message message = update.getCallbackQuery().getMessage();
            Chat chat = message.getChat();
            DogUser dogUser = new DogUser();
            dogUser.setChatId(chatId);
            dogUser.setFirstname(chat.getFirstName());
            dogUser.setLastname(chat.getLastName());
            dogUserRepository.save(dogUser);
        }
    }

    /**
     * Метод, создающий объект CatUser и сохраняющий его в БД
     */
    public void createCatUser(long chatId, Update update) {
        if (catUserRepository.findById(chatId).isEmpty()) {
            Message message = update.getCallbackQuery().getMessage();
            Chat chat = message.getChat();
            CatUser catUser = new CatUser();
            catUser.setChatId(chatId);
            catUser.setFirstname(chat.getFirstName());
            catUser.setLastname(chat.getLastName());
            catUserRepository.save(catUser);
        }
    }

    /**
     * Метод для полученеия сущности пользователя приюта собак по ИД
     *
     * @param chatId ИД пользователя
     * @return - возвращает пользователя из БД
     */
    public DogUser getDogUserById(long chatId) {
        return dogUserRepository.findById(chatId).orElseThrow();
    }

    /**
     * Метод для полученеия сущности пользователя приюта кошек по ИД
     *
     * @param chatId ИД пользователя
     * @return - возвращает пользователя из БД
     */
    public CatUser getCatUserById(long chatId) {
        return catUserRepository.findById(chatId).orElseThrow();
    }

    /**
     * Метод получает из базы тедефон пользователя приюта собак по ИД
     * @param chatId ИД пользователя
     */
    public String getDogUserPhone(long chatId) {
        return dogUserRepository.getUserPhoneById(chatId);
    }

    /**
     * Метод получает из базы тедефон пользователя приюта кошек по ИД
     * @param chatId ИД пользователя
     */
    public String getCatUserPhone(long chatId) {
        return catUserRepository.getUserPhoneById(chatId);
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

    /**
     * Метод для записи в базу телефона пользователя приюта для собак
     * @param msg объект сообщения
     */
    public void setDodUserPhone(Message msg) {
        DogUser dogUser = dogUserRepository.findById(msg.getChatId()).orElseThrow();
        dogUser.setPhoneNumber(msg.getContact().getPhoneNumber());
        dogUserRepository.save(dogUser);
        log.info("DogUsers " +  msg.getChatId() + " saved");
    }

    /**
     * Метод для записи в базу телефона пользователя приюта для кошек
     * @param msg объект сообщения
     */
    public void setCatUserPhone(Message msg) {
        CatUser catUser = catUserRepository.findById(msg.getChatId()).orElseThrow();
        catUser.setPhoneNumber(msg.getContact().getPhoneNumber());
        catUserRepository.save(catUser);
        log.info("CatUsers " +  msg.getChatId() + " saved");
    }

    /**
     * Метод возвращает список пользователей приюта для собак
     * @return
     */
    public List<DogUser> getAllDogUsers() {
        return (List)dogUserRepository.findAll();
    }

    /**
     * Метод возвращает список пользователей приюта для кошек
     * @return
     */
    public List<CatUser> getAllCatUsers() {
        return (List)catUserRepository.findAll();
    }
}
