package pro.sky.JD2AnimalShelterBot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * Класс, задающий конфигурацию для бота
 * Свойства считываются из application.properties
 */
@Configuration
@Data
@PropertySource("application.properties")
public class BotConfiguration {

    /**
     *
     * Поле - имя бота
     */
    @Value("${bot.name}")
    String botName;

    /**
     *
     * Поле - токен бота
     */
    @Value("${bot.token}")
    String token;

}
