package pro.sky.JD2AnimalShelterBot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.service.CorrespondenceService;
import pro.sky.JD2AnimalShelterBot.service.PetService;
import pro.sky.JD2AnimalShelterBot.service.UserService;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *  Контроллер для работы с запросами волонтера
 */
@RestController
@RequestMapping("/volontier")
public class VolunteerController {

    private final PetService petService;
    private final UserService userService;
    private final CorrespondenceService correspondenceService;

    public VolunteerController(PetService petService, UserService userService, CorrespondenceService correspondenceService) {
        this.petService = petService;
        this.userService = userService;
        this.correspondenceService = correspondenceService;
    }

    /**
     *Метод для просмотра всех неотвеченных сообщений
     * @return
     */
    @GetMapping("/messages")
    public List<Correspondence> getUnansweredMessages() {
        return correspondenceService.getUnansweredMessages();
    }

    /**
     *Метод для отправки ответа на сообщение
     * @return
     */
    @PostMapping("/messages/{messageId}/answer")
    public ResponseEntity replyToMessages(@PathVariable long messageId, @RequestBody String text) {
        try {
            correspondenceService.replyToMessages(messageId, text);
            return ResponseEntity.ok().build();
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *Метод для отправки сообщения пользователю
     * @return
     */
    @PostMapping("/messages/{chatId}/send")
    public ResponseEntity sendMessage(@PathVariable long chatId, @RequestBody String text) {
        try {
            correspondenceService.sendMessage(chatId, text);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *Метод для получения всей переписки с пользователем
     * @return
     */
    @GetMapping("/messages/{chatId}")
    public ResponseEntity getAllCorrespondenceWithUser(@PathVariable long chatId) {
        try {
            List<Correspondence> correspondences = correspondenceService.getAllCorrespondenceWithUser(chatId);
            return ResponseEntity.ok(correspondences);
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
