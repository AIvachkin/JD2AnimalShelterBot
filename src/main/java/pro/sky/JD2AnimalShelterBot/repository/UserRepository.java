package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.User;

/**
 * Интерфейс работы с БД пользователей
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
