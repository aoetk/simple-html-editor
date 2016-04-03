package aoetk.tools.htmleditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application.
 */
public class SimpleHtmlEditorApp extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/EditorView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Simple HTML Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        SimpleHtmlEditorApp.launch(args);
    }

}
