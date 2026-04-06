package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * Controller for the Signup Use Case.
 */
public class SearchController {

    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    /**
     */
    public void execute(String ticker) {
        final SearchInputData searchInputData = new SearchInputData(ticker);

        searchInteractor.execute(searchInputData);
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToResultView() {
        searchInteractor.switchToResultView();
    }
}
