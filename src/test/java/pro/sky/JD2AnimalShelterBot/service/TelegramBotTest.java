package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.JD2AnimalShelterBot.configuration.BotConfiguration;
import pro.sky.JD2AnimalShelterBot.handlers.UpdateHandler;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramBotTest {

    @Mock
    BotConfiguration configuration;

    @Mock
    UpdateHandler updateHandler;

    @InjectMocks
    TelegramBot telegramBot;

    @Test
    void getBotUsername() {
        when(configuration.getBotName()).thenReturn("superBot");
        assertEquals("superBot", telegramBot.getBotUsername());
    }

    @Test
    void getBotToken() {
        when(configuration.getToken()).thenReturn("ksdjwfjwwjfwj");
        assertEquals("ksdjwfjwwjfwj", telegramBot.getBotToken());
    }

    @Test
    void onUpdateReceived() throws TelegramApiException, IOException {
        Update update = new Update();
        Message message = new Message();
        update.setMessage(message);
        update.setUpdateId(1);
        telegramBot.onUpdateReceived(update);
        verify(updateHandler).handle(update);
    }
}