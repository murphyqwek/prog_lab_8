package org.example.base.fieldReader;

import org.example.base.exception.InvalidArgumentsException;
import org.example.base.iomanager.IOManager;
import org.example.base.model.Label;

/**
 * LabelFieldReader - класс для считывания полей класса Label.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class LabelFieldReader {
    private final IOManager ioManager;

    /**
     * Конструктор класса
     * @param ioManager      класс для работы с потоками ввода-вывода
     */
    public LabelFieldReader(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    /**
     * Метод для получения объекта Label с использованием пользовательских данных
     * @throws InterruptedException если пользователь решил прервать ввод
     */
    public Label executeLabel() throws InterruptedException {
        while(true) {
            DoubleFieldReader salesReader = new DoubleFieldReader(this.ioManager, "Введите поле sales (пример: 4.5; 3,2): ");
            double sales = salesReader.executeDouble();

            try {
                Label label = new Label(sales);
                return label;
            }
            catch (InvalidArgumentsException e) {
                this.ioManager.writeError(e.getMessage());
            }
        }
    }
}
