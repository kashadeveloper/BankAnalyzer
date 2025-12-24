package com.studying.bankAnalyzer;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите путь до файла: ");
        var filePath = sc.nextLine();
        try {
            var transactions = Analyzer.ReadTransactions(filePath);
            var currencyRate = GetUSDRateFor("BYN");
            System.out.println("Текущий курс: " + currencyRate);
            System.out.println("========= ТРАНЗАКЦИИ ==========\n");
            for (var transaction : transactions) {
                System.out.println(transaction.toString(currencyRate, "BYN"));
            }
            System.out.println("Итого: " + transactions.stream().mapToDouble(Transaction::getAmount).sum() * currencyRate + " BYN");
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
            System.out.println(Analyzer.getMostExpensive(transactions).toString(currencyRate, "BYN"));
        } catch (Exception e) {
            System.err.print("Ошибка выполнения программы: ");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static double GetUSDRateFor(String currency) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://openexchangerates.org/api/latest.json?app_id=63570bbdd7ac421d8b56479b3dd0973d"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // First way
        var responseString = response.body();
        var mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responseString);
        JsonNode ratesTree = node.get("rates");
        JsonNode rate = ratesTree.get(currency);
        return rate.asDouble(0);
    }
}