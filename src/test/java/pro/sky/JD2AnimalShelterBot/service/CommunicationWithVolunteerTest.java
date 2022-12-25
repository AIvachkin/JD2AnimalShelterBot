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

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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

    @InjectMocks
    CommunicationWithVolunteer out;

    @Test
    void volunteerButtonHandler() {

        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();
        chat.setId(123454321L);
        message.setChat(chat);
        update.setMessage(message);

        when(userService.getDogUserPhone(123454321L)).thenReturn(null);
        when(userContext.getUserContext(123454321L)).thenReturn(Set.of("dog"));

        SendMessage messageOut = new SendMessage();
        messageOut.setChatId(String.valueOf(123454321L));
        messageOut.setText("""

            Напишите, пожалуйста, ниже Ваш вопрос или сообщение волонтеру.
            Волонтер свяжется с Вами в ближайшее время. 👇✍️
            """);
        messageOut.setReplyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());

        out.volunteerButtonHandler(update);

        verify(userService).requestContactDetails(123454321L);

//        SendMessage messageOut = new SendMessage();
//        messageOut.setChatId(String.valueOf(123454321L));
//        messageOut.setText("""
//
//            Напишите, пожалуйста, ниже Ваш вопрос или сообщение волонтеру.
//            Волонтер свяжется с Вами в ближайшее время. 👇✍️
//            """);
//        messageOut.setReplyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());
//
//        when(userService.getDogUserPhone(123454321L)).thenReturn("+79999999999");
//        out.volunteerButtonHandler(update);
//
//        verify(executeMessage).executeMessage(messageOut);


//
//        userContext.setUserContext(chatId, "messageToVolunteer");
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(CALL_VOLUNTEER_MESSAGE);
//        message.setReplyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());//Убираем клавиатуру
//        executeMessage.executeMessage(message);
//        log.info("A call volunteer message has been sent to the user " + update.getMessage().getChat().getFirstName() + ", Id: " + chatId);

    }

    @Test
    void volunteerTextHandler() {
    }
}