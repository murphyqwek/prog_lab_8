package org.example.base.model;

import org.example.base.exception.InvalidArgumentsException;

import java.io.Serializable;

/**
 * Label - лейбел объекта класса MusicBand
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public class Label implements Comparable<Label>, Serializable {
    private double sales;

    /**
     * Конструктор класса
     * @param sales продажи
     */
    public Label(double sales) {
        this.sales = sales;

        if(!isValid()) {
            throw new InvalidArgumentsException("Значение sales должно быть больше 0");
        }
    }

    /**
     * Метод для получения продаж
     */
    public double getSales() {
        return sales;
    }

    /**
     * Метод для сравнения двух классов
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Label other)) return false;

        if(this == other) return true;

        return this.sales == other.getSales();
    }

    /**
     * Метод для получения хешкода значения объекта
     */
    @Override
    public int hashCode() {
        return (int) Math.round(this.sales * 100000);
    }

    /**
     * Метод для получения строкого значения объекта
     */
    @Override
    public String toString() {
        return "Label: sales " + this.sales;
    }

    /**
     * Метод для сравнения двух объектов Label
     * @param o the object to be compared.
     */
    @Override
    public int compareTo(Label o) {
        return Double.compare(this.sales, o.sales);
    }

    /**
     * Метод для проверки валидации объекта
     *
     * @return true, если объект проходит валидацию, false, если не проходит валидацию
     */
    public boolean isValid() {
        return sales > 0;
    }
}
