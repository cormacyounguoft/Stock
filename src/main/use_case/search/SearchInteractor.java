package use_case.search;

import entity.Stock;
import entity.StockFactory;
import entity.StockInterface;

import java.io.IOException;

/**
 * The Signup Interactor.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface searchDataAccessInterface;
    private final SearchOutputBoundary searchPresenter;
    private final StockFactory stockFactory;

    public SearchInteractor(SearchDataAccessInterface signupDataAccessInterface,
                            SearchOutputBoundary searchOutputBoundary,
                            StockFactory stockFactory) {
        this.searchDataAccessInterface = signupDataAccessInterface;
        this.searchPresenter = searchOutputBoundary;
        this.stockFactory = stockFactory;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        try {
            final StockInterface stock = searchDataAccessInterface.search(searchInputData.getTicker());
            SearchOutputData searchOutputData = new SearchOutputData(stock, false);
            searchPresenter.prepareSuccessView(searchOutputData);
        }
        catch (IOException | InterruptedException exception) {
            searchPresenter.prepareFailView("Ticker not found");
        }
    }

    @Override
    public void switchToResultView() {

    }
}
