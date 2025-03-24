package org.example.base.fieldReader;

import org.example.base.exception.InvalidArgumentsException;
import org.example.base.iomanager.IOManager;
import org.example.base.model.Coordinates;
import org.example.base.model.Label;
import org.example.base.model.MusicBand;
import org.example.base.model.MusicGenre;

/**
 * MusicBandFieldReader - класс для создания объекта MusicBand через пользовательский ввод.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class MusicBandFieldReader {
    private final IOManager ioManager;

    /**
     * Конструктор класса
     * @param ioManager класс для работы с вводом-выводом
     */
    public MusicBandFieldReader(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    /**
     * Метод для считывания полей класса MusicBand и его создание
     * @param id нового объекта MusicBand
     * @throws InterruptedException если пользователь прервал ввод
     */
    public MusicBand executeMusicBand(int id) throws InterruptedException {
        while(true) {
            String name = new StringFieldReader(this.ioManager, "Введите имя: ").executeString();
            Coordinates coord = new CoordinatesFieldReader(this.ioManager).executeCoordinates();
            Long numberOfParticipants = new LongWrapperFieldReader(this.ioManager, "Введите количество участников: ").executeLong();
            long albumsCount = new LongWrapperFieldReader(this.ioManager, "Введите количество альбомов").executeLong();
            MusicGenre musicGenre = new MusicGenreFieldReader(this.ioManager).executeMusicGenre();
            Label label = new LabelFieldReader(this.ioManager).executeLabel();

            try {
                MusicBand musicBand = new MusicBand(id, name, coord, numberOfParticipants, albumsCount, musicGenre, label);
                return musicBand;
            }
            catch (InvalidArgumentsException e) {
                ioManager.writeError(e.getMessage());
            }
        }
    }
}
