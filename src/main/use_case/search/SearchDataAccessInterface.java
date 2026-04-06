package use_case.search;

import entity.Stock;
import entity.StockInterface;

import java.io.IOException;

/**
 * DAO for the Signup Use Case.
 */
public interface SearchDataAccessInterface {

    StockInterface search(String ticker) throws IOException, InterruptedException;
}
