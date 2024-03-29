package pro.sky.JD2AnimalShelterBot.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * Класс - отчеты усыновителей
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrusteesReports {

    /**
     * id отчета
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    /**
     * id пользователя
     */
    private Long chatId;

    /**
     * id животного
     */
    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    /**
     * дата и время отчета
     */
    private LocalDateTime dateTime;

    /**
     * путь к фото животного на сервере
     */
    private String photoFilePath;

    /**
     * размер файла - фото животного
     */
    private long photoFileSize;

    /**
     * превью фото животного, хранящегося в БД
     */
    private byte [] preview;


    /**
     * текст отчета
     */
    @Column(columnDefinition="TEXT")
    private String text;

    /**
     * вид питомца
     */
    @Column(name = "type_of_pet")
    private String typeOfPet;

    /**
     * отчет просмотрен
     */
    private boolean viewed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrusteesReports that = (TrusteesReports) o;
        return photoFileSize == that.photoFileSize && Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(pet, that.pet) && Objects.equals(dateTime, that.dateTime) && Objects.equals(photoFilePath, that.photoFilePath)  && Arrays.equals(preview, that.preview) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, chatId, pet, dateTime, photoFilePath, photoFileSize, text);
        result = 31 * result + Arrays.hashCode(preview);
        return result;
    }
}