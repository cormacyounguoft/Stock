package view;

import interface_adapter.result.ResultController;
import interface_adapter.result.ResultState;
import interface_adapter.result.ResultViewModel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The View for the Search Result Use Case.
 */
public class ResultView extends JPanel implements ActionListener, PropertyChangeListener{

    private final String viewName = "result";
    private final ResultViewModel resultViewModel;

    private final JButton cancel;
    private final JLabel result;
    private ResultController resultController;

    public ResultView(ResultViewModel resultViewModel) {
        this.resultViewModel = resultViewModel;
        this.resultViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(ResultViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel resultInfo = new JLabel(ResultViewModel.SEARCH_LABEL);
        result = new JLabel();

        final JPanel buttons = new JPanel();
        cancel = new JButton(ResultViewModel.CANCEL_LABEL);
        buttons.add(cancel);

        cancel.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        resultController.switchToSearchView();
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(result);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ResultState state = (ResultState) evt.getNewValue();
            result.setText(state.getPrice().toString());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(ResultController controller) {
        this.resultController = controller;
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }
}