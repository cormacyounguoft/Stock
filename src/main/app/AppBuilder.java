package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.API;
import interface_adapter.ViewManagerModel;

import interface_adapter.result.ResultController;
import interface_adapter.result.ResultPresenter;
import interface_adapter.result.ResultViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;

import use_case.result.ResultInputBoundary;
import use_case.result.ResultInteractor;
import use_case.result.ResultOutputBoundary;
import use_case.search.SearchDataAccessInterface;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.ResultView;
import view.SearchView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
//    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    private final SearchDataAccessInterface searchDataAccessObject = new API();

    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private ResultViewModel resultViewModel;
    private ResultView resultView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel);
        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addResultView() {
        resultViewModel = new ResultViewModel();
        resultView = new ResultView(resultViewModel);
        cardPanel.add(resultView, resultView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(viewManagerModel,
                searchViewModel, resultViewModel);
        final SearchInputBoundary userSignupInteractor = new SearchInteractor(
                searchDataAccessObject, searchOutputBoundary);

        final SearchController controller = new SearchController(userSignupInteractor);
        searchView.setSearchController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addResultUseCase() {
        final ResultOutputBoundary resultOutputBoundary = new ResultPresenter(viewManagerModel,
                resultViewModel, searchViewModel);
        final ResultInputBoundary resultInteractor = new ResultInteractor(resultOutputBoundary);

        final ResultController resultController = new ResultController(resultInteractor);
        resultView.setResultController(resultController);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SearchView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(searchView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
