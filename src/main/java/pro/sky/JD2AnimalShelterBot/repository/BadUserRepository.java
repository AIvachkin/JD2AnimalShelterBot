package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.BadUser;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;

/**
 * Интерфейс работы с БД должников отчетов
 */
@Repository
public interface BadUserRepository extends CrudRepository<BadUser, Long> {
    BadUser findBadUserByCatUser(CatUser catUser);
    BadUser findBadUserByDogUser(DogUser dogUser);
}
