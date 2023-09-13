package pro.sky.JD2AnimalShelterBot.service.volunteer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.sky.JD2AnimalShelterBot.service.ExecuteMessage;

@Slf4j
@Service
public class CallVolunteer {
    private final ExecuteMessage executeMessage;

    public CallVolunteer(ExecuteMessage executeMessage) {
        this.executeMessage = executeMessage;
    }


}
