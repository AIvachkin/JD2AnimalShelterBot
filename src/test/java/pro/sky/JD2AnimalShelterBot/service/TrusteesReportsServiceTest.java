package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.JD2AnimalShelterBot.handlers.UpdateHandler;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.model.TrusteesReports;
import pro.sky.JD2AnimalShelterBot.repository.CatUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.TrusteesReportsRepository;
import pro.sky.JD2AnimalShelterBot.service.user.UserContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TrusteesReportsServiceTest {

    @InjectMocks
    TrusteesReportsService out;

    @Mock
    TrusteesReportsRepository trusteesReportsRepository;

    @Mock
    DogUserRepository dogUserRepository;

    @Mock
    CatUserRepository catUserRepository;

    @Mock
    private ExecuteMessage executeMessage;

    @Mock
    TelegramBot telegramBot;

    @Mock
    private UserContext userContext;

    @Mock
    UpdateHandler updateHandler;

    @Mock
    PetRepository petRepository;

    private final Pet pet1 = new Pet(1L, "Pet1", 1,
            null, null, LocalDate.of(2022, 12, 26), false, "dog");
    private final Pet pet2 = new Pet(2L, "Pet2", 2,
            null, null, LocalDate.of(2022, 12, 25), false, "dog");


    private final TrusteesReports report1 = new TrusteesReports(1L, 6666L, pet1, LocalDateTime.MIN,
            "path1", 100L, new byte[]{1, 2, 3}, "report1", "dog", false);

    private final TrusteesReports report2 = new TrusteesReports(2L, 6667L, pet2, LocalDateTime.MIN,
            "path2", 100L, new byte[]{1, 2, 3}, "report2", "dog", false);

    private final List<TrusteesReports> allReports = List.of(report1, report2);
    private final List<TrusteesReports> reportForPet1 = List.of(report1);

    @BeforeEach
    public void initOut() {
        out = new TrusteesReportsService(trusteesReportsRepository, petRepository, executeMessage, userContext,
                dogUserRepository, catUserRepository, telegramBot, updateHandler);
    }

    @Test
    void getAllPetReportsTest() {

        when(trusteesReportsRepository.findAllByPet(1L)).thenReturn(reportForPet1);
        List<TrusteesReports> actual = out.getAllPetReports(1L);
        assertEquals(reportForPet1, actual);

    }

    @Test
    void getAllUnwatchedReports() {
    }

    @Test
    void getUnwatchedReportAndMakeRead() {
    }

    @Test
    void detachPetFromCaregiver() {
    }

    @Test
    void sendWarning() {
    }

    @Test
    void trusteesButtonHandler() {
    }

    @Test
    void createMenuForSendReport() {
    }

    @Test
    void uploadReport() {
    }
}