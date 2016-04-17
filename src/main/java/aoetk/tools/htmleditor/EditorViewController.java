package aoetk.tools.htmleditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
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
        final FileChooser fileChooser = createFileChooser();
        openedFile = fileChooser.showSaveDialog(getParentWindow());
        if (openedFile != null) {
            createNewFile();
            editor.setHtmlText("");
        }
    }

    public void handleOpenAction(ActionEvent actionEvent) {
        final FileChooser fileChooser = createFileChooser();
        openedFile = fileChooser.showOpenDialog(getParentWindow());
        if (openedFile != null) {
            loadFileContent();
        }
    }

    public void handleSaveAction(ActionEvent actionEvent) {
        if (openedFile == null) {
            handleSaveAsAction(actionEvent);
        } else {
            saveEditorContent();
        }
    }

    public void handlePrintAction(ActionEvent actionEvent) {
        final PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob == null) {
            showAlertDialog("Print isn't supported in this environment.");
        } else if (printerJob.showPrintDialog(getParentWindow())) {
            editor.print(printerJob);
            printerJob.endJob();
        }
    }

    public void handleSaveAsAction(ActionEvent actionEvent) {
        final FileChooser fileChooser = createFileChooser();
        openedFile = fileChooser.showSaveDialog(getParentWindow());
        if (openedFile != null) {
            createNewFile();
            saveEditorContent();
        }
    }

    private void createNewFile() {
        try {
            Files.createFile(openedFile.toPath());
        } catch (FileAlreadyExistsException fae) {
            showErrorDialog("File already exists!");
        } catch (IOException e) {
            handleException(e);
        }
    }

    private FileChooser createFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open HTML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html", "*.html"));
        return fileChooser;
    }

    private void loadFileContent() {
        try {
            byte[] fileContents = Files.readAllBytes(openedFile.toPath());
            editor.setHtmlText(new String(fileContents, StandardCharsets.UTF_8));
        } catch (IOException e) {
            handleException(e);
        }
    }

    private void saveEditorContent() {
        final List<String> content = Arrays.asList(editor.getHtmlText());
        try {
            Files.write(openedFile.toPath(), content, StandardCharsets.UTF_8,
                    StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        e.printStackTrace();
        final String message = e.getMessage();
        showErrorDialog(message);
    }

    private void showErrorDialog(String message) {
        final Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showAlertDialog(String message) {
        final Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }

    private Window getParentWindow() {
        return editor.getScene().getWindow();
    }

}
