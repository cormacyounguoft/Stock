package interface_adapter.result;

/**
 * The state for the Search View Model.
 */
public class ResultState {

    private String ticker;
    private Float price;

    public String getTicker() {
        return ticker;
    }

    public Float getPrice() {
        return price;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}