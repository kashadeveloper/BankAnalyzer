package com.studying.bankAnalyzer;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            System.err.println("Ошибка в строке: " + e.getLineNumber());
            System.err.println("Тип ошибки: " + e.getClass().getSimpleName());
            if (e.getCause() != null) {
                System.err.println("Причина: " + e.getCause());
            } else {
                System.err.println("Сообщение: " + e.getMessage());
            }
        });
        return transactions;
    }

    public static HashMap<String, Integer> groupByPeriod(List<Transaction> transactions) {
        var result = new HashMap<String, Integer>();
        for (var transaction : transactions) {
            var period = transaction.Date.getMonth().toString() + " " + transaction.Date.getYear();
            if (result.containsKey(period)) {
                result.put(period, result.get(period) + 1);
            } else {
                result.put(period, 1);
            }
        }
        return result;
    }

    public static Map<String, Long> groupByCategory(List<Transaction> transactions) {
        return transactions.stream().collect(Collectors.groupingBy(Transaction::getCategory, Collectors.counting()));
    }

    public static Transaction getMostExpensive(List<Transaction> transactions) {
        return transactions.stream().max(Comparator.comparing(Transaction::getAmount)).get();
    }
}
