package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;

import java.util.List;

/**
 * Интерфейс работы с БД переписки
 */
@Repository
public interface CorrespondenceRepository extends CrudRepository<Correspondence, Long> {
    @Query(value = "SELECT * from correspondence where answered = false ORDER BY date_time", nativeQuery = true)
    List<Correspondence> getAllUnansweredMessages();

    @Query(value = "SELECT * from correspondence where message_id = ?1", nativeQuery = true)
    Correspondence getCorrespondenceByMessageId(long messageId);

    @Query(value = "SELECT * from correspondence where chat_id = ?1", nativeQuery = true)
    List<Correspondence> getAllCorrespondenceWithUser(long chatId);
}