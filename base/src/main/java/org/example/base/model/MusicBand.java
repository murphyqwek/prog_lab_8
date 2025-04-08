package org.example.base.model;

import org.example.base.exception.InvalidArgumentsException;

import java.io.Serializable;
import java.util.Date;

/**
 * MusicBand - описание модели музыкальной группы
 *
 * @author Starikov Arseny
 * @version 1.0
 */

public class MusicBand implements Comparable<MusicBand>, Serializable {
    private int id;
    private final String name;
    private final Coordinates coordinates;
    private Date creationDate;
    private final Long numberofParticipants;
    private final long albumsCount;
    private final MusicGenre genre;
    private Label label;

    /**
     * Конструктор класса
     * @param id id группы
     * @param name название группы
     * @param coordinates координаты
     * @param numberofParticipants количество участников
     * @param albumsCount количество альбомов
     * @param genre музыкальный жанр
     * @param label лейбел
     */
    public MusicBand(int id, String name, Coordinates coordinates, Long numberofParticipants, long albumsCount, MusicGenre genre, Label label) {
        this(id, name, coordinates, new Date(), numberofParticipants, albumsCount, genre, label);
    }

    /**
     * Конструктор класса
     * @param id id группы
     * @param name название группы
     * @param coordinates координаты
     * @param creationDate дата создания
     * @param numberofParticipants количество участников
     * @param albumsCount количество альбомов
     * @param genre музкыкальный жанр
     * @param label лейбел
     */
    public MusicBand(int id, String name, Coordinates coordinates, Date creationDate, Long numberofParticipants, long albumsCount, MusicGenre genre, Label label) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberofParticipants = numberofParticipants;
        this.albumsCount = albumsCount;
        this.genre = genre;
        this.label = label;

        if(!isValid()) {
            throw new InvalidArgumentsException("Неверные аргументы для создания объекта класса MusicBand");
        }
    }

    /**
     * Метод для получения поля id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Метод для получения названия группы
     */
    public String getName() {
        return this.name;
    }

    /**
     * Метод для получения координат
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Метод для получения даты создания
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Метод для получения количества участников
     */
    public Long getNumberOfParticipants() {
        return this.numberofParticipants;
    }

    /**
     * Метод для получения количество альбомов
     */
    public long getAlbumsCount() {
        return this.albumsCount;
    }

    /**
     * Метод для получения музыкального жанра
     */
    public MusicGenre getGenre() {
        return this.genre;
    }

    /**
     * Метод для получения лейбла
     */
    public Label getLabel() {
        return this.label;
    }

    /**
     * Метод для установки id
     * @param id - положительное число
     */
    public void setId(int id) {
        if(id <= 0) {
            throw new IllegalArgumentException("id must be positive integer");
        }

        this.id = id;
    }

    public void setCreationDate(Date creationDate) {
        if(creationDate == null) {
            throw new IllegalArgumentException("creationDate must not be null");
        }

        this.creationDate = creationDate;
    }

    /**
     * Метод для проверки валидации объекта
     *
     * @return true, если объект проходит валидацию, false, если не проходит валидацию
     */
    public boolean isValid() {
        if (this.id <= 0) {
            return false;
        }

        if(this.name == null || this.name.isEmpty()) {
            return false;
        }

        if (this.coordinates == null || !this.coordinates.isValid()) {
            return false;
        }

        if (this.creationDate == null) {
            return false;
        }

        if(this.numberofParticipants == null || this.numberofParticipants < 0) {
            return false;
        }

        if(this.albumsCount < 0) {
            return false;
        }

        if(this.genre == null) {
            return false;
        }

        if(this.label == null) {
            return false;
        }

        return true;
    }

    /**
     * Метод для сравнения двух объектов MusicBand
     * @param o объект, с которым сравниваем
     */
    @Override
    public int compareTo(MusicBand o) {
        return (int) (this.albumsCount - o.albumsCount);
    }

    /**
     * Метод для получения строкового значения объекта
     */
    @Override
    public String toString() {
        return String.format("MusicBand: {id: \"%d\", name: \"%s\", coordinates:%s, creationDate: \"%s\", number of participants: \"%s\", albums count: \"%d\", genre: \"%s\", label: \"%s\"}", this.id, this.name, this.coordinates.toString(), this.creationDate.toString(), this.numberofParticipants.toString(), this.albumsCount, this.genre.name(), this.label.toString());
    }

    /**
     * Метод для получения хешкода объекта
     */
    @Override
    public int hashCode() {
        return (int) (this.id | this.name.hashCode() | this.coordinates.hashCode() | this.creationDate.hashCode() | this.numberofParticipants | this.albumsCount | this.genre.hashCode() | this.label.hashCode());
    }

    /**
     * Метод для сравнения двух объектов
     * @param obj
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj == this) {
            return true;
        }

        if(!(obj instanceof MusicBand other)) {
            return false;
        }

        return this.id == other.id &&
                this.name.equals(other.name) &&
                this.label.equals(other.label) &&
                this.genre.equals(other.genre) &&
                this.albumsCount == other.albumsCount &&
                this.numberofParticipants.equals(other.numberofParticipants) &&
                this.creationDate.equals(other.creationDate) &&
                this.coordinates.equals(other.coordinates);
    }
}
