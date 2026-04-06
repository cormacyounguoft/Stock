package use_case.search;

import entity.Stock;
import entity.StockInterface;

/**
 * Output Data for the Signup Use Case.
 */
public class SearchOutputData {

    private final StockInterface stock;

    private final boolean useCaseFailed;

    public SearchOutputData(StockInterface stock, boolean useCaseFailed) {
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
