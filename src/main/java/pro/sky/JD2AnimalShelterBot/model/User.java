package pro.sky.JD2AnimalShelterBot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;


/**
 * Класс задает сущность USER и сохраняет его в БД
 */
@Entity(name = "usersDataTable")
@Getter
@Setter
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



    @OneToOne
    @JsonIgnore
    /**
     * Переменная - питомец, которого приютил пользователь
     */
    private Pet ward;



    public User() {
    }

    public User(long chatId, String firstname, String lastname, String phoneNumber, Pet ward) {
        this.chatId = chatId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.ward = ward;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return chatId == user.chatId && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, firstname, lastname, phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phoneNumber + '\'' +
                '}';
    }
}
