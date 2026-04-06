package use_case.result;

import entity.StockInterface;

/**
 * Output Data for the Signup Use Case.
 */
public class ResultOutputData {

    private final StockInterface stock;

    private final boolean useCaseFailed;

    public ResultOutputData(StockInterface stock, boolean useCaseFailed) {
        this.stock = stock;
        this.useCaseFailed = useCaseFailed;
    }

    public StockInterface getStock() {
        return stock;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
