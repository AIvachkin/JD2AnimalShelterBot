package pro.sky.JD2AnimalShelterBot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pro.sky.JD2AnimalShelterBot.util.StringUtil;

import java.util.Optional;
import java.util.stream.Stream;

import static pro.sky.JD2AnimalShelterBot.сonstants.CatConstants.*;
import static pro.sky.JD2AnimalShelterBot.сonstants.DogConstants.*;
import static pro.sky.JD2AnimalShelterBot.сonstants.ShelterConstants.*;
import static pro.sky.JD2AnimalShelterBot.сonstants.MainMenuConstants.*;


@Getter
@RequiredArgsConstructor
public enum Commands {
    START_COMAND("/start", "Регистрация пользователя", START_COMAND_LABEL),
    INFORMATION_COMAND("/information", "Узнать информацию о приюте", INFORMATION_COMAND_LABEL),
    TAKE_PET_COMAND("/how_take_pet", "Как взять собаку из приюта", TAKE_PET_COMAND_LABEL),
    SEND_REPORT_COMAND("/send_pet_report", "Прислать отчет о питомце", SEND_REPORT_COMAND_LABEL),

    CALL_VOLUNTEER_COMAND("/call_volunteer", "Позвать волонтера", CALL_VOLUNTEER_COMAND_LABEL),

    SHELTER_INFO_COMMAND("/shelter_info", SHELTER_INFO, SHELTER_INFO_COMMAND_LABEL),
    SCHEDULE_ADDRESS_COMMAND("/schedule_address", SCHEDULE_ADDRESS, SCHEDULE_ADDRESS_COMMAND_LABEL),
    SAFETY_RULES_COMMAND("/safety_rules", SAFETY_RULES, SAFETY_RULES_COMMAND_LABEL),
    CONTACT_DATA_COMMAND("/contact_data", CONTACT_DATA, CONTACT_DATA_COMMAND_LABEL),
    MAIN_MENU_COMMAND("/main_menu", MAIN_MENU, MAIN_MENU_LABEL),

    DOG_DATING_RULES_COMMAND("/dating_rules", DOG_DATING_RULES, DOG_DATING_RULES_COMMAND_LABEL),
    DOG_DOCUMENTS_COMMAND("/documents", DOG_DOCUMENTS, DOG_DOCUMENTS_COMMAND_LABEL),
    DOG_SHIPPING_COMMAND("/shipping", DOG_SHIPPING, DOG_SHIPPING_COMMAND_LABEL),
    RECOMM_FOR_PUPPY_COMMAND("/recommendation_for_puppy", RECOMM_FOR_PUPPY, RECOMM_FOR_PUPPY_COMMAND_LABEL),
    RECOMM_FOR_DOG_COMMAND("/recommendation_for_dog", RECOMM_FOR_DOG, RECOMM_FOR_DOG_COMMAND_LABEL),
    RECOMM_FOR_DOG_INVALID_COMMAND("/recommendation_for_dog_invalid", RECOMM_FOR_DOG_INVALID, RECOMM_FOR_DOG_INVALID_COMMAND_LABEL),
    CYNOLOGIST_INITIAL_ADVICE_COMMAND("/initial_advice", CYNOLOGIST_INITIAL_ADVICE, CYNOLOGIST_INITIAL_ADVICE_COMMAND_LABEL),
    RECOMMENDED_CYNOLOGIST_COMMAND("/recommeded_cynologist", RECOMMENDED_CYNOLOGIST, RECOMMENDED_CYNOLOGIST_COMMAND_LABEL),
    REASONS_FOR_REFUSAL_COMMAND("/refusal", REASONS_FOR_REFUSAL, REASONS_FOR_REFUSAL_COMMAND_LABEL),

    CAT_DATING_RULES_COMMAND("/cat_dating_rules", CAT_DATING_RULES, CAT_DATING_RULES_COMMAND_LABEL),

    CAT_DOCUMENTS_COMMAND("/cat_documents", CAT_DOCUMENTS, CAT_DOCUMENTS_LABEL),

    CAT_SHIPPING_COMMAND("/cat_shipping", CAT_SHIPPING, CAT_SHIPPING_COMMAND_LABEL),

    RECOMM_FOR_CAT_COMMAND("/recommendation_for_cat", RECOMM_FOR_CAT, RECOMM_FOR_CAT_COMMAND_LABEL),

    RECOMM_FOR_CAT_INVALID_COMMAND("/recommendation_for_cat_invalid", RECOMM_FOR_CAT_INVALID, RECOMM_FOR_CAT_INVALID_COMMAND_LABEL);


    /**
     * Поле - имя перечисления
     */
    private final String name;

    /**
     * Поле - описание перечисления
     */
    private final String desc;

    /**
     * Поле - изображение перечисления
     */
    private final String label;

    /**
     * Метод парсит поступившую команду на предмет эквивалентности имени или лейблу одной из команд в enum
     *
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

