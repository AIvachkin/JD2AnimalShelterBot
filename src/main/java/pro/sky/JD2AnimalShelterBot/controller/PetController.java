package pro.sky.JD2AnimalShelterBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.service.pet.PetService;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Контроллер для работы с сущностями животных
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }


    @Operation(
            summary = "Внесение нового животного в базу",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Внесение нового животного в базу",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ), tags = "Pets"
    )
    @PostMapping
    public ResponseEntity createPet(@RequestBody Pet pet) {
        Pet createdPet = petService.createPet(pet);
        return ResponseEntity.ok(createdPet);
    }


    @Operation(
            summary = "Поиск животного по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденное животное",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если животное не найдено"
                    )
            }, tags = "Pets"
    )
    @GetMapping("{petId}")
    public ResponseEntity getPet(@Parameter(description = "id питомца", required = true, example = "3")
                                 @PathVariable Long petId) {
        Pet pet = petService.getById(petId);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }


    @Operation(
            summary = "Редактирование животного",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемое животное",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ), tags = "Pets"
    )
    @PutMapping()
    public ResponseEntity updatePet(@RequestBody Pet pet) {
        if (pet != null) {
            Pet updatePet = petService.updatePet(pet);
            return ResponseEntity.ok(updatePet);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Удаление животного по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Животное найдено и удалено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если животное не найдено"
                    )
            }, tags = "Pets"
    )
    @DeleteMapping("/{petId}")
    public ResponseEntity deletePet(@Parameter(description = "id питомца", required = true, example = "3")
                                    @PathVariable Long petId) {
        try {
            petService.delete(petId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Получение списка всех животных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список животных сформирован",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если животные не найдены"
                    )
            }, tags = "Pets"
    )
    @GetMapping("/all")
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @Operation(
            summary = "Закрепление животного за попечителем",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Животное закреплено за попечителем",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404 (400)",
                            description = "Если животное или пользователь не найдены (животное уже закреплено)"
                    )
            }, tags = "Pets"
    )
    @GetMapping("/assign")
    public ResponseEntity assignPetToCaregiver(@Parameter(description = "id питомца", required = true, example = "3")
                                               @RequestParam Long petId,
                                               @Parameter(description = "id чата пользователя", required = true, example = "123456789")
                                               @RequestParam Long userId) {
        try {
            petService.assignPetToCaregiver(petId, userId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Открепление животного от попечителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Животное найдено по id и откреплено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если животное не найдено"
                    )
            }, tags = "Pets"
    )
    @GetMapping("/detach")
    public ResponseEntity detachPetFromCaregiver(@Parameter(description = "id питомца", required = true, example = "3")
                                                 @RequestParam Long petId) {
        try {
            petService.detachPetFromCaregiver(petId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
