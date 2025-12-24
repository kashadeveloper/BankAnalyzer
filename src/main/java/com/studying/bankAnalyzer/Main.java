package com.studying.bankAnalyzer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите путь до файла: ");
        var filePath = sc.nextLine();
        try {
            var transactions = Analyzer.ReadTransactions(filePath);
            System.out.println("========= ТРАНЗАКЦИИ ==========\n");
            for (var transaction : transactions) {
                System.out.println(transaction);
            }
            System.out.println("Итого: " + transactions.stream().mapToDouble(Transaction::getAmount).sum());
            System.out.println("Количество транзакций: " + transactions.size());

            System.out.println("\n========= ТРАНЗАКЦИИ ==========");

            System.out.println("\n=========== ТРАТЫ ПО МЕСЯЦАМ =========");
            var groupedInfo = Analyzer.groupByPeriod(transactions);

            for (var group : groupedInfo.keySet()) {
                System.out.println(group + ": " + groupedInfo.get(group));
            }
            System.out.println("============= ТРАТЫ ПО МЕСЯЦАМ ==========");

            System.out.println("\n============= КАТЕГОРИИ ТРАТ =============");

            var categories = Analyzer.groupByCategory(transactions);
            for(var category : categories.keySet()) {
                System.out.println(category + ": " + categories.get(category));
            }

            System.out.println("============= КАТЕГОРИИ ТРАТ =============");

            System.out.println("\n\nСАМАЯ ДОРОГАЯ ТРАТА: ");
            System.out.println(Analyzer.getMostExpensive(transactions));
        } catch (Exception e) {
            System.err.print("Ошибка выполнения программы: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}