package entity;

import java.util.Map;

/**
 * The representation of stock details in the program.
 */
public interface StockInterface {

    String getTicker();

    Map<String, String> getDetails();
}
