package pro.sky.JD2AnimalShelterBot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.JD2AnimalShelterBot.service.*;
import pro.sky.JD2AnimalShelterBot.service.pet.TakeCat;
import pro.sky.JD2AnimalShelterBot.service.pet.TakeDog;

import java.util.Objects;

import static pro.sky.JD2AnimalShelterBot.service.StartCommand.CAT_BUTTON;
import static pro.sky.JD2AnimalShelterBot.service.StartCommand.DOG_BUTTON;
import static pro.sky.JD2AnimalShelterBot.сonstants.CatConstants.CAT_DATING_RULES;
import static pro.sky.JD2AnimalShelterBot.сonstants.CatConstants.CAT_DATING_RULES_COMMAND_LABEL;
import static pro.sky.JD2AnimalShelterBot.сonstants.DogConstants.*;
import static pro.sky.JD2AnimalShelterBot.сonstants.MainMenuConstants.*;
import static pro.sky.JD2AnimalShelterBot.сonstants.ShelterConstants.*;

/**
 * Класс для обработки входящих сообщений пользователя.
 * Запрос направляется в соответствующий метод для получения ответа в зависимости от сути запроса
 */
@Slf4j
@Component
public class UpdateHandler implements InputMessageHandler {

    private final StartCommand startCommand;
    private final ShelterInfo shelterInfo;
    private final TakeDog takeDog;

    private final TakeCat takeCat;
    private final CommunicationWithVolunteer communicationWithVolunteer;

    private final ExecuteMessage executeMessage;
    private final UserService userService;
    private final UserContext userContext;


    /**
     * Конструктор - создание нового объекта с определенным значением конфигурации
     *
     * @param startCommand   - объект обработчика команды /start
     * @param shelterInfo    - объект "информация о приюте"
     * @param takeDog        - объект "информация об усыновлении собаки"
     * @param takeCat        - объект "информация об усыновлении кошки"
     * @param executeMessage - объект для работы с классом по отправке ответов пользователю
     * @param userService    - объект для работы с методами класса UserService
     * @param userContext    - объект для определения контекста пользователя для корректного выбора меню
     */
    public UpdateHandler(@Lazy StartCommand startCommand,
                         @Lazy ShelterInfo shelterInfo,
                         @Lazy TakeDog takeDog,
                         @Lazy TakeCat takeCat,
                         @Lazy CommunicationWithVolunteer communicationWithVolunteer,
                         @Lazy ExecuteMessage executeMessage,
                         UserService userService,
                         UserContext userContext) {

        this.startCommand = startCommand;
        this.shelterInfo = shelterInfo;
        this.takeDog = takeDog;
        this.takeCat = takeCat;
        this.communicationWithVolunteer = communicationWithVolunteer;
        this.executeMessage = executeMessage;
        this.userService = userService;
        this.userContext = userContext;
    }

    /**
     * метод, определяющий, что должен делать бот, когда ему поступает тот или иной запрос
     */
    @Override
    public void handle(Update update) {

        //Если нажата inline кнопка
        if (update.hasCallbackQuery()) {
            this.processingOfButtons(update);
            return;
        }

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        //Если пользователь прислал контакты
        if (update.hasMessage() && update.getMessage().getContact() != null) {
            userService.setUserPhone(update.getMessage());
            communicationWithVolunteer.volunteerButtonHandler(update);
            return;
        }

        // Если не выбраны кошки или собаки
        if(userContext.getUserContext(chatId) == null || (!userContext.getUserContext(chatId).contains("dog")
                                                      && !userContext.getUserContext(chatId).contains("cat"))) {
            startCommand.choosingTypeOfPet(chatId);
            return;
        }

        // Если пользователь пишет сообщение волонтеру
        if(userContext.getUserContext(chatId).equals("messageToVolunteer")) {
            communicationWithVolunteer.volunteerTextHandler(update);
            return;
        }

        switch (messageText) {
            case "/start":
                startCommand.startCallBack(chatId, update);
                break;

            case "❓ Узнать информацию о приюте":
                shelterInfo.createMenuShelterInfo();
                break;

            case TAKE_PET_COMAND_LABEL:
                if (userContext.getUserContext(chatId).contains("dog")) {
                    takeDog.takePetCommandReceived(chatId);
                    break;
                } else takeCat.takePetCommandReceived(chatId);

            case DOG_DATING_RULES_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, DOG_DATING_RULES, takeDog.createMenuTakePet());
                break;

            case DOG_DOCUMENTS_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, DOG_DOCUMENTS, takeDog.createMenuTakePet());
                break;

            case DOG_SHIPPING_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, DOG_SHIPPING, takeDog.createMenuTakePet());
                break;

            case RECOMM_FOR_PUPPY_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_PUPPY, takeDog.createMenuTakePet());
                break;

            case RECOMM_FOR_DOG_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_1, takeDog.createMenuTakePet());
                executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_2, takeDog.createMenuTakePet());
                break;

            case RECOMM_FOR_DOG_INVALID_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_INVALID, takeDog.createMenuTakePet());
                break;

            case CYNOLOGIST_INITIAL_ADVICE_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, CYNOLOGIST_INITIAL_ADVICE, takeDog.createMenuTakePet());
                break;

            case RECOMMENDED_CYNOLOGIST_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, RECOMMENDED_CYNOLOGIST, takeDog.createMenuTakePet());
                break;

            case REASONS_FOR_REFUSAL_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, REASONS_FOR_REFUSAL, takeDog.createMenuTakePet());
                break;

            case CAT_DATING_RULES_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, CAT_DATING_RULES, takeCat.createMenuTakePet());
                break;


            case CALL_VOLUNTEER_COMAND_LABEL:
                communicationWithVolunteer.volunteerButtonHandler(update);
                break;


            case SHELTER_INFO_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, SHELTER_INFO, shelterInfo.createMenuShelterInfo());
                break;

            case SCHEDULE_ADDRESS_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, SCHEDULE_ADDRESS, shelterInfo.createMenuShelterInfo());
                break;

            case SAFETY_RULES_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, SAFETY_RULES, shelterInfo.createMenuShelterInfo());
                break;

            case CONTACT_DATA_COMMAND_LABEL:
                executeMessage.prepareAndSendMessage(chatId, CONTACT_DATA, shelterInfo.createMenuShelterInfo());
                break;

            case MAIN_MENU_LABEL:
                startCommand.returnToMainMenu(chatId);
                break;


            default:
                System.out.println("Неизвестная команда: " + messageText);
                break;
        }
    }

    private void processingOfButtons(Update update) {

        String callBackData = update.getCallbackQuery().getData();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        if (callBackData.equals(DOG_BUTTON)) {
            userContext.setUserContext(chatId, "dog");
            userContext.deleteUserContext(chatId, "cat");
        }
        if (callBackData.equals(CAT_BUTTON)) {
            userContext.setUserContext(chatId, "cat");
            userContext.deleteUserContext(chatId, "dog");
        }
        startCommand.startCallBack(chatId, update);
    }
}
