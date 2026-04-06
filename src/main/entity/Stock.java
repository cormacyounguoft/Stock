package entity;

/**
 * A simple implementation of the StockInterface interface.
 */
public class Stock implements StockInterface {

    private final String ticker;
    private final Float price;

    public Stock(String ticker, Float price) {
        this.ticker = ticker;
        this.price = price;
    }

    @Override
    public String getTicker() {
        return ticker;
    }

    @Override
    public Float getPrice() {
        return price;
    }
}
