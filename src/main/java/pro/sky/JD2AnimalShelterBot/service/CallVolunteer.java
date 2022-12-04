package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.JD2AnimalShelterBot.interfaceForButton.ButtonCommand;

@Slf4j
@Service
public class CallVolunteer extends ButtonCommand{
    protected CallVolunteer(StartCommand startCommand) {
        super(startCommand);
    }
    public static void callVolunteer() {

    }
}
