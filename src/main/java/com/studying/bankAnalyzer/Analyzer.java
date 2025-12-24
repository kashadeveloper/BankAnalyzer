package com.studying.bankAnalyzer;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Analyzer {
    public static List<Transaction> ReadTransactions(String fileName) {
        try {
            var csvToBean = new CsvToBeanBuilder<Transaction>(new FileReader(fileName))
                    .withType(Transaction.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withThrowExceptions(false)
                    .build();
            List<Transaction> transactions = csvToBean.parse();
            csvToBean.getCapturedExceptions().forEach(e -> {
                System.err.println("------------------------------------------------");
                System.err.println("Ошибка в строке: " + e.getLineNumber());
                System.err.println("Тип ошибки: " + e.getClass().getSimpleName());
                if (e.getCause() != null) {
                    System.err.println("Причина: " + e.getCause());
                } else {
                    System.err.println("Сообщение: " + e.getMessage());
                }
            });
            return transactions;
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return List.of();
    }
}
