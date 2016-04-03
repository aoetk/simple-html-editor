package aoetk.tools.htmleditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ResourceBundle;

/**
 * Controller class for EditorView.fxml.
 */
public class EditorViewController implements Initializable {

    @FXML
    HTMLEditor editor;

    private File openedFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void handleNewFileAction(ActionEvent actionEvent) {

    }

    public void handleOpenAction(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open HTML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html", "*.html"));
        openedFile = fileChooser.showOpenDialog(getParentWindow());
        if (openedFile != null) {
            loadFileContent();
        }
    }

    public void handleSaveAction(ActionEvent actionEvent) {

    }

    public void handlePrintAction(ActionEvent actionEvent) {

    }

    public void handleSaveAsAction(ActionEvent actionEvent) {

    }

    private void loadFileContent() {
        try {
            byte[] fileContents = Files.readAllBytes(openedFile.toPath());
            editor.setHtmlText(new String(fileContents, Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
            final Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private Window getParentWindow() {
        return editor.getScene().getWindow();
    }

}
