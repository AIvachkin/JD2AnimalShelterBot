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
import pro.sky.JD2AnimalShelterBot.model.*;
import pro.sky.JD2AnimalShelterBot.service.CorrespondenceService;
import pro.sky.JD2AnimalShelterBot.service.TrusteesReportsService;
import pro.sky.JD2AnimalShelterBot.service.pet.PetService;
import pro.sky.JD2AnimalShelterBot.service.UserService;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Контроллер для работы с запросами волонтера
 */
@RestController
@RequestMapping("/volontier")
public class VolunteerController {

    private final PetService petService;
    private final UserService userService;
    private final CorrespondenceService correspondenceService;
    private final TrusteesReportsService trusteesReportsService;

    public VolunteerController(PetService petService, UserService userService, CorrespondenceService correspondenceService, TrusteesReportsService trusteesReportsService) {
        this.petService = petService;
        this.userService = userService;
        this.correspondenceService = correspondenceService;
        this.trusteesReportsService = trusteesReportsService;
    }

    @Operation(
            summary = "Просмотр всех неотвеченных сообщений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список неотвеченных сообщений",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Correspondence.class))
                            )

                    )
            }, tags = "Correspondence"
    )
    @GetMapping("/messages")
    public List<Correspondence> getUnansweredMessages() {
        return correspondenceService.getUnansweredMessages();
    }


    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отправляемый ответ пользователю на его сообщение",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)
                    )
            ),
            summary = "Отправка ответа пользователю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ответ отправлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    )
            }, tags = "Correspondence"
    )
    @PostMapping("/messages/{messageId}/answer")
    public ResponseEntity replyToMessages(@Parameter(description = "id сообщения пользователя", required = true, example = "12334455")
                                          @PathVariable long messageId,
                                          @RequestBody String text) {
        try {
            correspondenceService.replyToMessages(messageId, text);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отправляемое пользователю сообщение",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class)
                    )
            ),
            summary = "Отправка сообщения пользователю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сообщение отправлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    )
            }, tags = "Correspondence"
    )
    @PostMapping("/messages/{chatId}/send")
    public ResponseEntity sendMessage(@Parameter(description = "id сообщения пользователя", required = true, example = "12334455")
                                      @PathVariable long chatId, @RequestBody String text) {
        try {
            correspondenceService.sendMessage(chatId, text);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Просмотр всей переписки с пользователем",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получена история переписки с пользователем",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Correspondence.class))
                            )

                    )
            }, tags = "Correspondence"
    )
    @PostMapping("/messages/{chatId}")
    public ResponseEntity<List<Correspondence>> getAllCorrespondenceWithUser(@Parameter(description = "id чата пользователя", required = true, example = "1237864")
                                                       @PathVariable long chatId) {
        try {
            List<Correspondence> correspondences = correspondenceService.getAllCorrespondenceWithUser(chatId);
            return ResponseEntity.ok(correspondences);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Получение всех пользователей приюта собак",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список пользователей приюта собак",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = DogUser.class))
                            )

                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/dogusers")
    public ResponseEntity<List<DogUser>> getAllDogUsers() {
            List<DogUser> allDogUsers = userService.getAllDogUsers();
            return ResponseEntity.ok(allDogUsers);
    }

    @Operation(
            summary = "Получение всех пользователей приюта кошек",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список пользователей приюта кошек",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = CatUser.class))
                            )

                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/catusers")
    public ResponseEntity<List<CatUser>> getAllCatUsers() {
        List<CatUser> allCatUser = userService.getAllCatUsers();
        return ResponseEntity.ok(allCatUser);
    }

    @Operation(
            summary = "Просмотреть все отчеты по животному",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список отчетов по конкретному животному",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TrusteesReports.class))
                            )

                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/petreports")
    public ResponseEntity <List<TrusteesReports>> getAllPetReports(@Parameter(description = "id питомца", required = true,
                                                            example = "3") @RequestParam Long petId) {
        List<TrusteesReports> allPetReports = trusteesReportsService.getAllPetReports(petId);
        return ResponseEntity.ok(allPetReports);
    }

    @Operation(
            summary = "Отправка предупреждения попечителю",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "предупреждение отправлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Если попечитель не найден по id"
                    )
            }, tags = "Pets"
    )
    @GetMapping("/detach")
    public ResponseEntity sendWarning(@Parameter(description = "id питомца", required = true, example = "3")
                                                 @RequestParam Long petId) {
        try {
            trusteesReportsService.sendWarning(petId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
