package org.example.util.hash;

/**
 * HashStrategy - интерфейс для алгоритмов хеширования
 */
public interface HashStrategy {
    String hash(String toHash);
}
