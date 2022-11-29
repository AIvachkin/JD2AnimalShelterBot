package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;

/**
 * Интерфейс работы с БД переписки
 */
@Repository
public interface CorrespondenceRepository extends CrudRepository<Correspondence, Long> {
}