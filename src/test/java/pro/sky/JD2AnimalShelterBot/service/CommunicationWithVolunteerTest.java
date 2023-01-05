package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import pro.sky.JD2AnimalShelterBot.service.user.UserContext;
import pro.sky.JD2AnimalShelterBot.service.user.UserService;
import pro.sky.JD2AnimalShelterBot.service.volunteer.CommunicationWithVolunteer;
import pro.sky.JD2AnimalShelterBot.service.volunteer.CorrespondenceService;

import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommunicationWithVolunteerTest {

    @Mock
    UserService userService;

    @Mock
    UserContext userContext;

    @Mock
    ExecuteMessage executeMessage;

    @Mock
    CorrespondenceService correspondenceService;

    @InjectMocks
    CommunicationWithVolunteer out;

    @Test
    void volunteerButtonHandlerIfNoPhone() {

        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(123454321L);
        message.setChat(chat);
        update.setMessage(message);
        when(userService.getDogUserPhone(123454321L)).thenReturn(null);
        when(userContext.getUserContext(123454321L)).thenReturn(Set.of("dog"));
        out.volunteerButtonHandler(update);
        verify(userService).requestContactDetails(123454321L);
    }

    @Test
    void volunteerButtonHandlerIfYesPhone() {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(123454321L);
        message.setChat(chat);
        update.setMessage(message);
        when(userService.getDogUserPhone(123454321L)).thenReturn("+7999999999");
        when(userContext.getUserContext(123454321L)).thenReturn(Set.of("dog"));
        SendMessage messageOut = new SendMessage();
        messageOut.setChatId(String.valueOf(123454321L));
        messageOut.setText("""

            Напишите, пожалуйста, ниже Ваш вопрос или сообщение волонтеру.
            Волонтер свяжется с Вами в ближайшее время. 👇✍️
            """);
        messageOut.setReplyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());
        out.volunteerButtonHandler(update);
        verify(executeMessage).executeMessage(messageOut);
    }

    @Test
    void volunteerTextHandler() {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(123454321L);
        message.setChat(chat);
        message.setText("Сообщение волонтеру 1");
        update.setMessage(message);
        out.volunteerTextHandler(update);
        verify(userContext).deleteUserContext(123454321L, "messageToVolunteer");
        verify(correspondenceService).sendMessageToVolunteer(123454321L, "Сообщение волонтеру 1");
    }
}