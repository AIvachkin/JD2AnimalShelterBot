package pro.sky.JD2AnimalShelterBot.service.user;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс хранит в себе контекст пользователя
 */
@Component
public class UserContext {

    /**
     * Мапа для хранения контекста пользователей
     */
    private final Map<Long, Set<String>> userContext = new HashMap<>();

    /**
     * Метод для сохранения контекста пользователя
     * @param chatId id пользователя
     * @param context состояние пользователя
     */
    public void setUserContext(Long chatId, String context) {
        Set<String> contextSet = this.getUserContext(chatId);
        if(contextSet == null){
            contextSet = new HashSet<>();
        }
        contextSet.add(context);

        userContext.put(chatId, contextSet);
    }

    /**
     * Метод для получения контекста пользователя по id
     * @param chatId id пользователя
     * @return возвращает контекст конкретного пользователя
     */
    public Set<String> getUserContext(Long chatId) {
        return userContext.get(chatId);
    }


    /**
     * Метод удаляет значение из контекста юзера
     * @param chatId id пользователя
     * @param context контекст, который необходимо удалить
     */
    public void deleteUserContext(long chatId, String context) {
        Set<String> contextSet = this.getUserContext(chatId);
        contextSet.remove(context);
    }
}
