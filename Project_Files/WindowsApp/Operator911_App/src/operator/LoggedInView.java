package operator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
public class LoggedInView extends Application{

        private ArrayList<PersonModel> personModels;
        private String username;
        // Constants
        private final String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/persons";

    /**
     * Constructor to set the username
     * @param username Sets username
     */
    public LoggedInView(String username) {
        this.username = username;
    }

    /**
     * Method to open the logged in view
     * @param primaryStage Defines the stage and its root
     * @throws Exception
     */
    @Override
        public void start(Stage primaryStage) throws Exception{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Xmls/Main.fxml"));
            Controller controller = new Controller(username);
            loader.setController(controller);
            AnchorPane anchorPane = loader.load();
            personModels = new ArrayList<>();

            String response = getHTML(URL);
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                personModels.add(new PersonModel(jsonObject));

            }

            primaryStage.setTitle("911 Operator");

            Scene scene = new Scene(anchorPane, 200, 200);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        }

        private static String getHTML(String urlToRead) throws Exception {
            StringBuilder result = new StringBuilder();
            java.net.URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        }

    }
