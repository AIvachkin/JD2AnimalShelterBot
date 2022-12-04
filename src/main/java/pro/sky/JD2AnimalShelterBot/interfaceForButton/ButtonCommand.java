package pro.sky.JD2AnimalShelterBot.interfaceForButton;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.JD2AnimalShelterBot.service.StartCommand;

public abstract class ButtonCommand {
    public final StartCommand startCommand;
    /**
     * Абстрактный класс обработки нажатия кнопки меню
     */
    protected ButtonCommand(StartCommand startCommand) {
        this.startCommand = startCommand;
    }
//    /**
//     * Метод, обрабатывающий запрос пользователя,
//     * и предоставляющий интересующую информацию
//     */
//    public void onButton(Update update) {
//        String messageText = update.getMessage().getText();
//        if (update.hasMessage() && update.getMessage().hasText()) {
//
//            commandProcessing(update, update.getMessage().getChatId(), messageText);
//        } else {
//            SendMessage message = new SendMessage();
//            message.setChatId(update.getMessage().getChatId());
//            message.setText("Sorry, command was not recognized");
//            try {
//                bot.execute(message);
//            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
    /**
     * Метод, предоставляющий информацию
     * в зависимости от поступившей команды
     */
    public void commandProcessing(Update update, long chatId, String messageText) {
    }

    /**
     * Метод, формирующий ответное сообщение для отправки его пользователю
     */
    public void prepareAndSendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        startCommand.executeMessage(message);
    }
}
