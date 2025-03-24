package org.example.base.file;

import java.io.*;
import java.util.List;

/**
 * FileManager - класс для взаимодействия с файлом.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class FileManager {
    private String path;
    private FileReader fileReader;

    /**
     * Конструктор класса
     * @param path путь до файла
     */
    public FileManager(String path) throws IOException {
        this.path = path;
        File file = new File(path);
        file.createNewFile();
    }

    /**
     * Метод для чтения всего файла
     * @return
     */
    public String readAll() throws IOException {
        try (FileReader reader = new FileReader(this.path)) {
            String output = "";
            int c;
            while ((c = reader.read()) != -1) {
                output += (char) c;
            }

            return output;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Метод для перезаписи файла
     */
    public void writeAllToFile(String text) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.path), "UTF-8")) {
            writer.write(text);
        }
    }

    /**
     * Метод для записи множества строк в файл
     */
    public void writeAllToFile(List<String> lines) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.path), "UTF-8")) {
            for(var line : lines) {
                writer.write(line + "\n");
            }
        }
    }
}
