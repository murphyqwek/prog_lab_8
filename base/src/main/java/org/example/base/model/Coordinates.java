package org.example.base.model;

import org.example.base.exception.InvalidArgumentsException;

import java.io.Serializable;

/**
 * CollectionManager - координаты объекта класса MusicBand
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public class Coordinates implements Serializable {
    private Integer x;
    private long y;

    /**
     * Конструктор класса
     * @param x
     * @param y
     */
    public Coordinates(Integer x, long y) {
        this.x = x;
        this.y = y;

        if(!isValid()) {
            throw new InvalidArgumentsException("Координата x должно быть больше -390, не null, координата y должна быть больше -371");
        }
    }

    /**
     * Метод получения поля x
     */
    public Integer getX() {
        return x;
    }

    /**
     * Метод получения поля y
     */
    public long getY() {
        return y;
    }

    /**
     * Метод для проверки валидации объекта
     *
     * @return true, если объект проходит валидацию, false, если не проходит валидацию
     */
    public boolean isValid() {
        if(this.x == null || this.x <= -390) {
            return false;
        }

        if(this.y <= -371) {
            return false;
        }

        return true;
    }

    /**
     * Метод для получения строкового значения объекта
     */
    @Override
    public String toString() {
        return "Coordinates {x=" + x + ", y=" + y + "}";
    }

    /**
     * Метод для получения хешкода значения объекта
     */
    @Override
    public int hashCode() {
        return (int) (this.x.hashCode() | this.y);
    }

    /**
     * Метод для сравнения двух объектов
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj == this) {
            return true;
        }

        if(!(obj instanceof Coordinates other)) {
            return false;
        }

        return other.x.equals(this.x) && other.y == this.y;
    }
}
