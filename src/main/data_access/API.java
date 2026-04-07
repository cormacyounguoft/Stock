package data_access;

import entity.StockFactory;
import entity.StockInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class API implements SearchDataAccessInterface {
    private static final String API_KEY = "d7a4j2hr01qn9i7jkr10d7a4j2hr01qn9i7jkr1g";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    @Override
    public StockInterface search(String ticker) throws IOException, InterruptedException {
        final String normalizedTicker = ticker == null ? "" : ticker.trim().toUpperCase();
        if (normalizedTicker.isEmpty()) {
            throw new IOException("Please enter a ticker.");
        }

        final String encodedTicker = URLEncoder.encode(normalizedTicker, StandardCharsets.UTF_8);
        final String quoteBody = fetch("/quote?symbol=" + encodedTicker + "&token=" + API_KEY);

        final Map<String, String> details = buildDetails(normalizedTicker, quoteBody);
        return new StockFactory().create(normalizedTicker, details);
    }

    private String fetch(String path) throws IOException, InterruptedException {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://finnhub.io/api/v1" + path))
                .GET()
                .build();

        final HttpResponse<String> response =
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401 || response.statusCode() == 403) {
            throw new IOException("Finnhub authorization failed. Check the API token.");
        }
        if (response.statusCode() == 429) {
            throw new IOException("Finnhub API limit reached. Please try again shortly.");
        }
        if (response.statusCode() != 200) {
            throw new IOException("Finnhub request failed with status code " + response.statusCode() + ".");
        }

        return response.body();
    }

    private Map<String, String> buildDetails(String ticker, String quoteBody) throws IOException {
        final LinkedHashMap<String, String> details = new LinkedHashMap<>();

        final String currentPrice = extractNumber(quoteBody, "c");
        final String highPrice = extractNumber(quoteBody, "h");
        final String lowPrice = extractNumber(quoteBody, "l");
        final String openPrice = extractNumber(quoteBody, "o");
        final String previousClose = extractNumber(quoteBody, "pc");
        final String change = extractNumber(quoteBody, "d");
        final String percentChange = extractNumber(quoteBody, "dp");

        if (isMissingQuote(currentPrice, highPrice, lowPrice, openPrice, previousClose)) {
            throw new IOException("Ticker not found: " + ticker + ".");
        }

        put(details, "ticker", ticker);
        put(details, "currentPrice", currentPrice);
        put(details, "change", change);
        put(details, "percentChange", percentChange);
        put(details, "high", highPrice);
        put(details, "low", lowPrice);
        put(details, "previousClose", previousClose);

        return details;
    }

    private boolean isMissingQuote(String currentPrice, String highPrice, String lowPrice,
                                   String openPrice, String previousClose) {
        return isZeroOrMissing(currentPrice)
                && isZeroOrMissing(highPrice)
                && isZeroOrMissing(lowPrice)
                && isZeroOrMissing(openPrice)
                && isZeroOrMissing(previousClose);
    }

    private boolean isZeroOrMissing(String value) {
        return value == null || value.isBlank() || "0".equals(value) || "0.0".equals(value);
    }

    private void put(Map<String, String> details, String label, String value) {
        if (value != null && !value.isBlank()) {
            details.put(label, value);
        }
    }

    private String extractNumber(String body, String key) {
        final Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*(-?[0-9]+(?:\\.[0-9]+)?)");
        final Matcher matcher = pattern.matcher(body);
        if (!matcher.find()) {
            return null;
        }

        return matcher.group(1);
    }
}
