package pro.sky.JD2AnimalShelterBot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.sky.JD2AnimalShelterBot.util.StringUtil;

import java.util.Optional;
import java.util.stream.Stream;



@Getter
@RequiredArgsConstructor
public enum Commands {
    START_COMAND("/start", "Регистрация пользователя", "Домой \uD83C\uDFE1"),
    INFORMATION_COMAND("/information", "Узнать информацию о приюте", "❓ Узнать информацию о приюте"),
    TAKE_PET_COMAND("/how_take_pet", "Как взять собаку из приюта", "\uD83D\uDC36️ Как взять собаку из приюта"),
    SEND_REPORT_COMAND("/send_pet_report", "Прислать отчет о питомце", "\uD83D\uDDD3 Прислать отчет о питомце"),

    CALL_VOLUNTEER_COMAND("/call_volunteer", "Позвать волонтера", "\uD83E\uDDD1\u200D\uD83C\uDF3E️ Позвать волонтера"),
    SHELTER_INFO("/shelter_info", "Информация о приюте", "❓ Информация о приюте"),
    SCHEDULE_ADDRESS("/schedule_address", "Адрес и расписание приюта", "\uD83E\uDDED Адрес и расписание приютае"),
    SAFETY_RULES("/safety_rules", "Правила поведения в приюте", "\uD83D\uDCC3 Правила поведения в приюте"),
    CONTACT_DATA("/contact_data", "Оставить контактные данные", "\uD83D\uDCDE Оставить контактные данные"),
    MAIN_MENU("/main_menu", "Вернуться в основное меню", "\uD83C\uDFE0 Основное меню");


    private final String name;
    private final String desc;
    private final String label;

    /**
     * Метод парсит поступившую команду на предмет эквивалентности имени или лейблу одной из команд в enum
     * @param command поступившая команда
     * @return возвращает команду из enum
     */
    public static Optional<Commands> parseCommand(String command) {
        if (StringUtil.isBlank(command)) {
            return Optional.empty();
        }
        String formatName = StringUtil.trim(command).toLowerCase();
        return Stream.of(values()).filter(c -> c.name.equalsIgnoreCase(formatName) || c.label.equalsIgnoreCase(formatName))
                .findFirst();
    }
}

