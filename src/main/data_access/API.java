package data_access;

import entity.Stock;
import entity.StockFactory;
import entity.StockInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API implements SearchDataAccessInterface {
    private static final String API_KEY = "3S750UJW025W2JEO";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String PRICE_KEY = "\"05. price\": \"";
    private static final String NOTE_KEY = "\"Note\": \"";
    private static final String INFORMATION_KEY = "\"Information\": \"";
    private static final String ERROR_MESSAGE_KEY = "\"Error Message\": \"";

    @Override
    public StockInterface search(String ticker) throws IOException, InterruptedException {
        final String normalizedTicker = ticker == null ? "" : ticker.trim().toUpperCase();
        if (normalizedTicker.isEmpty()) {
            throw new IOException("Please enter a ticker.");
        }

        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE"
                + "&symbol=" + normalizedTicker
                + "&apikey=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Stock API request failed with status code " + response.statusCode() + ".");
        }

        final String body = response.body();
        final String apiError = extractMessage(body, ERROR_MESSAGE_KEY);
        if (apiError != null) {
            throw new IOException("Ticker not found: " + normalizedTicker + ".");
        }

        final String apiNote = extractMessage(body, NOTE_KEY);
        if (apiNote != null) {
            throw new IOException("Stock API limit reached. Please try again shortly.");
        }

        final String apiInformation = extractMessage(body, INFORMATION_KEY);
        if (apiInformation != null) {
            throw new IOException(apiInformation);
        }

        int start = body.indexOf(PRICE_KEY);
        if (start == -1) {
            throw new IOException("No price data was returned for ticker: " + normalizedTicker + ".");
        }

        start += PRICE_KEY.length();
        int end = body.indexOf("\"", start);
        if (end == -1) {
            throw new IOException("Invalid stock API response for ticker: " + normalizedTicker + ".");
        }

        String priceString = body.substring(start, end);
        float price;

        try {
            price = Float.parseFloat(priceString);
        } catch (NumberFormatException e) {
            throw new IOException("Could not parse price for ticker: " + normalizedTicker + ".", e);
        }

        return new StockFactory().create(normalizedTicker, price);
    }

    private String extractMessage(String body, String key) {
        final int start = body.indexOf(key);
        if (start == -1) {
            return null;
        }

        final int valueStart = start + key.length();
        final int valueEnd = body.indexOf("\"", valueStart);
        if (valueEnd == -1) {
            return null;
        }

        return body.substring(valueStart, valueEnd);
    }
}
