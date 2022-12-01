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
    CALL_VOLUNTEER_COMAND("/call_volunteer", "Позвать волонтера", "\uD83E\uDDD1\u200D\uD83C\uDF3E️ Позвать волонтера");


    private final String name;
    private final String desc;
    private final String label;

    public static Optional<Commands> parseCommand(String command) {
        if (StringUtil.isBlank(command)) {
            return Optional.empty();
        }
        String formatName = StringUtil.trim(command).toLowerCase();
        return Stream.of(values()).filter(c -> c.name.equalsIgnoreCase(formatName) || c.label.equalsIgnoreCase(formatName))
                .findFirst();
    }
}

