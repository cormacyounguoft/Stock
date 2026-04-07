package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import interface_adapter.result.ResultState;
import interface_adapter.result.ResultViewModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SearchPresenter implements SearchOutputBoundary {
    private static final String TICKER_KEY = "ticker";
    private static final String CURRENT_PRICE_KEY = "currentPrice";
    private static final String CHANGE_KEY = "change";
    private static final String PERCENT_CHANGE_KEY = "percentChange";
    private static final String PREVIOUS_CLOSE_KEY = "previousClose";

    private final SearchViewModel searchViewModel;
    private final ResultViewModel resultViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(ViewManagerModel viewManagerModel,
                           SearchViewModel searchViewModel,
                           ResultViewModel resultViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.resultViewModel = resultViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData response) {
        // On success, switch to the login view.
        final SearchState searchState = searchViewModel.getState();
        searchState.setSearchError(null);

        final ResultState resultState = resultViewModel.getState();
        resultState.setTicker(response.getStock().getTicker());
        resultState.setDetails(formatResultDetails(response));
        this.resultViewModel.setState(resultState);
        resultViewModel.firePropertyChanged();

        viewManagerModel.setState(resultViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setSearchError(error);
        searchViewModel.firePropertyChanged();
    }

    @Override
    public void switchToResultView() {
        viewManagerModel.setState(resultViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    private java.util.Map<String, String> formatResultDetails(SearchOutputData response) {
        final java.util.Map<String, String> stockDetails = response.getStock().getDetails();
        final java.util.LinkedHashMap<String, String> formattedDetails = new java.util.LinkedHashMap<>();

        put(formattedDetails, "Ticker", stockDetails.get(TICKER_KEY));
        put(formattedDetails, "Current Price", stockDetails.get(CURRENT_PRICE_KEY));
        put(formattedDetails, "Change", stockDetails.get(CHANGE_KEY));

        final String percentChange = stockDetails.get(PERCENT_CHANGE_KEY);
        if (percentChange != null && !percentChange.isBlank()) {
            put(formattedDetails, "Percent Change", percentChange + "%");
        }

        put(formattedDetails, "Previous Close", stockDetails.get(PREVIOUS_CLOSE_KEY));
        return formattedDetails;
    }

    private void put(java.util.Map<String, String> details, String label, String value) {
        if (value != null && !value.isBlank()) {
            details.put(label, value);
        }
    }
}
