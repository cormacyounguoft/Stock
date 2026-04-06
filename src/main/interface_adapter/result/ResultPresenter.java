package interface_adapter.result;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;
import use_case.result.ResultOutputBoundary;
import use_case.result.ResultOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class ResultPresenter implements ResultOutputBoundary {

    private final ResultViewModel resultViewModel;
    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;

    public ResultPresenter(ViewManagerModel viewManagerModel,
                           ResultViewModel resultViewModel,
                           SearchViewModel searchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.resultViewModel = resultViewModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(ResultOutputData response) {
        resultViewModel.firePropertyChanged();

        viewManagerModel.setState(resultViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }

    @Override
    public void switchToSearchView() {
        viewManagerModel.setState(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
