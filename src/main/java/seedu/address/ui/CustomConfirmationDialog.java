package seedu.address.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A custom dialog for confirmation with OK and Cancel buttons.
 */
public class CustomConfirmationDialog extends Dialog<ButtonType> {

    /**
     * Constructs a new CustomConfirmationDialog.
     *
     * @param title   The title of the dialog.
     * @param message The message to be displayed in the dialog.
     */
    public CustomConfirmationDialog(String title, String message) {
        setTitle(title);
        initStyle(StageStyle.UTILITY);
        setHeaderText(null);
        setContentText(message);

        ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());

        getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        EventHandler<ActionEvent> actionEventHandler = event -> {
            ButtonType resultButtonType = ((Button) event.getSource()).getText().equals("OK")
                    ? ButtonType.OK : ButtonType.CANCEL;
            setResult(resultButtonType);
            hide();
        };

        Button okBtn = (Button) getDialogPane().lookupButton(okButton);
        okBtn.setOnAction(actionEventHandler);

        Button cancelBtn = (Button) getDialogPane().lookupButton(cancelButton);
        cancelBtn.setOnAction(actionEventHandler);

        getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Node focusedNode = getDialogPane().getScene().getFocusOwner();
                if (focusedNode instanceof Button) {
                    Button focusedButton = (Button) focusedNode;
                    if (focusedButton.getText().equals("OK")) {
                        setResult(ButtonType.OK);
                        hide();
                    } else if (focusedButton.getText().equals("Cancel")) {
                        setResult(ButtonType.CANCEL);
                        hide();
                    }
                }
            } else if (event.getCode() == KeyCode.ESCAPE) {
                setResult(ButtonType.CANCEL);
                hide();
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.DOWN) {
                getDialogPane().lookupButton(okButton).requestFocus();
            } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP) {
                getDialogPane().lookupButton(cancelButton).requestFocus();
            }
        });

        // Set the default button to OK
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.setOnShown(e -> getDialogPane().getScene().getRoot().requestFocus());
    }
}
