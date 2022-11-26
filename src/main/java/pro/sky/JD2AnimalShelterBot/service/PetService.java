package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;

/**
 Класс для работы с БД домашних питомцев
 */
@Service
@Slf4j
public class PetService {
    private final PetRepository petRepository;
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }
    /**
     * Метод получения домашнего питомца
     * @param petId  id домашнего питомца в БД
     */
    public Pet getById(Long petId) {
        log.info("Was invoked method for get pet by id");
        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet == null) {
            log.warn("Getting pet = null");
            log.error("There is not pet with id = {}",petId);
        }
        log.debug("Getting pet = {}",pet);
        return pet;
    }
    /**
     * Метод добавления домашнего питомца в БД
     * @param pet домашний питомец
     */
    public Pet createPet(Pet pet) {
        log.info("Was invoked method for update pet");
        return petRepository.save(pet);
    }
    /**
     * Метод удаления домашнего питомца из БД
     * @param petId id домашнего питомца в БД
     */
    public void delete(Long petId) {
        log.info("Was invoked method for delete pet by id");
        if (getById(petId) != null) {
            petRepository.deleteById(petId);
        }
    }
}
