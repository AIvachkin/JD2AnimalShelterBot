package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.DogUser;

/**
 * Интерфейс работы с БД пользователей
 */
@Repository
public interface DogUserRepository extends CrudRepository<DogUser, Long> {
    @Query(value = "SELECT phone_number from dog_user_data_table where chat_id = ?1", nativeQuery = true)
    String getUserPhoneById(long chatId);
}
