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
        resultState.setDetails(response.getStock().getDetails());
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
}
