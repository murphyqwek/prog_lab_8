package org.example.base.iomanager;

/**
 * EmptyIOManager - описание класса.
 *
 * @version 1.0
 */

public class EmptyIOManager implements IOManager {
    /**
     * Записает в поток
     *
     * @param obj записываемое значение
     */
    @Override
    public void write(Object obj) {

    }

    /**
     * Записывает в поток, добавляя в конец знак переноса строки
     *
     * @param obj записываемое значение
     */
    @Override
    public void writeLine(Object obj) {

    }

    /**
     * Записывает в поток ошибок
     *
     * @param obj записываемое значение
     */
    @Override
    public void writeError(Object obj) {

    }

    /**
     * Чтение строки из потока
     *
     * @return строку из потока
     */
    @Override
    public String readLine() {
        return "";
    }

    /**
     * Проверяет наличие данных для чтения
     *
     * @return true, если в потоке есть данные для чтения, и false, если данных нет
     */
    @Override
    public boolean hasNext() {
        return false;
    }
}
