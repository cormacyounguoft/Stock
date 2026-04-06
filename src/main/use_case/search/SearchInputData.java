package use_case.search;

/**
 * The Input Data for the Signup Use Case.
 */
public class SearchInputData {

    private final String ticker;

    public SearchInputData(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }
}
