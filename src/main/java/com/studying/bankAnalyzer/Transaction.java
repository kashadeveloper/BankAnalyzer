package com.studying.bankAnalyzer;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.studying.bankAnalyzer.utils.HelperFunctions.getColorByAmount;
import static org.fusesource.jansi.Ansi.ansi;

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

    public String toColoredString() {
        return ansi().render(String.format("@|white %s|@\t\t@|underline,bold,%s %.2f$|@\t\t@|bold,cyan %s|@",
                Date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                getColorByAmount(Amount),
                Amount,
                Category
        )).toString();
    }

    @Override
    public String toString() {
        String formattedDate = Date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return formattedDate + "\t\t" + String.format("%.2f", Amount) + "\t\t" + Category;
    }
}
