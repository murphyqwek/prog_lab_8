package org.example.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.ElementNotFoundException;
import org.example.base.exception.IdAlreadyExistsException;
import org.example.base.model.MusicBand;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * CollectionManager - класс для управления коллекцией
 *
 * @author Starikov Arseny
 * @version 1.0
 */
public class CollectionManager {
    private final Logger logger = LogManager.getRootLogger();
    private final CopyOnWriteArrayList<MusicBandWithOwner> collection;
    private int lastId;
    private final Date collectionCreationDate;

    /**
     * Конструктор класса
     */
    public CollectionManager() {
        this.collection = new CopyOnWriteArrayList<>();
        this.lastId = 1;
        this.collectionCreationDate = new Date();
    }

    /**
     * Метод для получения даты создания коллекции
     * @return дату создания класса
     */
    public Date getCollectionCreationDate() {
        return collectionCreationDate;
    }

    /**
     * Методы для получения коллекции
     * @return неизменяемую коллекцию
     */
    public List<MusicBand> getCollection() {
        return this.collection.stream().map(MusicBandWithOwner::getMusicBand).toList();
    }

    /**
     * Метод для проверки, принадлежит ли пользователю элемент коллекции
     * @param id
     * @param owner
     * @return
     */
    public boolean checkOwner(int id, String owner) {
        var mb = getMusicBandWithOwnerById(id);
        if(mb == null) {
            throw new IllegalArgumentException();
        }

        return mb.getOwner().equals(owner);
    }

    /**
     * Метод для получения элемента коллекции по полю id
     * @param id поле класса MusicBand
     * @return элемент коллекции с соответствующим полем id
     */
    public MusicBand getMusicBandById(int id) {
        try {
            return collection.stream().filter((mb) -> mb.getMusicBand().getId() == id).findFirst().get().getMusicBand();
        }catch (ElementNotFoundException e) {
            return null;
        }
    }

    private MusicBandWithOwner getMusicBandWithOwnerById(int id) {
        try {
            return collection.stream().filter((mb) -> mb.getMusicBand().getId() == id).findFirst().get();
        }catch (ElementNotFoundException e) {
            return null;
        }
    }

    /**
     * Метод для генерации id
     * @return id, который нет ещё ни у какого элемента в коллекции
     */
    public int generateId() {
        return this.lastId + 1;
    }

    /**
     * Метод для добавления нового элемента в коллекцию
     * @param mb новый элемент типа MusicBand
     * @throws IdAlreadyExistsException если в коллекции уже есть элемент с таким же полем id
     */
    public void addNewMusicBand(MusicBand mb, String login) throws IdAlreadyExistsException {
        if(containsId(mb.getId())) {
            throw new IdAlreadyExistsException(mb.getId());
        }

        this.lastId = Math.max(this.lastId, mb.getId());
        this.collection.add(new MusicBandWithOwner(mb, login));
        sort();
        logger.info("В коллекцию добавлен новый элемент");
    }

    /**
     * Метод для удаления элемента с заданным id
     * @param id индикатор удаляемого элемента
     * @throws ElementNotFoundException если в коллекции нет элемента с заданным индикатором
     */
    public void removeMusicBandById(int id) throws ElementNotFoundException {
        var mb = this.getMusicBandById(id);

        if(mb == null) {
            throw new ElementNotFoundException("MusicBand с id " + id + " не найден");
        }

        removeMusicBand(mb);
    }

    /**
     * Метод для удаления элемента из коллекции
     * @param mb удаляемый элемент
     */
    private void removeMusicBand(MusicBand mb) {
        var elementToRemove = this.collection.stream().filter((m) -> m.getMusicBand().equals(mb)).findFirst().get();
        this.collection.remove(elementToRemove);
        sort();
        logger.info("Из коллекции был удален элемент: " + mb);
    }

    /**
     * Метод для очищения коллекции
     */
    public void clear() {
        this.collection.clear();
        logger.info("Коллекция была очищена");
    }

    /**
     * Метод для обновления элемента коллекции
     * @param newMusicBand новый элемент коллекции. Старый элемент коллекции находится по id нового элемента
     */
    public void updateMusicBand(MusicBand newMusicBand) {
        var musicBandWithOnwer = this.collection.stream().filter((m) -> m.getMusicBand().getId() == newMusicBand.getId()).findFirst();

        musicBandWithOnwer.ifPresent(m -> m.setMusicBand(newMusicBand));

        if(musicBandWithOnwer.isEmpty()) {
            throw new ElementNotFoundException("Элемент не был найден в коллекции");
        }

        logger.info("Элемент с id=" + newMusicBand.getId() + " был заменен");
    }

    /**
     * Возвращает строку с типом коллекции
     * @return
     */
    public String getCollectionType() {
        return collection.getClass().getSimpleName();
    }

    /**
     * Метод для проверки id
     * @param id проверяемое значение
     * @return true, если в коллекции уже есть элемент с заданным id, false в противном случае
     */
    public boolean containsId(int id) {
        return collection.stream().anyMatch((mb) -> mb.getMusicBand().getId() == id);
    }

    public void sort() {
        this.collection.sort(new Comparator<MusicBandWithOwner>() {
            @Override
            public int compare(MusicBandWithOwner o1, MusicBandWithOwner o2) {
                return o1.getMusicBand().compareTo(o2.getMusicBand());
            }
        });
    }
}
