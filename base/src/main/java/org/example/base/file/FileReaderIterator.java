package org.example.base.file;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * FileReaderIterator - класс итератор для построчного чтения файла.
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class FileReaderIterator implements Iterator<String> {
    private final FileReader reader;
    private String lineBuffer = "";
    private int currentChar;
    private boolean endOfFile = false;

    /**
     * Конструктор класса
     * @param filePath путь до файла
     * @throws IOException если не удалось открыть файл
     */
    public FileReaderIterator(String filePath) throws IOException {
        this.reader = new FileReader(filePath);
        this.currentChar = reader.read();
        if (currentChar == -1) {
            endOfFile = true;
        }
    }

    @Override
    public boolean hasNext() {
        return !endOfFile;
    }

    @Override
    public String next() {
        if (endOfFile) {
            throw new NoSuchElementException();
        }

        lineBuffer = "";

        try {
            while (currentChar != -1 && currentChar != '\n') {
                if (currentChar != '\r') {
                    lineBuffer += (char) currentChar;
                }
                currentChar = reader.read();
            }

            if (currentChar == '\n') {
                currentChar = reader.read();
            }

            if (currentChar == -1) {
                endOfFile = true;
            }

            return lineBuffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException ignored) { }
    }
}