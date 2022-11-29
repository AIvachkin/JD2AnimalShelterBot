package pro.sky.JD2AnimalShelterBot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;
import pro.sky.JD2AnimalShelterBot.service.CorrespondenceService;
import pro.sky.JD2AnimalShelterBot.service.PetService;
import pro.sky.JD2AnimalShelterBot.service.UserService;

import javax.ws.rs.NotFoundException;
import java.util.List;

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
    //public ResponseEntity replyToMessages(@RequestBody int messageId, @RequestBody String text) {
    public ResponseEntity replyToMessages(@PathVariable long messageId, @RequestBody String text) {
        try {
            correspondenceService.replyToMessages(messageId, text);
            return ResponseEntity.ok().build();
        }
        catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

}
