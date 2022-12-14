package pro.sky.JD2AnimalShelterBot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс - отчеты усыновителей
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TrusteesReports {

    /**
     * id отчета
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    /**
     * ИД пользователя
     */
    private Long chatId;

    /**
     * ИД животного
     */
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    /**
     * дата и время отчета
     */
    private LocalDateTime dateTime;

    /**
     * фото животного
     */
    private String photo;

    /**
     * текст отчета
     */
    @Column(columnDefinition="TEXT")
    private String text;

    /**
     * отчет просмотрен
     */
    private boolean viewed;

}