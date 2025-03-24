package org.example.base.fieldReader;

import org.example.base.iomanager.IOManager;

/**
 * StringFieldReader - класс для считывания полей типа Integer
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public class IntegerFieldReader extends BaseFieldReader {
    /**
     * Конструктор класса
     * @param ioManager класс для работы с потоком ввода-вывода
     * @param fieldAskString строка, которая запрашивает ввод
     */
    public IntegerFieldReader(IOManager ioManager, String fieldAskString) {
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
        if(input.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод для получения значения Integer, вводимого пользователем
     * @return значение типа Integer
     * @throws InterruptedException если пользователь решил прервать ввод
     */
    public Integer executeInteger() throws InterruptedException {
        var out = this.getFieldString();

        return Integer.valueOf(out);
    }
}
