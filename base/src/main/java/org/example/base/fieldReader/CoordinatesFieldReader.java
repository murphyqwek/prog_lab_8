package org.example.base.fieldReader;

import org.example.base.exception.InvalidArgumentsException;
import org.example.base.iomanager.IOManager;
import org.example.base.model.Coordinates;

/**
 * CoordinatesFieldReader - класс для создания объекта Coordinates через пользовательский ввод.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class CoordinatesFieldReader {
    private final IOManager ioManager;

    /**
     * Конструктор класса
     * @param ioManager класс для работы с вводом-выводом
     */
    public CoordinatesFieldReader(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    /**
     * Метод для получения объекта Label с использованием пользовательских данных
     * @throws InterruptedException если пользователь прервал ввод
     */
    public Coordinates executeCoordinates() throws InterruptedException {
        ioManager.writeLine("Ввод значений полей Coordinates");
        while (true) {
            IntegerFieldReader xReader = new IntegerFieldReader(this.ioManager, "Введите x: ");
            LongFieldReader yReader = new LongFieldReader(this.ioManager, "Введите y: ");

            var x = xReader.executeInteger();
            var y = yReader.executeLong();

            try {
                Coordinates coordinates = new Coordinates(x, y);
                return coordinates;
            }
            catch(InvalidArgumentsException e) {
                ioManager.writeError(e.getMessage());
            }
        }
    }
}
