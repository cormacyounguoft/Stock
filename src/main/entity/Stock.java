package entity;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A stock details entity backed by a display-ordered field map.
 */
public class Stock implements StockInterface {

    private final String ticker;
    private final Map<String, String> details;

    public Stock(String ticker, Map<String, String> details) {
        this.ticker = ticker;
        this.details = Collections.unmodifiableMap(new LinkedHashMap<>(details));
    }

    @Override
    public String getTicker() {
        return ticker;
    }

    @Override
    public Map<String, String> getDetails() {
        return details;
    }
}
