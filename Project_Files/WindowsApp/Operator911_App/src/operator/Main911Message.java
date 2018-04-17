package operator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import operator.Controllers.On911Message;
import operator.Models.OperatorModel;
import operator.Models.PersonModel;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class to initialize the On911Message.fxml view and the calls its controller class
 * @author Shubham Sharma
 */
public class Main911Message extends Application {

    private OperatorModel operatorModel;
    private PersonModel personModel;

    /**
     * Required constructor
     */
    public Main911Message(){
        // Required Constructor
    }

    /**
     * Constructor to set the instance variables and
     * create the OperatorModel and PersonModel
     * @param username username of the operator
     * @param callerID ID of the caller
     * @throws Exception
     */
    public Main911Message(String username, String callerID) throws Exception {
        JSONArray operators  = new JSONArray(getHTML("http://proj-309-sb-5.cs.iastate.edu:8080/login"));
        JSONArray persons = new JSONArray(getHTML("http://proj-309-sb-5.cs.iastate.edu:8080/persons"));

        for(int i=0; i<operators.length(); i++){
            if((new OperatorModel(operators.getJSONObject(i)).getUserName()).equals(username)){
                operatorModel = new OperatorModel(operators.getJSONObject(i));
            }
        }
        for(int i=0; i<persons.length(); i++){
            if((new PersonModel(persons.getJSONObject(i)).getId()).equals(callerID)){
                personModel = new PersonModel(persons.getJSONObject(i));
            }
        }
    }

    /**
     * Creates the view when on a 911 message
     * @param stage Sets the stage and the root of the view
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Xmls/On911Message.fxml"));
        On911Message controller = new On911Message(operatorModel,personModel);
        loader.setController(controller);
        AnchorPane anchorPane = loader.load();


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");

                Stage stage = new Stage();
                stage.setTitle("Operator");
            }
        });

        stage.setTitle("911 Operator");

        Scene scene = new Scene(anchorPane, 200, 200);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
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
