package pro.sky.JD2AnimalShelterBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.JD2AnimalShelterBot.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
}
