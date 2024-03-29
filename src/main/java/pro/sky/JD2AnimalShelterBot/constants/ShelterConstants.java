package pro.sky.JD2AnimalShelterBot.constants;

public class ShelterConstants {

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
     * Константа - сообщение пользователю, отправляющему отчет о питомце
     */
    public static final String TRUSTEES_REPORT_INFO = """
            Напоминаем, ежедневный отчет должен содержать следующую информацию:
            В одном сообщении необходимо прислать:
            - Одно (!Важно) фото животного
            - Рацион животного, общее самочувствие и привыкание к новому месту,
            изменение в поведении: отказ от старых привычек, приобретение новых.
            Напишите Ваш отчет в текстовой строке ниже и прикрепите к сообщению фото.
            """;

    /**
     * Константа - информация о приюте для собак
     */
    public static final String DOG_SHELTER_INFO = """
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
     * Константа - информация о приюте для кошек
     */
    public static final String CAT_SHELTER_INFO = """
            Приют для кошек «Новый дом» существует в Астане уже более 6 лет. В стенах приюта содержатся брошенные,
            оказавшиеся на улице и просто никому не нужные кошки, коты и котята.
            В приюте проживают более 100 животных, с разной судьбой и разного возраста. Большинство животных отданы
            добрыми людьми или бывшими хозяевами. У каждой кошки свой характер — кто-то ласковый, кто-то пугливый,
            игривый или стеснительный, грустный, своенравный или веселый. В приют попадают и обычные полосатики
            и породистые шотландцы, каждая кошка красива по-своему.
            Наша команда волонтеров старается сделать всё от них зависящее, чтобы все питомцы чувствовали в стенах
            приюта уют и хоть малую частичку дома. Однако мы не можем заменить им дом и заботливых хозяев в полной
            мере. К сожалению, многие люди боятся брать животное из приюта, думая, что тут все больные или чем-то
            хуже других, но на самом деле все проблемы не в кошках, а в людях, что плохо к ним относились до приюта.
            Вы же можете спасти хотя бы одну кошачью жизнь, подарить живому существу тёплый дом, любовь и ласку!
            Пока они живут в приюте, мы их единственная надежда! Все кошки, что живут в приюте, оказались на улице
            не по своей воле. Кроме нас с вами им никто не поможет. Если бы они умели говорить, то наверняка бы
            сказали, что им тоже хочется жить в тепле и заботе.
            """;

    /**
     * Константа - адрес и расписание приюта для собак
     */
    public static final String DOG_SCHEDULE_ADDRESS = """
           Наш адрес:
           ул. Брусилова, 32Б
            
           Ежедневно с 11:00 до 18:00
           """;

    /**
     * Константа - адрес и расписание приюта для кошек
     */
    public static final String CAT_SCHEDULE_ADDRESS = """
           Наш адрес:
           просп. Абая, 40,
           Нур-Султан (Астана)
           Ежедневно с 10:00 до 17:00
           """;

    /**
     * Константа - правила поведения в приюте для собак
     */
    public static final String DOG_SAFETY_RULES = """
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
     * Константа - правила поведения в приюте для кошек
     */
    public static final String CAT_SAFETY_RULES = """
            ПРАВИЛА ПОВЕДЕНИЯ НА ТЕРРИТОРИИ ПРИЮТА:
            На территории Приюта для всех посетителей действуют правила и распорядок, установленные администрацией Приюта.
            При посещении Приюта необходимо иметь документ удостоверяющий личность или постоянный пропуск, для детей
            - документ подтверждающий возраст. Дети до 16 лет без сопровождения взрослых (родителей, опекунов,
            представителей с предъявленной рукописной доверенностью) на территорию приюта не допускаются.
            Вся ответственность за несовершеннолетних детей, находящихся на территории приюта, возлагается на
            родителей (опекунов, представителей с предъявленной рукописной доверенностью).
            В приют не допускаются:
            дети до 16 лет без сопровождения взрослых (родителей, опекунов, представителей с предъявленной
            рукописной доверенностью);
            лица в состоянии алкогольного или наркотического опьянения;
            лица в агрессивном или неадекватном состоянии.
            Передача животного сотрудником Приюта посетителю для выгула осуществляется на специально отведенной территории.
            выдача животных сотрудниками Приюта посетителям осуществляется в порядке живой очереди;
            время выдачи животного сотрудниками Приюта посетителям для выгула не должно превышать 25 минут.
            """;

    /**
     * Константа - оставить контактные данные
     */
    public static final String CONTACT_DATA = """
            Оставьте Ваш телефон и с Вами обязательно свяжется наш волонтер
            """;


    /**
     * Константа - оставить контактные данные
     */
    public static final String SAFETY_PRECAUTIONS = """
            Находясь на территории приюта, пожалуйста, соблюдайте наши правила и технику безопасности!
            Запрещается:
            - Самостоятельно открывать выгулы и вольеры без разрешения работника приюта.
            - Кормить животных. Этим Вы можете спровоцировать драку. Угощения разрешены только постоянным
            опекунам и волонтерам, во время прогулок с животными на поводке.
            - Оставлять после себя мусор на территории приюта и прилегающей территории.
            - Подходить близко к вольерам и гладить животных через сетку на выгулах. Животные могут быть агрессивны!
            - Кричать, размахивать руками, бегать между будками или вольерами, пугать и дразнить животных.
            - Посещение приюта для детей дошкольного и младшего школьного возраста без сопровождения взрослых.
            - Нахождение на территории приюта детей среднего и старшего школьного возраста без сопровождения
            взрослых или письменной справки-разрешения от родителей или законных представителей.
            - Самостоятельно заходить в помещение к животным без разрешения сотрудников приюта.
            - Посещение приюта в состоянии алкогольного, наркотического опьянения.
            """;

    /**
     * Константа - надпись для кнопки меню приюта
     */
    public static final String SHELTER_INFO_COMMAND_LABEL = "❓ Информация о приюте";

    /**
     * Константа - надпись для кнопки меню приюта
     */
    public static final String SCHEDULE_ADDRESS_COMMAND_LABEL = "\uD83E\uDDED Адрес и расписание приюта";

    /**
     * Константа - надпись для кнопки меню приюта
     */
    public static final String SAFETY_RULES_COMMAND_LABEL = "\uD83D\uDCC3 Правила поведения в приюте";

    /**
     * Константа - надпись для кнопки меню приюта
     */
    public static final String SEND_FORM = "Получить форму ежедневного отчета";

    /**
     * Константа - надпись для кнопки меню приюта
     */
    public static final String FORM_FOR_REPORT = "<u>Форма ежедневного отчета:</u>      " +
            "<b>1. Рацион животного:</b> <i>укажите, чем кормили питомца за последние сутки</i>      " +
            "<b>2. Общее самочувствие и привыкание к новому месту:</b>  <i>опишите физическое и моральное(если возможно)" +
            " состояние питомца. Как он привыкает к дому, к семье, к месту, где он спит и ходит в туалет " +
            "(если это предусмотрено)</i>      " +
            "<b>3. Изменение в поведении:</b>  <i>напишите, заметили ли что-то новое или необычное в поведении" +
            "питомца за последние сутки. Появились ли какие-то новые привычки или пристрастия</i> ";

    /**
     * Константа - надпись для кнопки меню приюта
     */
    public static final String EXIT_THE_REPORT_FORM = "Отменить отправку отчета";

    /**
     * Константа - сообщение пользователю об успешном окончании испытательного срока и закреплении питомца
     */
    public static final String SUCCESSFUL_COMPLETION_OF_THE_PROBATION_PERIOD = """
            Поздравляем! Вы успешно прошли испытательный срок!
            Животное закреплено за Вами.
            Отчеты можно больше не присылать.
            """;

    /**
     * Константа - сообщение пользователю о провале испытательного срока
     */
    public static final String PROBATION_PERIOD_FAILED = """
            Вы не справились с испытательным сроком.
            Вам необходимо в течении трех дней вернуть животное в приют.
            По всем вопросам Вы можете проконсультироваться с волонтером приюта.
            """;

    /**
     * Константа - сообщение пользователю о том, что не прикреплено фото к отчету
     */
    public static final String NO_PHOTO_IN_THE_REPORT = """
            Вы забыли прикрепить фото к отчету.
            Пожалуйста, отправьте отчет еще раз, прикрепив к нему фото Вашего питомца.
            """;

    /**
     * Константа - сообщение пользователю о том, что не прикреплено фото к отчету
     */
    public static final String NO_TEXT_IN_THE_REPORT = """
            Вы забыли написать текстовую часть отчета.
            Пожалуйста, отправьте отчет еще раз, написав текстовую часть и  прикрепив к нему фото Вашего питомца.
            """;


    /**
     * Константа - сообщение пользователю о том, что отчет принят
     */
    public static final String REPORT_ACCEPTED = """
            Ваш отчет принят. Если считаете, что что-то упустили,
            можете прислать отчет повторно.
            Спасибо.
            """;
}
