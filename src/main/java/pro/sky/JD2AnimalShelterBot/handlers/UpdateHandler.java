package pro.sky.JD2AnimalShelterBot.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.JD2AnimalShelterBot.service.*;
import pro.sky.JD2AnimalShelterBot.service.pet.TakeCat;
import pro.sky.JD2AnimalShelterBot.service.pet.TakeDog;

import java.io.IOException;

import static pro.sky.JD2AnimalShelterBot.service.StartCommand.CAT_BUTTON;
import static pro.sky.JD2AnimalShelterBot.service.StartCommand.DOG_BUTTON;
import static pro.sky.JD2AnimalShelterBot.service.YMap.catShelterCoordinate;
import static pro.sky.JD2AnimalShelterBot.service.YMap.dogShelterCoordinate;
import static pro.sky.JD2AnimalShelterBot.сonstants.CatConstants.*;
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

    private final TrusteesReportsService trusteesReportsService;

    private final ExecuteMessage executeMessage;
    private final UserService userService;
    private final UserContext userContext;
    private final YMap yMap;


    /**
     * Конструктор - создание нового объекта с определенным значением конфигурации
     *
     * @param startCommand           - объект обработчика команды /start
     * @param shelterInfo            - объект "информация о приюте"
     * @param takeDog                - объект "информация об усыновлении собаки"
     * @param takeCat                - объект "информация об усыновлении кошки"
     * @param trusteesReportsService
     * @param executeMessage         - объект для работы с классом по отправке ответов пользователю
     * @param yMap                   - объект - карта для отображения местонахождения приюта
     * @param userService            - объект для работы с методами класса UserService
     * @param userContext            - объект для определения контекста пользователя для корректного выбора меню
     */
    public UpdateHandler(StartCommand startCommand,
                         ShelterInfo shelterInfo,
                         TakeDog takeDog,
                         TakeCat takeCat,
                         CommunicationWithVolunteer communicationWithVolunteer,
                         @Lazy TrusteesReportsService trusteesReportsService,
                         ExecuteMessage executeMessage,
                         @Lazy YMap yMap,
                         UserService userService,
                         UserContext userContext) {

        this.startCommand = startCommand;
        this.shelterInfo = shelterInfo;
        this.takeDog = takeDog;
        this.takeCat = takeCat;
        this.communicationWithVolunteer = communicationWithVolunteer;
        this.trusteesReportsService = trusteesReportsService;
        this.executeMessage = executeMessage;
        this.userService = userService;
        this.userContext = userContext;
        this.yMap = yMap;
    }

    /**
     * метод, определяющий, что должен делать бот, когда ему поступает тот или иной запрос
     */
    @Override
    public void handle(Update update) throws IOException, TelegramApiException {

        //Если нажата inline кнопка
        if (update.hasCallbackQuery()) {
            this.processingOfButtons(update);
            return;
        }

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        //Если пользователь прислал контакты
        if (update.hasMessage() && update.getMessage().getContact() != null) {
            if (userContext.getUserContext(chatId).contains("dog")) {
                userService.setDodUserPhone(update.getMessage());
            } else if (userContext.getUserContext(chatId).contains("cat")) {
                userService.setCatUserPhone(update.getMessage());
            }
            communicationWithVolunteer.volunteerButtonHandler(update);
            return;
        }

        // Если не выбраны кошки или собаки
        if (userContext.getUserContext(chatId) == null || (!userContext.getUserContext(chatId).contains("dog")
            && !userContext.getUserContext(chatId).contains("cat"))) {
                startCommand.choosingTypeOfPet(chatId);
                return;
        }

        // Если пользователь пишет сообщение волонтеру
        if (userContext.getUserContext(chatId).contains("messageToVolunteer")) {
            communicationWithVolunteer.volunteerTextHandler(update);
            return;
        }

        // если пользователь отправляет отчет
        if (userContext.getUserContext(chatId).contains("dogUserReport") ||
            userContext.getUserContext(chatId).contains("catUserReport")) {
                trusteesReportsService.uploadReport(update, userContext);
                return;
        }

        if (messageText != null) {
            switch (messageText) {
                case START_COMAND:

                    startCommand.startCallBack(chatId, update);
                    break;

                case CHOOSE_SHELTER, EXIT_THE_REPORT_FORM:
                    SendMessage message = new SendMessage();
                    message.setChatId(String.valueOf(chatId));
                    message.setReplyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());
                    message.setText("Возврат в начальное меню");
                    executeMessage.executeMessage(message);
                    startCommand.choosingTypeOfPet(chatId);
                    break;

                case INFORMATION_COMMAND_LABEL:
                    shelterInfo.shelterInfoCommandReceived(chatId);
                    break;

                case TAKE_PET_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        takeDog.takePetCommandReceived(chatId);
                        break;
                    } else {
                        takeCat.takePetCommandReceived(chatId);
                    }
                    break;

                case CAR_PASS_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, DOG_CAR_PASS, startCommand.createMenuStartCommand());
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, CAT_CAR_PASS, startCommand.createMenuStartCommand());
                    }
                    break;

                case SAFETY_PRECAUTIONS_LABEL:
                    executeMessage.prepareAndSendMessage(chatId, SAFETY_PRECAUTIONS, startCommand.createMenuStartCommand());
                    break;


                case DATING_RULES_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, DOG_DATING_RULES, takeDog.createMenuTakePet());
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, CAT_DATING_RULES, takeCat.createMenuTakePet());
                    }
                    break;

                case DOCUMENTS_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, DOG_DOCUMENTS, takeDog.createMenuTakePet());
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, CAT_DOCUMENTS, takeCat.createMenuTakePet());
                    }
                    break;

                case SHIPPING_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, DOG_SHIPPING, takeDog.createMenuTakePet());
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, CAT_SHIPPING, takeCat.createMenuTakePet());
                    }
                    break;

                case RECOMM_FOR_PUPPY_COMMAND_LABEL:
                    executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_PUPPY, takeDog.createMenuTakePet());
                    break;

                case RECOMM_FOR_DOG_COMMAND_LABEL:
                    executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_1, takeDog.createMenuTakePet());
                    executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_2, takeDog.createMenuTakePet());
                    break;

                case RECOMM_FOR_PET_INVALID_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_DOG_INVALID, takeDog.createMenuTakePet());
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_CAT_INVALID, takeCat.createMenuTakePet());
                    }
                    break;

                case CYNOLOGIST_INITIAL_ADVICE_COMMAND_LABEL:
                    executeMessage.prepareAndSendMessage(chatId, CYNOLOGIST_INITIAL_ADVICE, takeDog.createMenuTakePet());
                    break;

                case RECOMMENDED_CYNOLOGIST_COMMAND_LABEL:
                    executeMessage.prepareAndSendMessage(chatId, RECOMMENDED_CYNOLOGIST, takeDog.createMenuTakePet());
                    break;

                case REASONS_FOR_REFUSAL_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, REASONS_FOR_REFUSAL, takeDog.createMenuTakePet());
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, REASONS_FOR_REFUSAL, takeCat.createMenuTakePet());
                    }
                    break;

                case RECOMM_FOR_CAT_COMMAND_LABEL:
                    executeMessage.prepareAndSendMessage(chatId, RECOMM_FOR_CAT, takeCat.createMenuTakePet());
                    break;


                case CALL_VOLUNTEER_COMMAND_LABEL:
                    communicationWithVolunteer.volunteerButtonHandler(update);
                    break;


                case SHELTER_INFO_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, DOG_SHELTER_INFO, shelterInfo.createMenuShelterInfo());
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, CAT_SHELTER_INFO, shelterInfo.createMenuShelterInfo());
                    }
                    break;

                case SCHEDULE_ADDRESS_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, DOG_SCHEDULE_ADDRESS, shelterInfo.createMenuShelterInfo());
                        yMap.yMapInit(chatId, dogShelterCoordinate);
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, CAT_SCHEDULE_ADDRESS, shelterInfo.createMenuShelterInfo());
                        yMap.yMapInit(chatId, catShelterCoordinate);
                    }
                    break;

                case SAFETY_RULES_COMMAND_LABEL:
                    if (userContext.getUserContext(chatId).contains("dog")) {
                        executeMessage.prepareAndSendMessage(chatId, DOG_SAFETY_RULES, shelterInfo.createMenuShelterInfo());
                        break;
                    } else {
                        executeMessage.prepareAndSendMessage(chatId, CAT_SAFETY_RULES, shelterInfo.createMenuShelterInfo());
                    }
                    break;

                case CONTACT_DATA_COMMAND_LABEL:
                    userService.requestContactDetails(chatId);
                    break;

                case MAIN_MENU_LABEL:
                    startCommand.returnToMainMenu(chatId);
                    break;


                case SEND_REPORT_COMMAND_LABEL:
                    trusteesReportsService.trusteesButtonHandler(update);

                    break;

                case SEND_FORM:
                    executeMessage.prepareAndSendMessage(chatId, FORM_FOR_REPORT, trusteesReportsService.createMenuForSendReport());
                    break;


                default:
                    if (userContext.getUserContext(chatId).contains("dogUserReport") ||
                            userContext.getUserContext(chatId).contains("catUserReport")) {
                        System.out.println("default");
                        trusteesReportsService.uploadReport(update, userContext);
                        return;
                    }


                    System.out.println("Неизвестная команда: " + messageText);
                    break;
            }
        }



    }

    /**
     * Метод, задающий контекст пользователю - cat или dog - в зависимости от выбора приюта
     */
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

