package pro.sky.JD2AnimalShelterBot.service.pet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 Класс для работы с БД домашних питомцев
 */
@Service
@Slf4j
public class PetService {
    private final PetRepository petRepository;
    private final DogUserRepository dogUserRepository;
    public PetService(PetRepository petRepository, DogUserRepository dogUserRepository) {
        this.petRepository = petRepository;
        this.dogUserRepository = dogUserRepository;
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
    public void delete(Long petId) throws NotFoundException {
        log.info("Was invoked method for delete pet by id");
        if (getById(petId) != null) {
            petRepository.deleteById(petId);
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Метод редактирования домашнего питомца в БД
     * @param pet домашний питомец
     */
    public Pet updatePet(Pet pet) {
        return petRepository.save(pet);
    }

    /**
     * Метод для получения всех животных из базы
     * @return - возвращает список всех животных
     */
    public List<Pet> getAllPets() {
        return (List<Pet>) petRepository.findAll();
    }

    /**
     * Метод для закрепления животного за попечителем в базе
     * @param petId ИД животного
     * @param chatId ИД попечителя
     */
    public void assignPetToCaregiver(Long petId, Long chatId) {
        DogUser dogUser = dogUserRepository.findById(chatId).orElseThrow();
        Pet pet = petRepository.findById(petId).orElseThrow();
        pet.setDogUser(dogUser);
        petRepository.save(pet);
    }

    /**
     * Метод для открепления животного от попечителя в базе
     * @param petId ИД животного
     */
    public void detachPetFromCaregiver(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow();
        pet.setDogUser(null);
        petRepository.save(pet);
    }
}
