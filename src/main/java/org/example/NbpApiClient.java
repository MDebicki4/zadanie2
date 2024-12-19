package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class NbpApiClient {

    private static final String NBP_URL = "http://api.nbp.pl/api/exchangerates/tables/A";

    public JSONArray getExchangeRates() {
        Response response = RestAssured.get(NBP_URL);
        System.out.println("Response Code: " + response.getStatusCode());

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to get data from NBP API");
        }

        String jsonResponse = response.asString();
        JSONArray table = new JSONArray(jsonResponse);
        JSONObject tableData = table.getJSONObject(0);
        return tableData.getJSONArray("rates");
    }
}