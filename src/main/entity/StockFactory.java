package entity;

/**
 * Factory for creating Stock objects.
 */
public class StockFactory implements StockFactoryInterface {

    @Override
    public StockInterface create(String ticker, float price) {
        return new Stock(ticker, price);
    }
}
