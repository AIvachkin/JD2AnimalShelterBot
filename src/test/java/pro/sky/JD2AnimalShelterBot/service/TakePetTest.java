package pro.sky.JD2AnimalShelterBot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static pro.sky.JD2AnimalShelterBot.service.TakePet.*;

@ExtendWith(MockitoExtension.class)
class TakePetTest {

    SendMessage sendMessageTest = new SendMessage();

    @Mock
    StartCommand startCommand;


    @InjectMocks
    TakePet takePet;

    @BeforeEach
    void initParams() {
        sendMessageTest.setChatId(6666L);
    }

    public static Stream<Arguments> paramsForTest() {
        return Stream.of(
                Arguments.of("/dating_rules", DATING_RULES),
                Arguments.of("/documents", DOCUMENTS),
                Arguments.of("/shipping", SHIPPING),
                Arguments.of("/recommendation_for_puppy", RECOMM_FOR_PUPPY),
                Arguments.of("/recommendation_for_dog", RECOMM_FOR_DOG)
        );
    }

//    @ParameterizedTest
//    @MethodSource("paramsForTest")
//    public void commandProcessingTest(String textForMessageTest, String textForSendMessage) {
//
//        sendMessageTest.setText(textForSendMessage);
//
//        Message messageTest = new Message();
//        messageTest.setText(textForMessageTest);
//        Update updateTest = new Update();
//        updateTest.setMessage(messageTest);
//
//        takePet.commandProcessing(updateTest, 6666L, textForMessageTest);
//        verify(startCommand).executeMessage(sendMessageTest);
//
//    }
//
//    @Test
//    public void prepareAndSendMessageTest() {
//
//        sendMessageTest.setText(DATING_RULES);
//
//        takePet.prepareAndSendMessage(6666L, DATING_RULES);
//        verify(startCommand).executeMessage(sendMessageTest);
//    }
//
//
//    @Test
//    public void takePetCommandReceivedTest() {
//
//        takePet.takePetCommandReceived(6666L);
//        verify(startCommand).executeMessage(any(SendMessage.class));
//    }
}