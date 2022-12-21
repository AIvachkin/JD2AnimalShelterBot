package pro.sky.JD2AnimalShelterBot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

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
     * Пользователь приюта для собак
     */
    @JsonIgnore
    @ManyToOne
    @Nullable
    @JoinColumn(name = "dog_user_id")
    private DogUser dogUser;

    /**
     * Пользователь приюта для кошек
     */
    @JsonIgnore
    @ManyToOne
    @Nullable
    @JoinColumn(name = "cat_user_id")
    private CatUser catUser;

    /**
     * испытательный срок до
     */
    private LocalDate probationPeriodUpTo = null;

    /**
     * животное закреплено за попечителем по результатам испытательного срока
     */
    private boolean fixed = false;

    /**
     * вид питомца
     */
    @Column(name = "type_of_pet")
    private String typeOfPet;

}