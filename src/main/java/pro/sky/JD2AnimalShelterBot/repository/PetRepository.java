package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.model.Pet;
/**
 * Интерфейс работы с БД домашних питомцев
 */
@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {

    Pet findPetByCatUser(CatUser catUser);
    Pet findPetByDogUser(DogUser dogUser);
}
