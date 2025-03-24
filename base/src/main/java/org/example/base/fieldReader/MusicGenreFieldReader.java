package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;
import org.example.base.model.MusicGenre;

/**
 * MusicGenreFieldReader - описание класса.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class MusicGenreFieldReader extends BaseFieldReader {
    /**
     * Конструктор класса
     *
     * @param ioManager      класс для работы с потоками ввода-вывода
     */
    public MusicGenreFieldReader(IOManager ioManager) {
        super(ioManager, "");
        this.setFieldAskString(generateFieldAskString());
    }

    /**
     * Метод для генерации строки для запрашивания ввода
     * @return строку для запрашивания ввода
     */
    private String generateFieldAskString() {
        String fieldAskString = "Введите тип genre (";

        for(var field : MusicGenre.values()) {
            fieldAskString = fieldAskString + field.toString() + ", ";
        }

        fieldAskString = fieldAskString.substring(0, fieldAskString.length() - 2);
        fieldAskString += "): ";

        return fieldAskString;
    }

    public MusicGenreFieldReader(IOManager ioManager, String fieldAskString) {
        super(ioManager, fieldAskString);
    }

    /**
     * Метод для проверки вводимого значения на тип
     *
     * @param input вводимое поле
     * @return true, если вводимое поле прошло проверку на тип, и false в противном случае
     */
    @Override
    public boolean isInputCorrect(String input) {
        input = input.toUpperCase();

        try {
            MusicGenre.valueOf(input);
            return true;
        }
        catch(IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Метод для получения поля типа MusicGenre
     * @return поле типа MusicGenre
     * @throws InterruptedException если пользователь решил прервать ввод
     */
    public MusicGenre executeMusicGenre() throws InterruptedException {
        var out = this.getFieldString().toUpperCase();;
        return MusicGenre.valueOf(out);
    }
}
