package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.BadUser;


/**
 * Интерфейс работы с БД должников отчетов
 */
@Repository
public interface BadUserRepository extends CrudRepository<BadUser, Long> {

    void deleteById (Long id);
}
