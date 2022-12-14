package pro.sky.JD2AnimalShelterBot.service;

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

/**
 * Класс отправки карты
 */
@Component
@Slf4j
public class YMap {
    private final TelegramBot telegramBot;
    private final YandexMap yandexMap = new YandexMap();

    public YMap(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Метод отправки карты Яндекс в чат
     */
    public void yMapInit(long chartId) {
        yandexMap.setMapType(YandexMap.MapType.MAP);
        yandexMap.setCenter(new Coordinate("37.598981", "55.498720"));
        yandexMap.setViewport(new Coordinate("0.005", "0.005"));
        yandexMap.setScale(7);
        yandexMap.setSize(new Size(650, 450));
        yandexMap.setLanguage(YandexMap.Language.RUSSIAN);

        yandexMap.addMarker(new Marker(new Coordinate("37.598981", "55.498720"), Style.FLAG));

        YandexApiUrlBuilder yandexApiUrlBuilder = new YandexApiUrlBuilder();

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chartId);
        sendPhoto.setPhoto(new InputFile(yandexApiUrlBuilder.build(yandexMap)));
        try {
            telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
