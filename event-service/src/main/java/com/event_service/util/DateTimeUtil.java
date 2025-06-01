package com.event_service.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_12_HOUR_FORMATTER = DateTimeFormatter.ofPattern("h:mm a");
    private static final DateTimeFormatter TIME_24_HOUR_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Parsear fecha desde String
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy-MM-dd");
        }
    }

    // Parsear hora desde String (acepta formato 12 o 24 horas)
    public static LocalTime parseTime(String timeString) {
        try {
            // Intentar con formato 12 horas primero
            if (timeString.toUpperCase().contains("AM") || timeString.toUpperCase().contains("PM")) {
                return LocalTime.parse(timeString.toUpperCase(), TIME_12_HOUR_FORMATTER);
            } else {
                // Intentar con formato 24 horas
                return LocalTime.parse(timeString, TIME_24_HOUR_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm o h:mm AM/PM");
        }
    }

    // Formatear fecha a String
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    // Formatear hora a formato 12 horas
    public static String formatTimeToAmPm(LocalTime time) {
        return time.format(TIME_12_HOUR_FORMATTER);
    }

    // Formatear hora a formato 24 horas
    public static String formatTimeTo24Hour(LocalTime time) {
        return time.format(TIME_24_HOUR_FORMATTER);
    }
}