package pro.sky.JD2AnimalShelterBot.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс - переписка с пользователями
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Correspondence {

    /**
     * id сообщения
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    /**
     * ИД пользователя
     */
    private Long chatId;

    /**
     * дата и время сообщения
     */
    private LocalDateTime dateTime;

    /**
     * текст сообщения
     */
    @Column(columnDefinition="TEXT")
    private String text;

    /**
     * сообщение отвечено
     */
    private boolean answered;

    /**
     * пользователь или волонтер
     */
    private String whoSentIt;

    /**
     * вид питомца
     */
    @Column(name = "type_of_pet")
    private String typeOfPet;

}