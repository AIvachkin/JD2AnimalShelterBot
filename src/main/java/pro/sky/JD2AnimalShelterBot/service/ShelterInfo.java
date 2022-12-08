package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс обрабатывает запросы пользователей, желающих получить информацию о приюте
 */
@Slf4j
@Service
public class ShelterInfo {
    private final ExecuteMessage executeMessage;
    protected ShelterInfo(ExecuteMessage executeMessage) {
        this.executeMessage = executeMessage;
    }
    /**
     * Метод создания кнопочного меню
     */
    public void createMenuShelterInfo(long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(Commands.SHELTER_INFO.getLabel()));
        row1.add(new KeyboardButton(Commands.SCHEDULE_ADDRESS.getLabel()));
        keyboardRows.add(row1);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(Commands.SAFETY_RULES.getLabel()));
        row2.add(new KeyboardButton(Commands.CONTACT_DATA.getLabel()));
        keyboardRows.add(row2);
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton(Commands.CALL_VOLUNTEER_COMAND.getLabel()));
        row3.add(new KeyboardButton(Commands.MAIN_MENU.getLabel()));
        keyboardRows.add(row3);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);//Формирование клавиатуры
//Отправка приветственного сообщения и добавление клавиатуры
        executeMessage.prepareAndSendMessage(chatId,GREETING_INFO,keyboardMarkup);

        log.info("Keyboard ShelterInfo has been added to bot");
    }

    /**
     * Константа - приветственное сообщение для пользователя
     */
    public static final String GREETING_INFO = """
            Здравствуйте, здесь Вы можете получить информацию о приюте: где он находится, как и когда работает, 
            какие правила пропуска на территорию приюта, правила нахождения внутри и общения с питомцем.
            Выберите команду в меню \u2B07
            Если нужного вопроса нет в меню, позовите волонтера, который поможет Вам.
                        
            """;
    /**
     * Константа - информация о приюте
     */
    public static final String SHELTER_INFO = """
            Добро пожаловать в наш приют, созданный волонтерами приюта "Волна" для поиска находящимся здесь собакам 
            теплого, любящего и надежного дома.            
            К сожалению, бездомные собаки не исчезнут с улиц города;
            сейчас их численность регулируют путем отлова и размещения в приюты.  
            Без нашей помощи они никогда не получат шанса на новую, более светлую и достойную жизнь, 
            полную любви и заботы хозяина, никогда не поделятся с человеком своей безграничной нежностью.
            Если вы хотите, чтобы в вашем доме поселилась маленькая пушистая радость – поищите его в приюте!             
                        
            Спасибо вам за доброту!
            """;
    /**
     * Константа - адрес и расписание приюта
     */
    public static final String SCHEDULE_ADDRESS = """
           Наш адрес:
           Улица Аккорган, 5в
           Коктал ж/м, Сарыарка район, Астана
           1 этаж
            
           Ежедневно с 11:00 до 18:00
           """;
    /**
     * Константа - правила поведения в приюте
     */
    public static final String SAFETY_RULES = """
            ПРАВИЛА ПОВЕДЕНИЯ НА ТЕРРИТОРИИ ПРИЮТА:
            1. После входа и выхода из вольеров тщательно следить за тем, чтобы двери были хорошо закрыты на щеколду.
            2. Перед прогулкой с собакой за территорией приюта тщательно проверить на целостность поводка, 
            надежность карабина, насколько туго затянут ошейник, дабы избежать срыва питомца с поводка во время прогулки.
            3. Для угощения собак можно приносить только специальные собачьи лакомства. Ничего соленого, сладкого, 
            жирного, никаких куриных костей!
            4. Угощать собаку через сетку вольера категорически запрещается! Можно либо войти в вольер со знакомыми 
            собаками, либо угостить собаку на прогулке.
            5. Перед тем, как взять на прогулку новую (незнакомую) собаку, спросите у старших волонтеров или 
            работников - можно ли с ней гулять.
            6. Не заходите без опытных сопровождающих в вольеры с незнакомыми собаками!
            Не все собаки милые и дружелюбные, многие выросли на улице и людям не особо доверяют. 
            Или же они могут быть настолько рады вам, что вы не сумеете совладать с этой радостью.
            7. Если вы собираетесь посетить нас впервые - свяжитесь с опытными волонтерами, 
            вас сопроводят и помогут с адаптацией.
            8. По территории приюта передвигайтесь спокойно, не бегайте, не машите руками, говорите спокойно.
            Конечно бывают исключения, когда необходимо крикнуть или разнять дерущихся - но это особый пункт.
            9. Если при вас собаки начали драку - не лезьте голыми руками их разнимать!!!! Сразу зовите на помощь!!! 
            Вам помогут либо опытные волонтеры, либо работники. В крайнем случае, возьмите какой-либо инструмент 
            (лопата, метла) и попробуйте им разнять дерущихся!
            10. При первом посещении не трогайте собак, не смотрите в глаза. Спокойно проходите и занимайтесь делами. 
            Когда собаки с вами познакомятся (обнюхают), тогда можно и погладить.
            """;
    /**
     * Константа - оставить контактные данные
     */
    public static final String CONTACT_DATA = """
            Напишите Ваш телефон и с Вами обязательно свяжется наш волонтер
            """;
//    /**
//     * Метод отправки нужного сообщения
//     */
//    public void commandProcessing(Update update, long chatId, String messageText) {
//        switch (messageText) {
//            case "/shelter_info" -> prepareAndSendMessage(chatId, SHELTER_INFO);
//            case "/schedule_address" -> prepareAndSendMessage(chatId, SCHEDULE_ADDRESS);
//            case "/safety_rules" -> prepareAndSendMessage(chatId, SAFETY_RULES);
//            case "/contact_data" -> prepareAndSendMessage(chatId, CONTACT_DATA);
//            default -> CallVolunteer.callVolunteer();
//        }
//    }
}