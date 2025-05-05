package org.example.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization - описание класса.
 *
 * @version 1.0
 */

public class Localization {
    private static ResourceBundle bundle;
    private static Locale locale;

    public static void setLocale(Locale newLocale) {
        bundle = ResourceBundle.getBundle("lab_localization", newLocale);
        locale = newLocale;
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static Locale getLocale() {
        return locale;
    }

    public static String getLocaleString() {
        String language = locale.getLanguage();

        return switch (language) {
            case "en" -> "English";
            case "ru" -> "Русский";
            case "el" -> "Ελληνικά";
            case "sr" -> "Српски";
            case "es" -> "Español (HN)";
            default -> "Unknown Language";
        };
    }
}
