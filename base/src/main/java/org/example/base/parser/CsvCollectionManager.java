package org.example.base.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.example.base.exception.DeserializationException;
import org.example.base.model.Coordinates;
import org.example.base.model.Label;
import org.example.base.model.MusicBand;
import org.example.base.model.MusicGenre;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * CsvManager - класс для работы с файлом коллекции
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class CsvCollectionManager {
    public String filepath;

    /**
     * Конструктор класса
     * @param filename путь к файлу сохранения
     * @throws FileNotFoundException
     */
    public CsvCollectionManager(String filename) {
        this.filepath = filename;
    }

    /**
     * Метод для сохранения коллекции в файл
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveCollection(Collection<MusicBand> collection) throws FileNotFoundException, IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.filepath), "UTF-8");
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id", "name", "x", "y", "date", "participants", "albums", "genre", "sales"))) {

            for(var mb : collection) {
                csvPrinter.printRecord(mb.getId(),
                        mb.getName(),
                        mb.getCoordinates().getX(),
                        mb.getCoordinates().getY(),
                        parseDateToString(mb.getCreationDate()),
                        mb.getNumberOfParticipants(),
                        mb.getAlbumsCount(),
                        mb.getGenre().toString(),
                        String.format("%.8f", mb.getLabel().getSales()));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для загрузки коллекции из файла
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Collection<MusicBand> uploadCollection() throws FileNotFoundException, IOException, DeserializationException {
        Collection<MusicBand> collection = new LinkedList<>();

        try (FileReader reader = new FileReader(this.filepath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                MusicBand mb = parseRecord(record);
                collection.add(mb);
            }
        } catch (ParseException e) {
            throw new DeserializationException("Невозможно распарсить файл коллекции");
        }

        return collection;
    }

    /**
     * Метод для парсинга строки CSV
     */
    private MusicBand parseRecord(CSVRecord record) throws ParseException, DeserializationException {
        //"id", "name", "x", "y", "date", "participants", "albums", "genre", "sales"
        int id = Integer.parseInt(record.get("id"));
        String name = record.get("name");
        Integer x = Integer.valueOf(record.get("x"));
        long y = Long.parseLong(record.get("y"));
        Date date = parseStringDate(record.get("date"));
        Long numberOfParticipants = Long.valueOf(record.get("participants"));
        long albumCounts = Long.parseLong(record.get("albums"));
        MusicGenre genre = MusicGenre.valueOf(record.get("genre"));
        double sales = Double.parseDouble(record.get("sales").replace(',', '.').replace(";", "").replace("\n", ""));

        try {
            Label label = new Label(sales);
            Coordinates coordinates = new Coordinates(x, y);
            MusicBand musicBand = new MusicBand(id, name, coordinates, date, numberOfParticipants, albumCounts, genre, label);
            return musicBand;
        }
        catch (Exception e) {
            throw new DeserializationException("Файл коллекции поврежден");
        }

    }

    /**
     * Метод для получения даты из строки
     */
    private static Date parseStringDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.parse(date);
    }

    /**
     * Метод для получения строки формата dd-MM-yyyy из даты
     */
    private static String parseDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }
}
