package pro.sky.JD2AnimalShelterBot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;


/**
 * Класс задает сущность BADUSER и сохраняет его в БД
 */
@Entity(name = "bad_user_data_table")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BadUser {

    /**
     * Переменная - идентификатор должника по отчетам. Primary key
     */
    @Id
    private long Id;

    /**
     * Должник приюта для собак
     */
    @OneToOne(mappedBy="badUser")
    @Nullable
    @JoinColumn(name = "dog_user_id")
    private DogUser dogUser;

    /**
     * Должник приюта для кошек
     */
    @OneToOne(mappedBy="badUser")
    @Nullable
    @JoinColumn(name = "cat_user_id")
    private CatUser catUser;

}
