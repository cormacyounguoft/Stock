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

    @Override
    public StockInterface search(String ticker) throws IOException, InterruptedException {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE"
                + "&symbol=" + ticker
                + "&apikey=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();

        String key = "\"05. price\": \"";
        int start = body.indexOf(key);
        if (start == -1) {
            throw new IOException("Could not find price for ticker: " + ticker);
        }

        start += key.length();
        int end = body.indexOf("\"", start);
        if (end == -1) {
            throw new IOException("Invalid API response for ticker: " + ticker);
        }

        String priceString = body.substring(start, end);
        float price;

        try {
            price = Float.parseFloat(priceString);
        } catch (NumberFormatException e) {
            throw new IOException("Could not parse price for ticker: " + ticker, e);
        }
        return new StockFactory().create(ticker, price);
    }
}