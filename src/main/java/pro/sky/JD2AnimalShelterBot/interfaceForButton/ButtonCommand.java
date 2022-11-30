package pro.sky.JD2AnimalShelterBot.interfaceForButton;


import org.telegram.telegrambots.meta.api.objects.Update;

public interface ButtonCommand {


    void onButton(Update update);
}
