package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.TrusteesReports;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс работы с БД отчетов
 */
@Repository
public interface TrusteesReportsRepository extends CrudRepository<TrusteesReports, Long> {
    @Query(value = "SELECT * FROM trustees_reports WHERE pet_id = ?1", nativeQuery = true)
    List<TrusteesReports> findAllByPet(Long petId);

    @Query(value = "SELECT * FROM trustees_reports WHERE viewed = false" , nativeQuery = true)
    List<TrusteesReports> findAllByViewed(Boolean viewed);

    @Query(value = "SELECT * FROM trustees_reports WHERE message_id = ?1", nativeQuery = true)
    Optional<TrusteesReports> findById(Long messageId);

}