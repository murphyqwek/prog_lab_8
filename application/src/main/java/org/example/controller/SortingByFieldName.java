package org.example.controller;

import org.example.base.model.MusicBand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SortingByFieldName - описание класса.
 *
 * @version 1.0
 */

public class SortingByFieldName {
    public static List<MusicBand> sortByFieldName(List<MusicBand> list, String fieldName) {
        Comparator<MusicBand> comparator = switch (fieldName.toLowerCase()) {
            case "id" -> Comparator.comparingInt(MusicBand::getId);
            case "name" -> Comparator.comparing(MusicBand::getName);
            case "x" -> Comparator.comparingInt(MusicBand::getX);
            case "y" -> Comparator.comparingLong(MusicBand::getY);
            case "creation date" -> Comparator.comparing(MusicBand::getCreationDate);
            case "albums count" -> Comparator.comparingLong(MusicBand::getAlbumsCount);
            case "number of participants" -> Comparator.comparingLong(MusicBand::getNumberOfParticipants);
            case "genre" -> Comparator.comparing(MusicBand::getGenreString);
            case "sales" -> Comparator.comparingDouble(MusicBand::getSales);
            default -> throw new IllegalArgumentException("Unknown sort field: " + fieldName);
        };

        return list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
