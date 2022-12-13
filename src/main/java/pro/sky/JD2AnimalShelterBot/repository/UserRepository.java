package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.User;

/**
 * Интерфейс работы с БД пользователей
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT phone_number from user_data_table where chat_id = ?1", nativeQuery = true)
    String getUserPhoneById(long chatId);
}
