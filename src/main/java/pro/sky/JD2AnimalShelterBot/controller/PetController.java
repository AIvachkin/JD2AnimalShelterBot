package pro.sky.JD2AnimalShelterBot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.model.User;
import pro.sky.JD2AnimalShelterBot.service.PetService;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 *  Контроллер для работы с сущностями животных
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     *Метод для регистрации нового животного в базе
     * @param pet
     * @return
     */
    @PostMapping
    public ResponseEntity createPet(@RequestBody Pet pet) {
        Pet createdPet = petService.createPet(pet);
        return ResponseEntity.ok(createdPet);
    }

    /**
     * Метод получения животного по ИД
     * @param petId
     * @return
     */
    @GetMapping("{petId}")
    public ResponseEntity getPet(@PathVariable Long petId) {
        Pet pet = petService.getById(petId);
        if(pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    /**
     * Метод по редактированию сущности животного
     * @param pet
     * @return
     */
    @PutMapping()
    public ResponseEntity updatePet(@RequestBody Pet pet) {
        if (pet != null) {
            Pet updatePet = petService.updatePet(pet);
            return ResponseEntity.ok(updatePet);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Метод по удалению животного из базы
     * @param petId
     * @return
     */
    @DeleteMapping("/{petId}")
    public ResponseEntity deletePet(@PathVariable Long petId) {
        try {
            petService.delete(petId);
            return ResponseEntity.ok().build();
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Метод для получения списка всех животных
     * @return
     */
    @GetMapping("/all")
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    /**
     * Метод для закрепления животного за попечителем
     * @param petId идетификатор животного
     * @param chatId идентификатор попечителя
     * @return
     */
    @GetMapping("/assign")
    public ResponseEntity assignPetToCaregiver(@RequestParam Long petId, @RequestParam Long chatId) {
        petService.assignPetToCaregiver(petId, chatId);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод для открепления животного от попечителя
     * @param petId идетификатор животного
     * @return
     */
    @GetMapping("/detach")
    public ResponseEntity detachPetFromCaregiver(@RequestParam Long petId) {
        petService.detachPetFromCaregiver(petId);
        return ResponseEntity.ok().build();
    }

}
