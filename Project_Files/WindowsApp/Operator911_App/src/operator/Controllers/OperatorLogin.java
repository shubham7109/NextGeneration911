package operator.Controllers; /**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operator.LoggedInView;
import operator.Main911Call;
import operator.Models.OperatorModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OperatorLogin {

    @FXML
    private Text loginError;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="scenetitle"
    private Text scenetitle; // Value injected by FXMLLoader

    @FXML // fx:id="userName"
    private Label userName; // Value injected by FXMLLoader

    @FXML // fx:id="userTextField"
    private TextField userTextField; // Value injected by FXMLLoader

    @FXML // fx:id="pw"
    private Label pw; // Value injected by FXMLLoader

    @FXML // fx:id="pwBox"
    private PasswordField pwBox; // Value injected by FXMLLoader

    @FXML // fx:id="btn"
    private Button btn; // Value injected by FXMLLoader

    private boolean loginAuth = false;
    private OperatorModel operator;
    @FXML
    public void onEnter(ActionEvent ae) throws Exception {
        loginAuth = checkLogin();
        if(loginAuth){
            updateStatus();
            loginEnter();
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        // set handlers
        btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    try {
                        loginAuth = checkLogin();
                        if(loginAuth){
                            updateStatus();
                            loginEnter();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void updateStatus() throws Exception {

        String response = getHTML("http://proj-309-sb-5.cs.iastate.edu:8080/login/");
        ArrayList<OperatorModel> operatorModels = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            operatorModels.add(new OperatorModel(jsonObject));

            if(operatorModels.get(i).getUserName().equals(userTextField.getText()))
                operator = operatorModels.get(i);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",operator.getId());
        jsonObject.put("firstName",operator.getFirstName());
        jsonObject.put("lastName",operator.getLastName());
        jsonObject.put("accesibility",operator.getAccesibility());
        jsonObject.put("userName",operator.getUserName());
        jsonObject.put("password",operator.getPassword());
        jsonObject.put("location",operator.getLocation());
        jsonObject.put("status",1);
        jsonObject.put("ipAddress",operator.getIpAddress());
        jsonObject.put("image",operator.getImage());

        URL url = new URL("http://proj-309-sb-5.cs.iastate.edu:8080/login/" + operator.getId());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(String.format(jsonObject.toString()));
        osw.flush();
        osw.close();
        System.err.println(connection.getResponseCode());
    }

    private boolean checkLogin() throws Exception {
        loginError.setFill(Color.FIREBRICK);
        if(userTextField.getText().equals("") || pwBox.getText().equals(""))
        {
            loginError.setText("Username or password cannot be empty");
            return false;
        }

        String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/login/" + userTextField.getText() + "/" + pwBox.getText();
         if(getHTML(URL).equals("true")){
             return true;
         }
         else
         {
             loginError.setText("Incorrect username or password!");
             return false;
         }
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

    private void loginEnter(){
        Stage stage = new Stage();
        stage.setTitle("Operator");
        LoggedInView loggedInView = new LoggedInView(userTextField.getText());
        try {
            loggedInView.start(stage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        Stage primaryStage = (Stage) loginError.getScene().getWindow();
        primaryStage.close();
    }
}
