package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import pro.sky.JD2AnimalShelterBot.handlers.UpdateHandler;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.model.TrusteesReports;
import pro.sky.JD2AnimalShelterBot.repository.CatUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.TrusteesReportsRepository;
import pro.sky.JD2AnimalShelterBot.service.user.UserContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pro.sky.JD2AnimalShelterBot.constants.ShelterConstants.*;


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


    private static final String WARNING_TEXT = """
            Предупреждение:
            Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо.
            Пожалуйста, подойди ответственнее к этому занятию.
            В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного.
            """;


    private final Pet pet1 = new Pet(1L, "Pet1", 1,
            null, null, LocalDate.of(2022, 12, 26), false, "dog");
    private final Pet pet2 = new Pet(2L, "Pet2", 2,
            null, null, LocalDate.of(2022, 12, 25), false, "dog");

    private final DogUser dogUserTest = new DogUser(6666L, "Maksim", "Petrov",
            "5555", null, null);

    private final Pet pet3 = new Pet(3L, "Pet3", 3,
            dogUserTest, null, LocalDate.of(2022, 12, 24), true, "dog");


    private final Pet petNull = new Pet(2L, "Pet2", 2,
            null, null, null, false, "dog");


    private final TrusteesReports report1 = new TrusteesReports(1L, 6666L, pet1, LocalDateTime.MIN,
            "path1", 100L, new byte[]{1, 2, 3}, "report1", "dog", true);

    private final TrusteesReports report2 = new TrusteesReports(2L, 6667L, pet2, LocalDateTime.MIN,
            "path2", 100L, new byte[]{1, 2, 3}, "report2", "dog", false);

    private final TrusteesReports report3 = new TrusteesReports(2L, 6667L, pet2, LocalDateTime.MIN,
            "path2", 100L, new byte[]{1, 2, 3}, "report2", "dog", true);

    private final List<TrusteesReports> reportForPet1 = List.of(report1);

    private final List<TrusteesReports> unwatchedReport = List.of(report2);

    private ReplyKeyboardMarkup keyboardMarkupTest;

    @BeforeEach
    public void initOut() {
        out = new TrusteesReportsService(trusteesReportsRepository, petRepository, executeMessage, userContext,
                dogUserRepository, catUserRepository, telegramBot, updateHandler);


        keyboardMarkupTest = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(SEND_FORM);
        keyboardRows.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(EXIT_THE_REPORT_FORM);
        keyboardRows.add(row2);

        keyboardMarkupTest.setResizeKeyboard(true);
        keyboardMarkupTest.setKeyboard(keyboardRows);

    }



    @Test
    void getAllPetReportsTest() {

        when(trusteesReportsRepository.findAllByPet(1L)).thenReturn(reportForPet1);
        List<TrusteesReports> actual = out.getAllPetReports(1L);
        assertEquals(reportForPet1, actual);

    }

    @Test
    void getAllUnwatchedReportsTest() {

        when(trusteesReportsRepository.findAllByViewed(false)).thenReturn(unwatchedReport);
        List<TrusteesReports> actual = out.getAllUnwatchedReports(false);
        assertEquals(unwatchedReport, actual);
    }

    @Test
    void getUnwatchedReportAndMakeReadTest() {
        when(trusteesReportsRepository.findById(2L)).thenReturn(Optional.of(report2));
        Optional<TrusteesReports> actual = out.getUnwatchedReportAndMakeRead(2L);
        Optional<TrusteesReports> expected = Optional.of(report3);
        assertEquals(expected, actual);
    }

    @Test
    void detachPetFromCaregiverTest() {
        when(petRepository.findById(2L)).thenReturn(Optional.of(petNull));
        out.detachPetFromCaregiver(2L);
        verify(petRepository).save(petNull);
    }

    @Test
    void sendWarningTest() {
        when(petRepository.findById(3L)).thenReturn(Optional.of(pet3));
        out.sendWarning(3L);
        verify(executeMessage).prepareAndSendMessage(6666L, WARNING_TEXT, null);

    }

    @Test
    void trusteesButtonHandler() {

        Update updateTest = new Update();
        Chat chat = new Chat();
        Message message = new Message();
        chat.setFirstName("Maksim");
        chat.setLastName("Petrov");
        chat.setId(6666L);
        message.setChat(chat);
        updateTest.setMessage(message);


        when(userContext.getUserContext(anyLong())).thenReturn(Collections.singleton("dog"));


        SendMessage messageTest = new SendMessage();
        messageTest.setChatId(String.valueOf(6666L));
        messageTest.setText(TRUSTEES_REPORT_INFO);
        messageTest.setReplyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());//Убираем клавиатуру

        messageTest.setReplyMarkup(keyboardMarkupTest);

        out.trusteesButtonHandler(updateTest);
        verify(executeMessage).executeMessage(messageTest);
    }

    @Test
    void createMenuForSendReport() {

        assertEquals(keyboardMarkupTest, out.createMenuForSendReport());

    }


}