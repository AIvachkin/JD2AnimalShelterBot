package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.model.User;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.UserRepository;

import java.util.List;

/**
 Класс для работы с БД домашних питомцев
 */
@Service
@Slf4j
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
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

    /**
     * Метод редактирования домашнего питомца в БД
     * @param pet домашний питомец
     */
    public Pet updatePet(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return (List<Pet>) petRepository.findAll();
    }

    public void assignPetToCaregiver(Long petId, Long chatId) {
        User user = userRepository.findById(chatId).orElseThrow();
        Pet pet = petRepository.findById(petId).orElseThrow();
        pet.setUser(user);
        petRepository.save(pet);
    }

    public void detachPetFromCaregiver(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow();
        pet.setUser(null);
        petRepository.save(pet);
    }
}
