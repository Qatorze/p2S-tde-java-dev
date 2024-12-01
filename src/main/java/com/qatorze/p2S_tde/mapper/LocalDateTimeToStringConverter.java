package com.qatorze.p2S_tde.mapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Convertisseur JPA qui convertit un objet {@link LocalDateTime} 
 * en une chaîne formatée au format "yyyy-MM-dd HH:mm:ss" pour l'enregistrement dans la base de données,
 * et inversement, convertit une chaîne lue dans la base de données en un objet {@link LocalDateTime}.
 *
 * Ce convertisseur est appliqué automatiquement à tous les champs de type {@link LocalDateTime}
 * grâce à l'annotation {@link Converter} avec le paramètre {@code autoApply = true}.
 */
@Converter(autoApply = true)
public class LocalDateTimeToStringConverter implements AttributeConverter<LocalDateTime, String> {

    /**
     * Formatter pour définir le format "yyyy-MM-dd HH:mm:ss",
     * utilisé pour convertir {@link LocalDateTime} en chaîne et inversement.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /**
     * Convertit un objet {@link LocalDateTime} en une chaîne formatée 
     * avant de l'enregistrer dans la base de données.
     *
     * @param attribute l'objet {@link LocalDateTime} à convertir.
     *                  Peut être {@code null}.
     * @return une chaîne représentant la date et l'heure au format "yyyy-MM-dd HH:mm:ss",
     *         ou {@code null} si l'attribut fourni est {@code null}.
     */
    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return (attribute == null) ? null : attribute.format(FORMATTER);
    }

    /**
     * Convertit une chaîne lue depuis la base de données en un objet {@link LocalDateTime}.
     *
     * @param dbData la chaîne lue depuis la base de données, formatée comme "yyyy-MM-dd HH:mm:ss".
     *               Peut être {@code null}.
     * @return un objet {@link LocalDateTime} représentant la date et l'heure,
     *         ou {@code null} si les données fournies sont {@code null}.
     * @throws java.time.format.DateTimeParseException si le format de la chaîne est invalide.
     */
    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : LocalDateTime.parse(dbData, FORMATTER);
    }
}
