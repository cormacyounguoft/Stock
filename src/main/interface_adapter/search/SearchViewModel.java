package interface_adapter.search;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Signup View.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public static final String TITLE_LABEL = "Search View";
    public static final String SEARCH_LABEL = "Search Ticker";

    public static final String SEARCH_BUTTON_LABEL = "Search";

    public SearchViewModel() {
        super("search");
        setState(new SearchState());
    }

}
