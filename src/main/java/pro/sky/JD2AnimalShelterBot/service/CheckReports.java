package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.service.user.BadUserService;
import pro.sky.JD2AnimalShelterBot.service.user.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Класс - проверка своевременности предоставления отчетов
 */
@Service
@Slf4j
@EnableScheduling
@Transactional
public class CheckReports {
    @Value("${START_CHECK_REPORT_DAY_VALUE}")
    private static int START_CHECK;
    @Value("${CHECK_REPORT_DAY_VALUE}")
    private static int CHECK;
    private final UserService userService;
    private final TrusteesReportsService trusteesReportsService;
    private  final BadUserService badUserService;
    public CheckReports(UserService userService,
                        TrusteesReportsService trusteesReportsService,
                        BadUserService badUserService) {
        this.userService = userService;
        this.trusteesReportsService = trusteesReportsService;
        this.badUserService = badUserService;
    }

    /**
     * Метод, запрашивающий данные по отчетам из БД один раз в день и определяющий необходимость внесения
     * пользователя в список должников
     */
    @Scheduled(cron = "0 0 21 * * *")
    public void check() {
        log.info("Was invoked method for check badUsers");
        List<DogUser> dogUserList = userService.getAllDogUsers();
        List<CatUser> catUserList = userService.getAllCatUsers();
        dogUserList.stream().
                map(DogUser::getPets).
                filter(pets -> !pets.isEmpty()).
                forEach(pl -> pl.
                        forEach(p -> {
                                    if ((trusteesReportsService.getAllPetReports(p.getId()).isEmpty() &
                                            p.getProbationPeriodUpTo().isBefore(LocalDate.now().plusDays(START_CHECK))
                                        ) ||
                                            trusteesReportsService.getAllPetReports(p.getId()).stream().noneMatch(t -> t.getDateTime().truncatedTo(DAYS).
                                                    isBefore(LocalDateTime.now().truncatedTo(DAYS).minusDays(CHECK)))) {
                                        badUserService.createBadUser(p.getDogUser());
                                    }
                                }
                        )
                );
        catUserList.stream().
                map(CatUser::getPets).
                filter(pets -> !pets.isEmpty()).
                forEach(pl -> pl.
                        forEach(p -> {
                                    if ((trusteesReportsService.getAllPetReports(p.getId()).isEmpty() &
                                            p.getProbationPeriodUpTo().isBefore(LocalDate.now().plusDays(START_CHECK))
                                    ) ||
                                            trusteesReportsService.getAllPetReports(p.getId()).stream().noneMatch(t -> t.getDateTime().truncatedTo(DAYS).
                                                    isBefore(LocalDateTime.now().truncatedTo(DAYS).minusDays(CHECK)))) {
                                        badUserService.createBadUser(p.getCatUser());
                                    }
                                }
                        )
                );
    }
}
