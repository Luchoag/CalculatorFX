package calculatorfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
        Platform.runLater( () -> root.requestFocus() );
    }

    public static void main(String[] args) {
        launch(args);
        
    }
    
}
