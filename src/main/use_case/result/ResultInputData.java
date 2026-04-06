package use_case.result;

import entity.StockInterface;

/**
 * The Input Data for the Signup Use Case.
 */
public class ResultInputData {

    private final StockInterface stock;

    public ResultInputData(StockInterface stock) {
        this.stock = stock;
    }

    public StockInterface getStock() {
        return stock;
    }
}
