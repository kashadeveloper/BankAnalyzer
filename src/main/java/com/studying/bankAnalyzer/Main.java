package com.studying.bankAnalyzer;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var transactions = Analyzer.ReadTransactions("data.csv");
        System.out.println("========= ТРАНЗАКЦИИ ==========\n");
        for (var transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println("Итого: " + transactions.stream().mapToDouble(Transaction::getAmount).sum());
        System.out.println("Количество транзакций: " + transactions.size());

        System.out.println("\n========= ТРАНЗАКЦИИ ==========");

        System.out.println("\n=========== ТРАТЫ ПО МЕСЯЦАМ =========");
        var groupedInfo = groupByPeriod(transactions);

        for (var group : groupedInfo.keySet()) {
            System.out.println(group + ": " + groupedInfo.get(group));
        }
        System.out.println("============= ТРАТЫ ПО МЕСЯЦАМ ==========");

        System.out.println("\n============= КАТЕГОРИИ ТРАТ =============");

        var categories = groupByCategory(transactions);
        for(var category : categories.keySet()) {
            System.out.println(category + ": " + categories.get(category));
        }

        System.out.println("============= КАТЕГОРИИ ТРАТ =============");

        System.out.println("\n\nСАМАЯ ДОРОГАЯ ТРАТА: ");
        System.out.println(getMostExpensive(transactions));
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