package com.studying.bankAnalyzer;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @CsvBindByName(column = "date")
    @CsvDate("yyyy-MM-dd")
    public LocalDate Date;

    @CsvBindByName(column = "category")
    public String Category;

    @CsvBindByName(column = "amount")
    public double Amount;

    @Override
    public String toString() {
        var newLine = System.lineSeparator();
        return "========== ТРАНЗАКЦИЯ ==========" + newLine +
                "Дата: " + Date + newLine +
                "Категория: " + Category + newLine +
                "Сумма: " + Amount  + "$" + newLine +
                "========= ТРАНЗАКЦИЯ ==========" + newLine;
    }

    public String toString(double currencyRate, String currency) {
        var newLine = System.lineSeparator();
        return "========== ТРАНЗАКЦИЯ ==========" + newLine +
                "Дата: " + Date + newLine +
                "Категория: " + Category + newLine +
                "Сумма: " + (Amount * currencyRate) + " " + currency + newLine +
                "========= ТРАНЗАКЦИЯ ==========" + newLine;
    }
}
