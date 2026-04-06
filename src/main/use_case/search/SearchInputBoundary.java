package use_case.search;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface SearchInputBoundary {

    /**
     * Executes the search use case.
     * @param searchInputData the input data
     */
    void execute(SearchInputData searchInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToResultView();
}
