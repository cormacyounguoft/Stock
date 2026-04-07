package view;

import interface_adapter.result.ResultController;
import interface_adapter.result.ResultState;
import interface_adapter.result.ResultViewModel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * The view for the search result use case.
 */
public class ResultView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "result";
    private final ResultViewModel resultViewModel;

    private final JButton cancel;
    private final JTextArea resultDetails;
    private ResultController resultController;

    public ResultView(ResultViewModel resultViewModel) {
        this.resultViewModel = resultViewModel;
        this.resultViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(ResultViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultDetails = new JTextArea(20, 40);
        resultDetails.setEditable(false);
        resultDetails.setLineWrap(true);
        resultDetails.setWrapStyleWord(true);

        final JScrollPane scrollPane = new JScrollPane(resultDetails);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        this.add(scrollPane);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final ResultState state = (ResultState) evt.getNewValue();
            resultDetails.setText(formatDetails(state.getDetails()));
            resultDetails.setCaretPosition(0);
        }
    }

    private String formatDetails(Map<String, String> details) {
        final StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : details.entrySet()) {
            builder.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }

        return builder.toString().trim();
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
