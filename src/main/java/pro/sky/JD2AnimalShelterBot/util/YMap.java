package pro.sky.JD2AnimalShelterBot.util;

import io.github.mvpotter.model.Coordinate;
import io.github.mvpotter.model.Size;
import io.github.mvpotter.model.YandexMap;
import io.github.mvpotter.model.marker.Marker;
import io.github.mvpotter.model.marker.Style;
import io.github.mvpotter.urlbuilder.YandexApiUrlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.JD2AnimalShelterBot.service.TelegramBot;

/**
 * Класс для отправки карты (местоположение приюта)
 */
@Component
@Slf4j
public class YMap {
    private final TelegramBot telegramBot;
    private final YandexMap yandexMap = new YandexMap();

    public YMap(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public static Coordinate dogShelterCoordinate = new Coordinate("37.598981", "55.498720");
    public static Coordinate catShelterCoordinate = new Coordinate("71.434687", "51.169102");

    /**
     * Метод отправки карты Яндекс в чат
     */
    public void yMapInit(long chatId, Coordinate coordinate) {
        yandexMap.setMapType(YandexMap.MapType.MAP);
        yandexMap.setCenter(coordinate);
        yandexMap.setViewport(new Coordinate("0.005", "0.005"));
        yandexMap.setScale(7);
        yandexMap.setSize(new Size(650, 450));
        yandexMap.setLanguage(YandexMap.Language.RUSSIAN);

        yandexMap.addMarker(new Marker(coordinate, Style.FLAG));

        YandexApiUrlBuilder yandexApiUrlBuilder = new YandexApiUrlBuilder();

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(yandexApiUrlBuilder.build(yandexMap)));
        try {
            telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
