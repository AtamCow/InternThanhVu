package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String checkedAmountDate = LocalDate.now().plusDays(7).format(formatter);

        System.out.println(checkedAmountDate);
    }
}