package org.example.base.iomanager;

import java.util.Scanner;

/**
 * StandartIOManager - класс со стандартной реализацией IOManager. Вывод происходит в System.out, а ввод через передаваемых Scanner
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class StandartIOManager implements IOManager {
    private Scanner scanner;

    /**
     * Конструктор класса
     */
    public StandartIOManager() {
        scanner = new Scanner(System.in);
    }

    /**
     * Конструктор класса
     * @param scanner класс для чтения из потока ввода-вывода
     */
    public StandartIOManager(Scanner scanner) {
        this.scanner = scanner;
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
        System.out.println(obj + "\n");
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
        return scanner.nextLine();
    }

    /**
     * Проверяет наличие данных для чтения
     *
     * @return true, если в потоке есть данные для чтения, и false, если данных нет
     */
    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }
}
