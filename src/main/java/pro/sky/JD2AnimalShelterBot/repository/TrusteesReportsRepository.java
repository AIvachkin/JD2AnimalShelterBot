package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.TrusteesReports;

import java.util.List;

/**
 * Интерфейс работы с БД отчетов
 */
@Repository
public interface TrusteesReportsRepository extends CrudRepository<TrusteesReports, Long> {
    @Query(value = "SELECT * FROM trustees_reports WHERE pet_id = ?1", nativeQuery = true)
    List<TrusteesReports> findAllByPet(Long petId);
}