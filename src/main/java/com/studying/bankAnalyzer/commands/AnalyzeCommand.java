package com.studying.bankAnalyzer.commands;

import com.studying.bankAnalyzer.Analyzer;
import com.studying.bankAnalyzer.Transaction;
import com.studying.bankAnalyzer.utils.HelperFunctions;
import com.studying.bankAnalyzer.utils.converters.DateConverter;
import org.fusesource.jansi.Ansi;
import picocli.CommandLine;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.fusesource.jansi.Ansi.ansi;

@CommandLine.Command(name = "analyze", mixinStandardHelpOptions = true, description = "Analyze CSV of transactions")
public class AnalyzeCommand implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", arity = "1", description = "Path to file with transactions", paramLabel = "<filePath>")
    private String filePath;

    @CommandLine.Option(names = {"--stats", "-s"}, description = "Show stats")
    private boolean showStats;

    @CommandLine.Option(names = {"--category", "-c"}, description = "Filter transactions by category")
    private String category;

    @CommandLine.Option(names = {"-min", "--min"}, description = "Filter transactions by minimum value")
    private Double min;

    @CommandLine.Option(names = {"-max", "--max"}, description = "Filter transactions by max value")
    private Double max;

    @CommandLine.Option(names = {"--date", "-d"}, description = "Filter transactions by date (dd-MM-yyyy)", converter = DateConverter.class)
    private LocalDate date;

    @CommandLine.Option(names = {"--from"}, description = "Filter transactions from date (dd-MM-yyyy)", converter = DateConverter.class)
    private LocalDate from;

    @CommandLine.Option(names = {"--to"}, description = "Filter transactions to some date (dd-MM-yyyy)", converter = DateConverter.class)
    private LocalDate to;

    @Override
    public Integer call() throws Exception {
        final List<Transaction> transactions = Analyzer.ReadTransactions(filePath);
        if (IsFiltersApplied()) {
            var filteredTransactions = getFilteredTransactions(transactions);
            var filteredTransactionsList = filteredTransactions.toList();
            PrintResult(filteredTransactionsList);

            if (showStats && !filteredTransactionsList.isEmpty()) {
                PrintStats(filteredTransactionsList);
            }
        } else {
            PrintResult(transactions);
            PrintStats(transactions);
        }

        return 0;
    }

    private Stream<Transaction> getFilteredTransactions(List<Transaction> transactions) {
        var filteredTransactions = transactions.stream();

        if (category != null) {
            filteredTransactions = filteredTransactions
                    .filter(transaction -> transaction.getCategory().equalsIgnoreCase(category));
        }
        if (min != null) {
            filteredTransactions = filteredTransactions
                    .filter(x -> x.getAmount() >= min);
        }
        if (max != null) {
            filteredTransactions = filteredTransactions
                    .filter(x -> x.getAmount() <= max);
        }
        if (date != null) {
            filteredTransactions = filteredTransactions.filter(x -> x.getDate().equals(date));
        }

        if (from != null) {
            filteredTransactions = filteredTransactions.filter(x -> x.getDate().isAfter(from));
        }

        if (to != null) {
            filteredTransactions = filteredTransactions.filter(x -> x.getDate().isBefore(to));
        }

        return filteredTransactions;
    }

    private boolean IsFiltersApplied() {
        return category != null || min != null || date != null || from != null || to != null;
    }

    private void PrintResult(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println(ansi().bold().fg(Ansi.Color.RED).a("No transactions found!"));
            return;
        }
        System.out.println(ansi().render("@|bold,yellow Date\t\t\tAmount\t\tCategory\n|@"));
        transactions.forEach(x -> System.out.println(x.toColoredString()));
        System.out.println(ansi().render(String.format("\n@|bold,yellow Total: %.2f$|@",
                transactions.stream().mapToDouble(t -> t.Amount).sum())));
    }

    private void PrintStats(List<Transaction> transactions) {
        DoubleSummaryStatistics statistics = transactions.stream().mapToDouble(t -> t.Amount).summaryStatistics();
        Map<String, Long> categoryCounts = transactions.stream().collect(Collectors.groupingBy(
                Transaction::getCategory,
                Collectors.counting()));
        String mostPopularCategory = categoryCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("None");
        System.out.println();
        System.out.println(ansi().fg(Ansi.Color.GREEN).bold().a("STATS:").reset());
        System.out.println(ansi().bold().render("Average amount: " + String.format("@|%s %.2f$|@",
                HelperFunctions.getColorByAmount(statistics.getAverage()),
                statistics.getAverage())));
        System.out.println(ansi().bold().render("Minimum amount: " + String.format("@|%s %.2f$|@",
                HelperFunctions.getColorByAmount(statistics.getMin()),
                statistics.getMin())));
        System.out.println(ansi().bold().render("Maximum amount: " + String.format("@|%s %.2f$|@",
                HelperFunctions.getColorByAmount(statistics.getMax()),
                statistics.getMax())));
        System.out.println(ansi().bold().render("Total transactions: @|cyan " + statistics.getCount() + "|@"));
        System.out.println(ansi().bold().render("Most popular category: @|underline,yellow " + mostPopularCategory + "|@"));
    }
}
