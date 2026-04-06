package use_case.result;

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface ResultInputBoundary {

    /**
     * Executes the search use case.
     * @param resultInputData the input data
     */
    void execute(ResultInputData resultInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToSearchView();
}
