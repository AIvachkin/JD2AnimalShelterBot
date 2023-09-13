package pro.sky.JD2AnimalShelterBot.service.pet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.repository.CatUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;
import pro.sky.JD2AnimalShelterBot.service.ExecuteMessage;

import java.time.LocalDate;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;

import static pro.sky.JD2AnimalShelterBot.constants.ShelterConstants.PROBATION_PERIOD_FAILED;
import static pro.sky.JD2AnimalShelterBot.constants.ShelterConstants.SUCCESSFUL_COMPLETION_OF_THE_PROBATION_PERIOD;

/**
 Класс для работы с БД домашних питомцев
 */
@Service
@Slf4j
public class PetService {
    private final PetRepository petRepository;
    private final DogUserRepository dogUserRepository;
    private final CatUserRepository catUserRepository;
    private final ExecuteMessage executeMessage;
    public PetService(PetRepository petRepository, DogUserRepository dogUserRepository, CatUserRepository catUserRepository, ExecuteMessage executeMessage) {
        this.petRepository = petRepository;
        this.dogUserRepository = dogUserRepository;
        this.catUserRepository = catUserRepository;
        this.executeMessage = executeMessage;
    }
    /**
     * Метод для получения домашнего питомца
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
     * Метод для добавления домашнего питомца в БД
     * @param pet домашний питомец
     */
    public Pet createPet(Pet pet) {
        log.info("Was invoked method for update pet");
        return petRepository.save(pet);
    }
    /**
     * Метод для удаления домашнего питомца из БД
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
     * Метод для редактирования домашнего питомца в БД
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
     * @param petId id животного
     * @param userId id попечителя
     * @return pet измененный объект питомца
     */
    public Pet assignPetToCaregiver(Long petId, Long userId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        if(pet != null){
            if(pet.getDogUser() != null || pet.getCatUser() != null){
                throw new SecurityException();
            }
            if(Objects.equals(pet.getTypeOfPet(), "dog")){
                DogUser dogUser = dogUserRepository.findById(userId).orElse(null);
                if (dogUser == null){
                    return null;
                }
                pet.setDogUser(dogUser);
            } else  if(Objects.equals(pet.getTypeOfPet(), "cat")){
                CatUser catUser = catUserRepository.findById(userId).orElse(null);
                if (catUser == null){
                    return null;
                }
                pet.setCatUser(catUser);
            }
            pet.setProbationPeriodUpTo(LocalDate.now().plusDays(30));
            petRepository.save(pet);
        }
       return pet;
    }

    /**
     * Метод для открепления животного от попечителя в базе
     * @param petId id животного
     */
    public Pet detachPetFromCaregiver(Long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        if(pet == null){
            return null;
        }
        pet.setDogUser(null);
        pet.setCatUser(null);
        pet.setProbationPeriodUpTo(null);
        pet.setFixed(false);
        petRepository.save(pet);
        return pet;
    }

    /**
     * Метод для продления испытательного срока и отправки пользователю соответствующего сообщения.
     * @param petId id питомца
     * @param extensionDays количество дней на которые необходимо продлить испытательный срок
     * @return возвращает новую дату окончания испытательного срока.
     */
    public LocalDate extensionOfProbationPeriod(Long petId, Integer extensionDays) {
        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet == null || pet.getProbationPeriodUpTo() == null){
            return null;
        }
        String typeOfPet = pet.getTypeOfPet();
        Long chatId = null;
        if(typeOfPet.equals("dog")){
            var dogUser = pet.getDogUser();
            if(dogUser == null){
                return null;
            } else {
                chatId = dogUser.getChatId();
            }
        }
        if(typeOfPet.equals("cat")){
            var catUser = pet.getCatUser();
            if(catUser == null){
                return null;
            } else {
                chatId = catUser.getChatId();
            }
        }
        if(chatId == null){
            return null;
        }
        pet.setProbationPeriodUpTo(pet.getProbationPeriodUpTo().plusDays(extensionDays));
        petRepository.save(pet);

        String replyText = "Сообщение от волонтера приюта: \n" +
                "Вам продлен испытательный срок на " + extensionDays + " дней до " + pet.getProbationPeriodUpTo() +
                " в связи с нарушениями правил приюта. По всем вопросам Вы можете получить консультацию у волонтера приюта.";
        executeMessage.prepareAndSendMessage(chatId, replyText, null);

        return pet.getProbationPeriodUpTo();
    }

    /**
     * Метод для закрепления животного за попечителем по результатам испытательного срока
     * и отправки пользователю соответствующего сообщения.
     * @param petId id животного
     * @param chatId id попечителя
     * @return возвращает объект питомца с внесенными изменениями.
     */
    public Pet securingAnimalToCaregiver(Long petId, Long chatId) {
        Pet pet = petRepository.findById(petId).orElseThrow(NotFoundException::new);
        CatUser catUser = catUserRepository.findById(chatId).orElse(null);
        DogUser dogUser = dogUserRepository.findById(chatId).orElse(null);
        String typeOfPet = pet.getTypeOfPet();
        if(typeOfPet.equals("dog")){
            if(dogUser == null || !Objects.equals(pet.getDogUser(), dogUser)) {
                throw new BadRequestException();
            }
        }
        if(typeOfPet.equals("cat")){
            if(catUser == null || !Objects.equals(pet.getCatUser(), catUser)) {
                throw new BadRequestException();
            }
        }
        pet.setFixed(true);
        petRepository.save(pet);

        String replyText = "Сообщение от волонтера приюта: \n" + SUCCESSFUL_COMPLETION_OF_THE_PROBATION_PERIOD;
        executeMessage.prepareAndSendMessage(chatId, replyText, null);

        return pet;
    }

    /**
     * Метод отправляет пользователю сообщение о провале испытательного срока и вносит необходимые изменения в БД
     * @param petId id животного
     * @param chatId id пользователя
     */
    public void probationFailed(Long petId, Long chatId) {
        Pet pet = petRepository.findById(petId).orElseThrow(NotFoundException::new);
        if(dogUserRepository.findById(chatId).isEmpty() && catUserRepository.findById(chatId).isEmpty()){
            throw new BadRequestException();
        }
        this.detachPetFromCaregiver(petId);

        String replyText = "Сообщение от волонтера приюта: \n" + PROBATION_PERIOD_FAILED;
        executeMessage.prepareAndSendMessage(chatId, replyText, null);

    }
}
