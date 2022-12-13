package pro.sky.JD2AnimalShelterBot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.JD2AnimalShelterBot.service.*;

import static pro.sky.JD2AnimalShelterBot.service.TakePet.*;

/**
 * Класс для обработки входящих сообщений пользователя.
 * Запрос направляется в соответствующий метод для получения ответа в зависимости от сути запроса
 */
@Slf4j
@Component
public class UpdateHandler implements InputMessageHandler {

    private final StartCommand startCommand;
    private final ShelterInfo shelterInfo;
    private final TakePet takePet;
    private final CommunicationWithVolunteer communicationWithVolunteer;

    private final ExecuteMessage executeMessage;


    /**
     * Конструктор - создание нового объекта с определенным значением конфигурации
     *
     * @param startCommand   - объект обработчика команды /start
     * @param shelterInfo    - объект "информация о приюте"
     * @param takePet        - объект "информация об усыновлении животного"
     * @param executeMessage - объект для работы с классом по отправке ответов пользователю
     */
    public UpdateHandler(@Lazy StartCommand startCommand,
                         @Lazy ShelterInfo shelterInfo,
                         @Lazy TakePet takePet,
                         @Lazy CommunicationWithVolunteer communicationWithVolunteer,
                         @Lazy ExecuteMessage executeMessage) {

        this.startCommand = startCommand;
        this.shelterInfo = shelterInfo;
        this.takePet = takePet;
        this.communicationWithVolunteer = communicationWithVolunteer;
        this.executeMessage = executeMessage;
    }

    /**
     * метод, определяющий, что должен делать бот, когда ему поступает тот или иной запрос
     */
    @Override
    public void handle(Update update) {

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        switch (messageText) {
            case "/start":
                startCommand.startCallBack(chatId, update);
                break;

            case "❓ Узнать информацию о приюте":
                shelterInfo.createMenuShelterInfo(chatId);
                break;

            case "\uD83D\uDC36️ Как взять собаку из приюта":
                takePet.takePetCommandReceived(chatId);
                break;

            case "\uD83D\uDC36  Правила знакомства с собакой":
                executeMessage.prepareAndSendMessage(chatId, DATING_RULES, takePet.createMenuTakePet());
                break;

            case "\uD83D\uDCDC  Список необходимых документов":
                executeMessage.prepareAndSendMessage(chatId, DOCUMENTS, takePet.createMenuTakePet());
                break;

            case "\uD83D\uDEFB  Транспортировка животного":
                executeMessage.prepareAndSendMessage(chatId, SHIPPING, takePet.createMenuTakePet());
                break;

            case "\uD83C\uDFE1  Дом для щенка":
                executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_PUPPY, takePet.createMenuTakePet());
                break;

            case "\uD83C\uDFE1  Дом для взрослой собаки":
                executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_1, takePet.createMenuTakePet());
                executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_2, takePet.createMenuTakePet());
                break;

            case "\uD83C\uDFE1  Дом для собаки-инвалида":
                executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_INVALID, takePet.createMenuTakePet());
                break;

            case "\uD83D\uDCC3  Советы кинолога":
                executeMessage.prepareAndSendMessage(chatId, CYNOLOGIST_INITIAL_ADVICE, takePet.createMenuTakePet());
                break;

            case "\uD83C\uDFC5  Лучшие кинологи":
                executeMessage.prepareAndSendMessage(chatId, RECOMMENDED_CYNOLOGIST, takePet.createMenuTakePet());
                break;

            case "\uD83D\uDEC7  Почему Вам могут отказать?":
                executeMessage.prepareAndSendMessage(chatId, REASONS_FOR_REFUSAL, takePet.createMenuTakePet());
                break;

            case "\uD83D\uDC69\u200D\uD83C\uDF3E  Позвать волонтера":
                communicationWithVolunteer.volunteerButtonHandler(update);

                break;
            default:
                System.out.println("Неизвестная команда: " + messageText);
                break;
        }

    }
}
