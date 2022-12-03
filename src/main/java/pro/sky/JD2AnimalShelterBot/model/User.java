package pro.sky.JD2AnimalShelterBot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


/**
 * Класс задает сущность USER и сохраняет его в БД
 */
@Entity(name = "user_data_table")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

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
    @OneToMany(mappedBy = "user")
    private List<Pet> pets;



}
