package com.ai.tremoo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CountryList {

    public static List<String> getCountryNames() {
        String[] countryCodes = Locale.getISOCountries();
        List<String> countryNames = new ArrayList<>();

        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            countryNames.add(locale.getDisplayCountry());
        }

        // Sort the country names alphabetically
        Collections.sort(countryNames);

        return countryNames;
    }

    public static void main(String[] args) {
        List<String> countries = getCountryNames();
        for (String country : countries) {
            System.out.println(country);
        }
    }
}
