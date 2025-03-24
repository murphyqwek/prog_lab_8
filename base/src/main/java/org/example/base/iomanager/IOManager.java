package org.example.base.iomanager;

/**
 * IOManager - интерфейс для управления потоком ввода-вывода
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public interface IOManager {
    /**
     * Записает в поток
     * @param obj записываемое значение
     */
    public void write(Object obj);

    /**
     * Записывает в поток, добавляя в конец знак переноса строки
     * @param obj записываемое значение
     */
    public void writeLine(Object obj);

    /**
     * Записывает в поток ошибок
     * @param obj записываемое значение
     */
    public void writeError(Object obj);

    /**
     * Чтение строки из потока
     * @return строку из потока
     */
    public String readLine();

    /**
     * Проверяет наличие данных для чтения
     * @return true, если в потоке есть данные для чтения, и false, если данных нет
     */
    public boolean hasNext();
}
