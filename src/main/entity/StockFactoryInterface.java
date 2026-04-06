package entity;

/**
 * Factory for creating users.
 */
public interface StockFactoryInterface {

    StockInterface create(String ticker, float price);

}
