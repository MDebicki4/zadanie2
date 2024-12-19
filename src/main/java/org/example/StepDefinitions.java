package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StepDefinitions {

    private final NbpApiClient nbpApiClient = new NbpApiClient();
    private JSONArray exchangeRates;

    @Given("Pobierz kursy walut")
    public void pobierzKursyWalut() {
        exchangeRates = nbpApiClient.getExchangeRates();
        assertNotNull("Kursy walut nie zostały pobrane", exchangeRates);
    }

    @Then("Wyświetl kurs dla waluty o kodzie: {string}")
    public void wyswietlKursDlaWalutyOKodzie(String currencyCode) {
        boolean found = false;
        for (int i = 0; i < exchangeRates.length(); i++) {
            JSONObject rate = exchangeRates.getJSONObject(i);
            if (rate.getString("code").equals(currencyCode)) {
                System.out.println("Kurs dla waluty " + currencyCode + ": " + rate.getDouble("mid"));
                found = true;
                break;
            }
        }
        assertTrue("Nie znaleziono waluty o kodzie: " + currencyCode, found);
    }

    @Then("Wyświetl kurs dla waluty o nazwie: {string}")
    public void wyswietlKursDlaWalutyONazwie(String currencyName) {
        boolean found = false;
        for (int i = 0; i < exchangeRates.length(); i++) {
            JSONObject rate = exchangeRates.getJSONObject(i);
            if (rate.getString("currency").equalsIgnoreCase(currencyName)) {
                System.out.println("Kurs dla waluty " + currencyName + ": " + rate.getDouble("mid"));
                found = true;
                break;
            }
        }
        assertTrue("Nie znaleziono waluty o nazwie: " + currencyName, found);
    }

    @Then("Wyświetl waluty o kursie powyżej: {double}")
    public void wyswietlWalutyOPowyzejKursu(double minRate) {
        boolean found = false;
        for (int i = 0; i < exchangeRates.length(); i++) {
            JSONObject rate = exchangeRates.getJSONObject(i);
            if (rate.getDouble("mid") > minRate) {
                System.out.println("Waluta " + rate.getString("currency") + " (" + rate.getString("code") + ") ma kurs: " + rate.getDouble("mid"));
                found = true;
            }
        }
        assertTrue("Nie znaleziono walut o kursie powyżej " + minRate, found);
    }

    @Then("Wyświetl waluty o kursie poniżej: {double}")
    public void wyswietlWalutyOPonizejKursu(double maxRate) {
        boolean found = false;
        for (int i = 0; i < exchangeRates.length(); i++) {
            JSONObject rate = exchangeRates.getJSONObject(i);
            if (rate.getDouble("mid") < maxRate) {
                System.out.println("Waluta " + rate.getString("currency") + " (" + rate.getString("code") + ") ma kurs: " + rate.getDouble("mid"));
                found = true;
            }
        }
        assertTrue("Nie znaleziono walut o kursie poniżej " + maxRate, found);
    }
}