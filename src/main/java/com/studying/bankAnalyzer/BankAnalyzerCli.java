package com.studying.bankAnalyzer;

import com.studying.bankAnalyzer.commands.AnalyzeCommand;
import com.studying.bankAnalyzer.commands.CurrenciesList;
import lombok.SneakyThrows;
import org.fusesource.jansi.Ansi;
import picocli.CommandLine;

import static com.studying.bankAnalyzer.utils.HelperFunctions.GetFigletByOS;
import static com.studying.bankAnalyzer.utils.HelperFunctions.GetPlatformOS;
import static org.fusesource.jansi.Ansi.ansi;

@CommandLine.Command(
        name = "bank-analyzer-cli",
        version = "1.0.0",
        description = "CLI for analyzing transactions",
        mixinStandardHelpOptions = true,
        subcommands = {CurrenciesList.class, AnalyzeCommand.class}
)
public class BankAnalyzerCli implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        var figlet = GetFigletByOS(GetPlatformOS());
        System.out.println(ansi().render(figlet));
        System.out.println(ansi().render("@|bold,underline,yellow Study CLI for transaction parsing|@"));
        System.out.println(ansi().newline().render("Use @|bold,yellow --help|@ to see the list of commands"));
    }
}
