package use_case.result;

import entity.StockInterface;

import java.io.IOException;

/**
 * The Signup Interactor.
 */
public class ResultInteractor implements ResultInputBoundary {
    private final ResultOutputBoundary searchPresenter;

    public ResultInteractor(ResultOutputBoundary resultOutputBoundary) {
        this.searchPresenter = resultOutputBoundary;
    }

    @Override
    public void execute(ResultInputData resultInputData) {
        final ResultOutputData resultOutputData = new ResultOutputData(resultInputData.getStock(), false);
        searchPresenter.prepareSuccessView(resultOutputData);
        }

    @Override
    public void switchToSearchView() {
        searchPresenter.switchToSearchView();
    }
}
