package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.repository.CrudRepository;
import pro.sky.JD2AnimalShelterBot.model.Pet;
/**
 * Интерфейс работы с БД домашних питомцев
 */
public interface PetRepository extends CrudRepository<Pet, Long> {

}
