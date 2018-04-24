package operator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import operator.Controllers.AdminController;
import operator.Controllers.Controller;
import operator.Controllers.OperatorLogin;
import operator.Models.PersonModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class to initialize the Main.fxml view and the calls its controller class
 * @author Shubham Sharma
 */
public class AdminLoggedIn extends Application{

    private String username;

    /**
     * Constructor to set the username
     * @param username Sets username
     */
    public AdminLoggedIn(String username) {
        this.username = username;
    }

    /**
     * Method to open the logged in view
     * @param primaryStage Defines the stage and its root
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Xmls/AdminLoggedIn.fxml"));
        AdminController controller = new AdminController(username);
        loader.setController(controller);
        AnchorPane anchorPane = loader.load();


        primaryStage.setTitle("911 Operator - Admin");

        Scene scene = new Scene(anchorPane, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
