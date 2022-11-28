package pro.sky.JD2AnimalShelterBot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * Класс - сущность домашнего питомца
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Pet {

    /**
     * id домашнего питомца в БД
     */
    @Id
    @GeneratedValue
    private Long petId;

    /**
     * имя домашнего питомца
     */
    private String name;

    /**
     * возраст домашнего питомца
     */
    private Integer age;

    /**
     * хозяин домашнего питомца
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * испытательный срок до
     */
    private LocalDate probationPeriodUpTo;

    /**
     * животное закреплено за попечителем по результатам испытательного срока
     */
    private boolean fixed;

}