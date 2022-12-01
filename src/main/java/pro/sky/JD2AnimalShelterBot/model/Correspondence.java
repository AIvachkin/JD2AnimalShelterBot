package pro.sky.JD2AnimalShelterBot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class Correspondence {

    /**
     * id сообщения
     */
    @Id
    @GeneratedValue
    private Long messageId;

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

}