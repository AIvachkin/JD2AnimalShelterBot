package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExecuteMessageTest {

    @InjectMocks
    ExecuteMessage executeMessage;

    @Mock
    TelegramBot telegramBot;

    @Test
    void executeMessage() throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setText("text");
        message.setChatId(1L);
        executeMessage.executeMessage(message);
        verify(telegramBot).execute(message);
    }

    @Test
    void prepareAndSendMessage() throws TelegramApiException {
        executeMessage.prepareAndSendMessage(1L, "text", null);
        SendMessage message = new SendMessage();
        message.setText("text");
        message.setChatId(1L);
        message.setParseMode("html");
        verify(telegramBot).execute(message);
    }
}