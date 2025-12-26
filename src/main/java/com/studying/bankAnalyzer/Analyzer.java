package com.studying.bankAnalyzer;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Analyzer {
    public static List<Transaction> ReadTransactions(String fileName) throws FileNotFoundException {
        var csvToBean = new CsvToBeanBuilder<Transaction>(new FileReader(fileName))
                .withType(Transaction.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withThrowExceptions(false)
                .build();
        List<Transaction> transactions = csvToBean.parse();
        csvToBean.getCapturedExceptions().forEach(e -> {
            System.err.println("------------------------------------------------");
            System.err.println("Line: " + e.getLineNumber());
            System.err.println("Error class: " + e.getClass().getSimpleName());
            if (e.getCause() != null) {
                System.err.println("Caused by: " + e.getCause());
            } else {
                System.err.println("Message: " + e.getMessage());
            }
        });
        return transactions;
    }
}
