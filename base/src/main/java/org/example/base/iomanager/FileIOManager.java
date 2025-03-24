package org.example.base.iomanager;

import org.example.base.file.FileReaderIterator;

/**
 * FileIOManager - класс для чтения из файлов для команд.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class FileIOManager implements IOManager {
    private final FileReaderIterator fileReaderIterator;


    /**
     * Конструктор класс
     * @param iterator итератор файла
     */
    public FileIOManager(FileReaderIterator iterator) {
        this.fileReaderIterator = iterator;
    }

    /**
     * Записает в поток
     *
     * @param obj записываемое значение
     */
    @Override
    public void write(Object obj) {
        System.out.print(obj);
    }

    /**
     * Записывает в поток, добавляя в конец знак переноса строки
     *
     * @param obj записываемое значение
     */
    @Override
    public void writeLine(Object obj) {
        System.out.println(obj);
    }

    /**
     * Записывает в поток ошибок
     *
     * @param obj записываемое значение
     */
    @Override
    public void writeError(Object obj) {
        System.err.println(obj);
    }

    /**
     * Чтение строки из потока
     *
     * @return строку из потока
     */
    @Override
    public String readLine() {
        return this.fileReaderIterator.next();
    }

    /**
     * Проверяет наличие данных для чтения
     *
     * @return true, если в потоке есть данные для чтения, и false, если данных нет
     */
    @Override
    public boolean hasNext() {
        return this.fileReaderIterator.hasNext();
    }
}
