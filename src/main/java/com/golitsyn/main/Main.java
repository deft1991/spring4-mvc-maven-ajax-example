package com.golitsyn.main;

import java.util.Currency;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        System.out.println(Currency.getInstance("USD").getSymbol(Locale.US));
        System.out.println(Currency.getInstance("USD").getDefaultFractionDigits());
        System.out.println(Currency.getInstance("USD").getNumericCode());

    }
}
