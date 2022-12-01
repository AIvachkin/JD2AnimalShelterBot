package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.TrusteesReports;

/**
 * Интерфейс работы с БД отчетов
 */
@Repository
public interface TrusteesReportsRepository extends CrudRepository<TrusteesReports, Long> {
}