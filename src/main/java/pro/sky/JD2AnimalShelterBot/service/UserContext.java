package pro.sky.JD2AnimalShelterBot.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс хранит в себе контекст пользователя
 */
@Component
public class UserContext {

    /**
     * Мапа для хранения контекста пользователей
     */
    Map<Long, String> userContext = new HashMap<>();

    /**
     * Метод для сохранения контекста пользователя
     * @param chatId ИД пользователя
     * @param context состояние пользователя
     */
    public void setUserContext(Long chatId, String context) {
        userContext.put(chatId, context);
    }

    /**
     * Метод для получения контекста пользователя по ИД
     * @param chatId ИД пользователя
     * @return возвращает контекст конкретного пользователя
     */
    public String getUserContext(Long chatId) {
        return userContext.get(chatId);
    }

}
