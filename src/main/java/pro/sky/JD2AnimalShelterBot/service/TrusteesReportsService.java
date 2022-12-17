package pro.sky.JD2AnimalShelterBot.service;

import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.model.TrusteesReports;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.TrusteesReportsRepository;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class TrusteesReportsService {

    private static final String WORNING_TEXT = """
            Предупреждение:
            Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. 
            Пожалуйста, подойди ответственнее к этому занятию. 
            В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного.
            """;
    private final TrusteesReportsRepository trusteesReportsRepository;
    private final PetRepository petRepository;
    private final ExecuteMessage executeMessage;

    public TrusteesReportsService(TrusteesReportsRepository trusteesReportsRepository, PetRepository petRepository, ExecuteMessage executeMessage) {
        this.trusteesReportsRepository = trusteesReportsRepository;
        this.petRepository = petRepository;
        this.executeMessage = executeMessage;
    }

    /**
     * Метод возвращает список всех отчетов по конкретному животному
     * @param petId ИД животного
     * @return List of TrusteesReports
     */
    public List<TrusteesReports> getAllPetReports(Long petId) {
        return trusteesReportsRepository.findAllByPet(petId);
    }

    /**
     * Метод отправляет пользователю предупреждение
     * @param petId ИД животного
     */
    public void sendWarning(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(NotFoundException::new);
        Long userId = null;
        if(pet.getTypeOfPet().equals("dog")){
            userId = pet.getDogUser().getChatId();
        } else if(pet.getTypeOfPet().equals("cat")){
            userId = pet.getCatUser().getChatId();
        }
        if(userId != null){
            executeMessage.prepareAndSendMessage(userId, WORNING_TEXT, null);
        }
    }
}
