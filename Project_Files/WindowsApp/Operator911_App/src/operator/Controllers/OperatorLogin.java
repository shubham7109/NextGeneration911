package operator.Controllers; /**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    @FXML
    public void onEnter(ActionEvent ae) throws Exception {
        loginAuth = checkLogin();
        if(loginAuth)
            loginEnter();
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(loginAuth)
                        loginEnter();

                }
            }
        });
    }

    private boolean checkLogin() throws Exception {
        loginError.setFill(Color.FIREBRICK);
        if(userTextField.getText().equals("") || pwBox.getText().equals(""))
        {
            loginError.setText("Username or password cannot be empty");
            return false;
        }

        /*String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/login/" + userTextField.getText() + "/" + pwBox.getText();
         if(getHTML(URL).equals("true")){
             return true;
         }
         else
         {
             loginError.setText("Incorrect username or password!");*/
             return true;

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
        LoggedInView loggedInView = new LoggedInView();
        try {
            loggedInView.start(stage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        Stage primaryStage = (Stage) loginError.getScene().getWindow();
        primaryStage.close();
    }
}
