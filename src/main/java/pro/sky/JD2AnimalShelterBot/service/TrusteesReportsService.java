package pro.sky.JD2AnimalShelterBot.service;

import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.TrusteesReports;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.TrusteesReportsRepository;

import java.util.List;

@Service
public class TrusteesReportsService {

    private final TrusteesReportsRepository trusteesReportsRepository;
    private final PetRepository petRepository;

    public TrusteesReportsService(TrusteesReportsRepository trusteesReportsRepository, PetRepository petRepository) {
        this.trusteesReportsRepository = trusteesReportsRepository;
        this.petRepository = petRepository;
    }

    /**
     * Метод возвращает список всех отчетов по конкретному животному
     * @param petId ИД животного
     * @return List of TrusteesReports
     */
    public List<TrusteesReports> getAllPetReports(Long petId) {
        return trusteesReportsRepository.findAllByPet(petId);
    }

    public void sendWarning(Long petId) {

    }
}
