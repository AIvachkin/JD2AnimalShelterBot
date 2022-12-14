package pro.sky.JD2AnimalShelterBot.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


/**
 * Класс задает сущность CATUSER и сохраняет его в БД
 */
@Entity(name = "cat_user_data_table")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CatUser {

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

}
