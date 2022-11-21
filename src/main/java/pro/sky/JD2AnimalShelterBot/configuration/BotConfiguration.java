package pro.sky.JD2AnimalShelterBot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
/**
* Класс, задающий кофигурацию для бота
* Свойства считываются из application.properties
*/
public class BotConfiguration {

    @Value("${bot.name}")
    /** Поле - имя бота */
    String botName;

    @Value("${bot.token}")
    /** Поле - токен бота */
    String token;

}
