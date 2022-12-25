package pro.sky.JD2AnimalShelterBot.model;


import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;


/**
 * Класс задает сущность USER и сохраняет его в БД
 */
@Entity(name = "dog_user_data_table")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DogUser {

    /**
     * Переменная - идентификатор чата пользователя. Primary key
     */
    @Id
    private long chatId;

    /**
     * Переменная - имя пользователя
     */
    private String firstname;

    /**
     * Переменная - фамилия пользователя
     */
    private String lastname;

    /**
     * Переменная - номер телефона пользователя
     */
    private String phoneNumber;

    /**
     * Переменная - питомец, которого приютил пользователь
     */
    @OneToMany(mappedBy = "dogUser")
    private List<Pet> pets;

    /**
     * Переменная - должник по отчетам
     */
    @Nullable
    @OneToOne(cascade=CascadeType.ALL)
    private BadUser badUser;





}
