package pro.sky.JD2AnimalShelterBot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    /**
     * id домашнего питомца в БД
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

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
    private DogUser dogUser;

    /**
     * испытательный срок до
     */
    private LocalDate probationPeriodUpTo;

    /**
     * животное закреплено за попечителем по результатам испытательного срока
     */
    private boolean fixed;

}