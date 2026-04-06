package interface_adapter.result;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class ResultViewModel extends ViewModel<ResultState> {

    public static final String TITLE_LABEL = "Result View";
    public static final String SEARCH_LABEL = "Search Ticker";
    public static final String CANCEL_LABEL = "Cancel";

    public ResultViewModel() {
        super("result");
        setState(new ResultState());
    }

}
