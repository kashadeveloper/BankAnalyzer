package com.studying.bankAnalyzer.commands;

import com.studying.bankAnalyzer.dtos.ExchangeRatesResponse;
import com.studying.bankAnalyzer.http.ExchangeRatesApi;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;

@CommandLine.Command(name = "currencies", description = "All currencies list")
public class CurrenciesList implements Callable<Integer> {
    @CommandLine.Option(names = {"--a", "--appid"}, required = true, description = "App ID for openexchangerates.org API")
    private String appId;

    @CommandLine.Option(names = "-c", arity = "1", description = "Currency name")
    private String currency;

    @Override
    public Integer call() {
        ExchangeRatesApi api = new ExchangeRatesApi(appId);
        final ExchangeRatesResponse response = api.GetCurrencyRates();
        var rates = response.rates();

        if (currency != null) {
            System.out.println(ansi().render(String.format("@|green,bold 1 USD|@ = @|blue,bold,underline %f %s|@", rates.get(currency.toUpperCase()), currency.toUpperCase())));
        } else {
            System.out.println(ansi().render("@|bold,underline,green 1 USD|@ in different currencies:"));
            for (String currency : rates.keySet()) {
                System.out.println(ansi().render(String.format("@|cyan %s|@: @|green,bold %f|@", currency, rates.get(currency))));
            }
        }
        return 1;
    }
}
