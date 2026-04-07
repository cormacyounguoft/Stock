package interface_adapter.result;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The state for the result view model.
 */
public class ResultState {

    private String ticker;
    private Map<String, String> details = new LinkedHashMap<>();

    public String getTicker() {
        return ticker;
    }

    public Map<String, String> getDetails() {
        return new LinkedHashMap<>(details);
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setDetails(Map<String, String> details) {
        this.details = new LinkedHashMap<>(details);
    }
}
