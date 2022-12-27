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
import pro.sky.JD2AnimalShelterBot.service.user.BadUserService;
import pro.sky.JD2AnimalShelterBot.service.volunteer.CorrespondenceService;
import pro.sky.JD2AnimalShelterBot.service.TrusteesReportsService;
import pro.sky.JD2AnimalShelterBot.service.pet.PetService;
import pro.sky.JD2AnimalShelterBot.service.user.UserService;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    private final BadUserService badUserService;

    public VolunteerController(PetService petService, UserService userService, CorrespondenceService correspondenceService, TrusteesReportsService trusteesReportsService, BadUserService badUserService) {
        this.petService = petService;
        this.userService = userService;
        this.correspondenceService = correspondenceService;
        this.trusteesReportsService = trusteesReportsService;
        this.badUserService = badUserService;
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
    public ResponseEntity<List<TrusteesReports>> getAllPetReports(@Parameter(description = "id питомца", required = true,
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
            }, tags = "Volunteer"
    )
    @GetMapping("/sendworning")
    public ResponseEntity sendWarning(@Parameter(description = "id питомца", required = true, example = "3")
                                      @RequestParam Long petId) {
        try {
            trusteesReportsService.sendWarning(petId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Продление испытательного срока на 14 или 30 дней",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Испытательный срок продлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404, 400",
                            description = "Не найдено по ИД животное (неправильно указано количество дней для продления)"
                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/extension")
    public ResponseEntity extensionOfProbationPeriod(@Parameter(description = "id питомца", required = true, example = "3")
                                                     @RequestParam Long petId,
                                                     @Parameter(description = "количество дней для продления", required = true, example = "14")
                                                     @RequestParam Integer extensionDays) {
        if (extensionDays != 14 && extensionDays != 30) {
            return ResponseEntity.badRequest().build();
        }
        try {
            var probationPeriod = petService.extensionOfProbationPeriod(petId, extensionDays);
            return ResponseEntity.ok(probationPeriod);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Закрепление животного за попечителем по результатам испытательного срока.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Животное закреплено за попечителем.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404 (400)",
                            description = "Не найдено по ИД животное или попечитель (не найдена информация о прохождении попечителем испытательрного срока)."
                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/fixing")
    public ResponseEntity<Pet> securingAnimalToCaregiver(@Parameter(description = "id питомца", required = true, example = "3")
                                                         @RequestParam Long petId,
                                                         @Parameter(description = "id попечителя", required = true, example = "1372481155")
                                                         @RequestParam Long chatId) {
        try {
            var pet = petService.securingAnimalToCaregiver(petId, chatId);
            return ResponseEntity.ok(pet);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Отправка пользователю сообщение о непрохождении испытательного срока и внесение соответствующих изменений в БД",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сообщение отправлено. Изменения в БД внесены.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404 (400)",
                            description = "Не найдено по ИД животное или попечитель (животное не было закреплено за указанным пользователем на испытательный срок)."
                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/probationfailed")
    public ResponseEntity probationFailed(@Parameter(description = "id питомца", required = true, example = "3")
                                          @RequestParam Long petId,
                                          @Parameter(description = "id попечителя", required = true, example = "1372481155")
                                          @RequestParam Long chatId) {
        try {
            petService.probationFailed(petId, chatId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(
            summary = "Просмотреть непрочитанные отчеты",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список непрочитанных отчетов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TrusteesReports.class))
                            )

                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/unwatchedreports")
    public ResponseEntity<List<TrusteesReports>> getAllUnwatchedReports() {
        List<TrusteesReports> allUnwatchedReports = trusteesReportsService.getAllUnwatchedReports(false);
        return ResponseEntity.ok(allUnwatchedReports);
    }


    @Operation(
            summary = "Пометить отчет как прочитанный",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет найден и помечен как прочитанный",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = TrusteesReports.class))
                            )

                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/makereadreport")
    public ResponseEntity<Optional<TrusteesReports>> makeReadReport(Long id) {
        Optional<TrusteesReports> readReport = trusteesReportsService.getUnwatchedReportAndMakeRead(id);
        return ResponseEntity.ok(readReport);
    }

    @Operation(
            summary = "Удаление пользователя из списка должников",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Должник найден и удален из списка нарушителей",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = BadUser.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Должник по id не найден"

                    )
            }, tags = "Volunteer"
    )
    @DeleteMapping("/delete/{badUserId}")
    public ResponseEntity deleteBadUser(@Parameter(description = "id штрафника", required = true, example = "3")
                                        @PathVariable Long badUserId) {
        try {
            badUserService.delete(badUserId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Просмотреть список имеющих долги по отчетам",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список получен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = BadUser.class))
                            )

                    )
            }, tags = "Volunteer"
    )
    @GetMapping("/debtorsonreports")
    public ResponseEntity<List<BadUser>> debtorsOnReports() {
        List<BadUser> allBadUser = badUserService.getAllBadUsers();
        return ResponseEntity.ok(allBadUser);
    }

    @Operation(
            summary = "Добавление должника в таблицу штрафников",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Должник в список добавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = BadUser.class))
                            )

                    )
            }, tags = "Volunteer"
    )
    @PostMapping("/adddebtor")
    public ResponseEntity addDebtor(@RequestBody DogUser dogUser) {
        badUserService.createBadUser(dogUser);
        return ResponseEntity.ok().build();

    }

}
