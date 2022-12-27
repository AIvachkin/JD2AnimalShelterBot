package pro.sky.JD2AnimalShelterBot.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.BadUser;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.repository.BadUserRepository;
import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 Класс для работы с БД должников отчетов
 */
@Service
@Slf4j
public class BadUserService {
    private final BadUserRepository badUserRepository;

    public BadUserService(BadUserRepository badUserRepository) {
        this.badUserRepository = badUserRepository;
    }
    /**
     * Метод получения должника
     * @param badUserId  id должника в БД
     */
    public BadUser getById(Long badUserId) {
        log.info("Was invoked method for get badUser by id");
        BadUser badUser = badUserRepository.findById(badUserId).orElse(null);
        if (badUser == null) {
            log.warn("Getting badUser = null");
            log.error("There is not badUser with id = {}",badUserId);
        }
        log.debug("Getting badUser = {}",badUser);
        return badUser;
    }
    /**
     * Метод добавления должника в БД
     * @param dogUser должник
     */
    public BadUser createBadUser(DogUser dogUser) {
        log.info("Was invoked method for create dog badUser");
        BadUser badUser = new BadUser();
        badUser.setDogUser(dogUser);
        return badUserRepository.save(badUser);
    }
    /**
     * Метод добавления должника в БД
     * @param catUser должник
     */
    public BadUser createBadUser(CatUser catUser) {
        log.info("Was invoked method for create cat badUser");
        BadUser badUser = new BadUser();
        badUser.setCatUser(catUser);
        return badUserRepository.save(badUser);
    }
    /**
     * Метод удаления должника из БД
     *
     * @param badUserId id должника в БД
     */
    public void delete(Long badUserId) throws NotFoundException {
        log.info("Was invoked method for delete badUser by id");
        if (getById(badUserId)!= null) {
            badUserRepository.deleteById(badUserId);
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Метод редактирования должника в БД
     * @param badUser должник
     */
    public BadUser updateBadUser(BadUser badUser) {
        return badUserRepository.save(badUser);
    }

    /**
     * Метод для получения всех должника из базы
     * @return - возвращает список всех должников
     */
    public List<BadUser> getAllBadUsers() {
        return (List<BadUser>) badUserRepository.findAll();
    }
}
