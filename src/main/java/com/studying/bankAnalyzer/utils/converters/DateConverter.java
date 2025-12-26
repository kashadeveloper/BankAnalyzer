package com.studying.bankAnalyzer.utils.converters;

import picocli.CommandLine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverter implements CommandLine.ITypeConverter<LocalDate> {
    @Override
    public LocalDate convert(String s) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(s, formatter);
        } catch (DateTimeParseException e) {
            throw new Exception("Invalid date format. Date format must be 'dd-MM-yyyy'");
        }
    }
}
