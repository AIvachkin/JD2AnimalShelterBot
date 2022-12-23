package pro.sky.JD2AnimalShelterBot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.sky.JD2AnimalShelterBot.model.CatUser;
import pro.sky.JD2AnimalShelterBot.model.DogUser;
import pro.sky.JD2AnimalShelterBot.model.Pet;
import pro.sky.JD2AnimalShelterBot.model.TrusteesReports;
import pro.sky.JD2AnimalShelterBot.repository.CatUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.DogUserRepository;
import pro.sky.JD2AnimalShelterBot.repository.PetRepository;
import pro.sky.JD2AnimalShelterBot.repository.TrusteesReportsRepository;

import javax.imageio.ImageIO;
import javax.ws.rs.NotFoundException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static pro.sky.JD2AnimalShelterBot.сonstants.ShelterConstants.*;

@Service
@Slf4j
public class TrusteesReportsService {
    private final CatUserRepository catUserRepository;
    private final DogUserRepository dogUserRepository;

    @Autowired
    private final TelegramBot telegramBot;

    private static final String WORNING_TEXT = """
            Предупреждение:
            Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. 
            Пожалуйста, подойди ответственнее к этому занятию. 
            В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного.
            """;
    /**
     * Путь на сервере, по которому хранятся фото животных из отчетов пользователей
     */
    @Value("${pet.photo.dir.path}")
    private String photoPetDir;

    private final TrusteesReportsRepository trusteesReportsRepository;
    private final PetRepository petRepository;
    private final ExecuteMessage executeMessage;

    private final UserContext userContext;

    public TrusteesReportsService(TrusteesReportsRepository trusteesReportsRepository,
                                  PetRepository petRepository, ExecuteMessage executeMessage, UserContext userContext,
                                  DogUserRepository dogUserRepository,
                                  CatUserRepository catUserRepository, TelegramBot telegramBot) {
        this.trusteesReportsRepository = trusteesReportsRepository;
        this.petRepository = petRepository;
        this.executeMessage = executeMessage;
        this.userContext = userContext;
        this.dogUserRepository = dogUserRepository;
        this.catUserRepository = catUserRepository;
        this.telegramBot = telegramBot;
    }

    /**
     * Метод возвращает список всех отчетов по конкретному животному
     *
     * @param petId ИД животного
     * @return List of TrusteesReports
     */
    public List<TrusteesReports> getAllPetReports(Long petId) {
        return trusteesReportsRepository.findAllByPet(petId);
    }

    /**
     * Метод отправляет пользователю предупреждение
     * @param petId ИД животного
     */
    public void sendWarning(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(NotFoundException::new);
        Long userId = null;
        if(pet.getTypeOfPet().equals("dog")){
            userId = pet.getDogUser().getChatId();
        } else if(pet.getTypeOfPet().equals("cat")){
            userId = pet.getCatUser().getChatId();
        }
        if(userId != null){
            executeMessage.prepareAndSendMessage(userId, WORNING_TEXT, null);
        }
    }

    /**
     * Метод возвращает пользователю сообщение (новые кнопки) после нажатия на кнопку "Отправить отчет"
     * и устанавливает контекст
     *
     * @param update объект входящего сообщения пользователя из Телеграм
     */
    public void trusteesButtonHandler(Update update) {


//        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

//присвоение нового контекста - отправка отчета
        if (userContext.getUserContext(chatId).contains("dog")) {
            userContext.setUserContext(chatId, "dogUserReport");
        } else if (userContext.getUserContext(chatId).contains("cat")) {
            userContext.setUserContext(chatId, "catUserReport");
        }

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(TRUSTEES_REPORT_INFO);
        message.setReplyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build());//Убираем клавиатуру

        ReplyKeyboardMarkup keyboardMarkup = createMenuForSendReport();

        message.setReplyMarkup(keyboardMarkup);

        executeMessage.executeMessage(message);
    }

    /**
     * Метод для создания клавиатуры меню по отправке отчетов пользователей
     */
    public ReplyKeyboardMarkup createMenuForSendReport() {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(SEND_FORM);
        keyboardRows.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(EXIT_THE_REPORT_FORM);
        keyboardRows.add(row2);

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboardRows);//Формирование клавиатуры

        log.info("Keyboard TrusteesReportInfo has been added to bot");

        return keyboardMarkup;


    }


    /**
     * Метод для загрузки в БД полного отчета пользователя (фото + текст)
     */
    public void uploadReport(Update update, UserContext context) throws TelegramApiException {

        //если пользователь не прикрепил фото к отчету
        if(!update.getMessage().hasPhoto()){
            executeMessage.prepareAndSendMessage(update.getMessage().getChatId(), NO_PHOTO_IN_THE_REPORT, null);
            return;
        }

        //если пользователь не прикрепил текст к отчету
        if(update.getMessage().getCaption() == null){
            executeMessage.prepareAndSendMessage(update.getMessage().getChatId(), NO_TEXT_IN_THE_REPORT, null);
            return;
        }

        //Получаем путь к файлу на сервере Telegram
        var photos = update.getMessage().getPhoto();
        var path = telegramBot.execute(new GetFile(photos.get(photos.size() - 1).getFileId())).getFilePath();

        //Формируем имя файла
        long chatId = update.getMessage().getChatId();
        var filePath = "./src/photos/" + chatId + "_" + LocalDateTime.now().hashCode() + ".jpeg";

        //Сохраняем файл на диск
        telegramBot.downloadFile(path, new java.io.File(filePath));

        //Coхраняем в базу
        //Удаляем  контекст
        //Шлем пользователю сообщение, что отчет получен

        //-----------------------------------------------------------------------------------//

        executeMessage.prepareAndSendMessage(update.getMessage().getChatId(), "все получилось", null);


        if (update.hasMessage() || update.getMessage().hasPhoto()) {

            Pet pet = new Pet();
            String typeOfPet = null;
            Message message = update.getMessage();

            System.out.println(message);

            // устанавливаем тип питомца в зависимости от контекста пользователя
            if (context.equals("dogUserReport")) {
                DogUser dogUser = dogUserRepository.findDogUsersByChatId(chatId);
                pet = petRepository.findPetByDogUser(dogUser);
                typeOfPet = "dog";

            } else if (context.equals("catUserReport")) {
                CatUser catUser = catUserRepository.findCatUserByChatId(chatId);
                pet = petRepository.findPetByCatUser(catUser);
                typeOfPet = "cat";
            }


            if (message.hasPhoto()) {

//            Рассматриваем массив фото
//            List<PhotoSize> photos = update.getMessage().getPhoto();
//
//            String fileId = photos.stream()
//                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
//                    .findFirst()
//                    .orElse(null).getFileId();


//            рассматриваем одно фото
                String fileId = message.getPhoto().get(0).getFileId();


//                File file = bot.execute(
//                        GetFile.builder()
//                                .fileId(fileId)
//                                .build());
//                String filePath = bot.downloadFile(file).getPath();



            }


//
//        TrusteesReports trusteesReports = new TrusteesReports();
//        trusteesReports.setChatId(chatId);
//        trusteesReports.setPet(pet);
//        trusteesReports.setDateTime(LocalDateTime.now());
//        trusteesReports.setTypeOfPet(typeOfPet);
//        trusteesReports.setViewed(false);

//        uploadPhoto(chatId, file, trusteesReports);


//        trusteesReportsRepository.save(trusteesReports);

        }
    }

    /**
     * Метод для загрузки в БД фото животного из отчета пользователя
     */
    public void uploadPhoto(long chatId, File file, TrusteesReports trusteesReports) throws IOException {

//        Path filePath = Path.of(photoPetDir, chatId + "_" + file.getFileUniqueId() + "."
//                + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
//        Files.createDirectories(filePath.getParent());
//
//        try (InputStream is = file.п;
//             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//        ) {
//            bis.transferTo(bos);
//        }
//
//
//        trusteesReports.setPhotoFilePath(filePath.toString());
//        trusteesReports.setPhotoFileSize(file.getSize());
//        trusteesReports.setMediaType(file.getContentType());
//        trusteesReports.setPreview(generatePreviewForDB(filePath));


    }


    /**
     * Метод для создания превью фото животного для БД
     */
    private byte[] generatePreviewForDB(Path filePath) throws IOException {
        log.info("Was invoked method to upload preview for database");
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    /**
     * Метод для получения расширения файла, отправленного пользователем
     */
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Метод для загрузки текстовой информации о животном в БД из отчета пользователя
     */
    public void uploadText(String report, TrusteesReports trusteesReports) {

        trusteesReports.setText(report);

    }
}
