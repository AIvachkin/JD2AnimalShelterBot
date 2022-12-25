package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Slf4j
@EnableScheduling
public class CheckReports {
    private final UserService userService;
    private final TrusteesReportsService trusteesReportsService;

    public CheckReports(UserService userService, TrusteesReportsService trusteesReportsService) {
        this.userService = userService;
        this.trusteesReportsService = trusteesReportsService;
    }

    @Scheduled(cron = "0 0 21 * * *")
    public void check() {
        List<DogUser> dogUserList = userService.getAllDogUsers();
        List<CatUser> catUserList = userService.getAllCatUsers();
        dogUserList.stream().
                filter(d -> d.getPets() != null).
                map(d -> d.getPets()).
                forEach(pl -> pl.stream().
                        forEach(p -> {
                                    if ((trusteesReportsService.getAllPetReports(p.getId()) == null &
                                            p.getProbationPeriodUpTo().isBefore(LocalDate.now().plusDays(28))
                                        ) ||
                                            trusteesReportsService.getAllPetReports(p.getId()).stream().
                                                    filter(t -> t.getDateTime().truncatedTo(DAYS).
                                                            isBefore(LocalDateTime.now().truncatedTo(DAYS).minusDays(2))).
                                                    collect(Collectors.toList()).isEmpty()) {
                                        badUserService.createBadUser(p.getDogUser());
                                    }
                                }
                        )
                );
        catUserList.stream().
                filter(c -> c.getPets() != null).
                map(c -> c.getPets()).
                forEach(pl -> pl.stream().
                        forEach(p -> {
                                    if ((trusteesReportsService.getAllPetReports(p.getId()) == null &
                                            p.getProbationPeriodUpTo().isBefore(LocalDate.now().plusDays(28))
                                    ) ||
                                            trusteesReportsService.getAllPetReports(p.getId()).stream().
                                                    filter(t -> t.getDateTime().truncatedTo(DAYS).
                                                            isBefore(LocalDateTime.now().truncatedTo(DAYS).minusDays(2))).
                                                    collect(Collectors.toList()).isEmpty()) {
                                        badUserService.createBadUser(p.getCatUser());
                                    }
                                }
                        )
                );
    }
}
