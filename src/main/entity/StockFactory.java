package entity;

import java.util.Map;

/**
 * Factory for creating Stock objects.
 */
public class StockFactory implements StockFactoryInterface {

    @Override
    public StockInterface create(String ticker, Map<String, String> details) {
        return new Stock(ticker, details);
    }
}
