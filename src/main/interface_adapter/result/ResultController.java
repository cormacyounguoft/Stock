package interface_adapter.result;

import entity.StockInterface;
import use_case.result.ResultInputBoundary;
import use_case.result.ResultInputData;

/**
 * Controller for the Signup Use Case.
 */
public class ResultController {

    private final ResultInputBoundary resultInteractor;

    public ResultController(ResultInputBoundary resultInteractor) {
        this.resultInteractor = resultInteractor;
    }

    /**
     */
    public void execute(StockInterface stock) {
        final ResultInputData resultInputData = new ResultInputData(stock);

        resultInteractor.execute(resultInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToSearchView() {
        resultInteractor.switchToSearchView();
    }
}
