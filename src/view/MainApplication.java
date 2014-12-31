package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MainApplication extends Application {

    private static Logger log = Logger.getLogger(MainApplication.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load logger config
        LogManager.getLogManager().readConfiguration(
                MainApplication.class.getResourceAsStream("/logging.properties")
        );

        try {
            //log.info("Logging subsystem initialised");

            //Loading main view form fxml
            Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

            //Brief view configuration
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException ioe) {
            log.log(Level.SEVERE, "Error loading FXML: \n", ioe);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: \n, e");
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
