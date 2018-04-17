package operator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import operator.Controllers.OperatorLogin;
import operator.Models.PersonModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Main class for the NextGen 911 project.
 * @author  Shubham Sharma
 */
public class Main extends Application {

    /**
     * Overridden method to start Login.fxml view for Controller.java
     * @param primaryStage Defines the view to be displayed
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Xmls/Login.fxml"));

        primaryStage.setTitle("911 Operator");
        //primaryStage.setMaximized(false););
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * Main method for the NextGen 911 project
     * @param args No args needed
     */
    public static void main(String[] args) {
        launch(args);
    }
}
