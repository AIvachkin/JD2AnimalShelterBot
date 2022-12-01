package pro.sky.JD2AnimalShelterBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.JD2AnimalShelterBot.interfaceForButton.ButtonCommand;

@Service
public class CallVolunteer implements ButtonCommand {

    @Override
    public void onButton(Update update) {

    }
}
