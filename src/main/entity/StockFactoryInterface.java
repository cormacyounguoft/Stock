package entity;

import java.util.Map;

/**
 * Factory for creating stock detail entities.
 */
public interface StockFactoryInterface {

    StockInterface create(String ticker, Map<String, String> details);
}
