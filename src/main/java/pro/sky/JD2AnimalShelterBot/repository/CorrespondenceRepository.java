package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pro.sky.JD2AnimalShelterBot.model.Correspondence;

public interface CorrespondenceRepository extends CrudRepository<Correspondence, Long> {
}