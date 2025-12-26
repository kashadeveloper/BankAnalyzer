package com.studying.bankAnalyzer;

import com.github.lalyos.jfiglet.FigletFont;
import com.studying.bankAnalyzer.commands.CurrenciesList;
import com.studying.bankAnalyzer.commands.AnalyzeCommand;
import lombok.SneakyThrows;
import picocli.CommandLine;

import static org.fusesource.jansi.Ansi.ansi;

@CommandLine.Command(
        name = "bank-analyzer-cli",
        version = "0.0.1",
        description = "CLI for analyzing transactions",
        mixinStandardHelpOptions = true,
        subcommands = {CurrenciesList.class, AnalyzeCommand.class}
)
public class BankAnalyzerCli implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        String art = FigletFont.convertOneLine("BankAnalyzer v. 1.0");
        System.out.println(ansi().render("@|green,bold,blink_slow" + art + "|@"));
        System.out.println(ansi().render("@|bold,underline,yellow Study CLI for transaction parsing|@"));
        System.out.println(ansi().newline().render("Use @|bold,yellow --help|@ to see the list of commands"));
    }
}
