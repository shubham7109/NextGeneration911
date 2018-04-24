package operator.Controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import operator.*;
import operator.Models.LogModel;
import operator.Models.OperatorModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

import static java.lang.Character.isDigit;

/**
 * Controller class for Main.fxml
 * @author Shubham Sharma
 */
public class AdminController {

    @FXML public ComboBox operatorStatus;
    @FXML private Label operatorsName;
    @FXML private TableView<LogModel> logView;
    @FXML private Label timeLabel;
    @FXML private TableColumn<LogModel, String> date;
    @FXML private TableColumn<LogModel, String> time;
    @FXML private TableColumn<LogModel, String> callLength;
    @FXML private TableColumn<LogModel, String> operatorName;
    @FXML private TableColumn<LogModel, String> phoneNumber;
    @FXML private ImageView profileImage;
    @FXML private Button logoutButton;
    @FXML private TextField idEnter;
    @FXML private Text messageLabel;

    private String username;
    private String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/logs";
    private String LOGIN_URL = "http://proj-309-sb-5.cs.iastate.edu:8080/operators/";
    private ArrayList<LogModel> logModels;
    private Timer timer;
    private OperatorModel operator;
    private int portNumber = 6789;
    private String host = "10.25.69.139";
    private ArrayList<OperatorModel> operatorModels;

    private boolean isServer = true;

    private void updateOperators(){
        operatorModels = new ArrayList<>();

        try{
            String response = getHTML("http://proj-309-sb-5.cs.iastate.edu:8080/operators");
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                operatorModels.add(new OperatorModel(jsonObject));
                System.out.println(jsonObject);
            }
        }catch (Exception e){

        }
    }

    @FXML
    public void onEnter(ActionEvent ae) throws Exception {
        updateOperators();
        boolean check = false;


        for (OperatorModel operatorModel:
             operatorModels) {
            String id = idEnter.getText();

            if(id.equals(operatorModel.getId()) && operatorModel.getStatus().equals("On-Call")){
                timer.cancel();
                timer.purge();
                //client = new Client(portNumber,host,operator.getFirstName(),idEnter.getText());
                Thread.sleep(100);
                Stage stage = new Stage();
                stage.setTitle("Welcome");
                Main911Message main911Call = new Main911Message(username, idEnter.getText(),operatorModel.getId());
                updateStatus();
                main911Call.start(stage);
                Stage primaryStage = (Stage) operatorStatus.getScene().getWindow();
                primaryStage.close();
                check = true;
            }
        }
        if(!check){
            messageLabel.setFill(Color.FIREBRICK);
            messageLabel.setText("This operator is not on call!");
        }

    }

    /**
     * Required default constructor
     */
    public AdminController(){
        // Required Constructor
    }


    /**
     * Constructor: Starts the connection to listen's for a 911 Message
     * @param username gets the username of the operator.
     */
    public AdminController(String username){
        this.username = username;
    }

    /**
     * This method initializes the view, sets the status of the login user,
     * updates the IP on the server for the user, pulls in the list of logs,
     * and displays interactive buttons, images and other information.
     * @throws Exception
     */
    @FXML
    public void initialize() throws Exception {

        operatorStatus.getItems().removeAll(operatorStatus.getItems());
        operatorStatus.getItems().addAll("Available", "Unavailable");

        operatorStatus.getSelectionModel().select(0);
        operatorStatus.buttonCellProperty().bind(
                Bindings.createObjectBinding(() -> {
                    StackPane arrowButton = (StackPane) operatorStatus.lookup(".arrow-button");
                    return new ListCell<String>() {

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
                                setBackground(Background.EMPTY);
                                setText("");
                            } else {
                                if(item.equals("Available"))
                                    setTextFill(Color.GREEN);
                                else
                                    setTextFill(Color.RED);
                                setText(item);
                            }
                            // Set the background of the arrow also
                            if (arrowButton != null)
                                arrowButton.setBackground(getBackground());
                        }

                    };
                }, operatorStatus.valueProperty()));
        operatorStatus.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override public ListCell<String> call(ListView<String> param) {
                final ListCell<String> cell = new ListCell<String>() {
                    {
                        super.setPrefWidth(100);
                    }
                    @Override public void updateItem(String item,
                                                     boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            if (item.contains("Unavailable")) {
                                setTextFill(Color.RED);
                            }
                            else if (item.contains("Available")){
                                setTextFill(Color.GREEN);
                            }
                            else {
                                setTextFill(Color.BLACK);
                            }
                        }
                        else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        logModels = new ArrayList<>();
        try{
            String response = getHTML(URL);
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                logModels.add(new LogModel(jsonObject));
                System.out.println(jsonObject);
            }
        }catch (Exception e){

        }
        ArrayList<OperatorModel> operatorModels;
        operatorModels = new ArrayList<>();

        try{
            String response = getHTML(LOGIN_URL);
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                operatorModels.add(new OperatorModel(jsonObject));
                System.out.println(jsonObject);
            }
        }catch (Exception e){

        }
        for(int i=0; i< operatorModels.size(); i++){
            if(operatorModels.get(i).getUserName().equals(username))
                operator = operatorModels.get(i);
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yy");
                    String time =sdf.format(cal.getTime()) + "\n";
                    sdf = new SimpleDateFormat("HH:mm:ss");
                    time =  time + sdf.format(cal.getTime());
                    timeLabel.setText(time);
                });
            }
        }, 1000, 1000);

        profileImage.setImage(new Image(operator.getImage()));
        operatorsName.setText(operator.getFirstName() + " " + operator.getLastName());
        ObservableList<LogModel> observableList = FXCollections.observableArrayList(logModels);

        date = new TableColumn("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        logView.setItems(observableList);
        logView.getColumns().add(date);

        time = new TableColumn("Time");
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        logView.setItems(observableList);
        logView.getColumns().add(time);

        callLength = new TableColumn("Call Length(secs)");
        callLength.setCellValueFactory(new PropertyValueFactory<>("callLength"));
        logView.setItems(observableList);
        logView.getColumns().add(callLength);

        operatorName = new TableColumn("Operator Name");
        operatorName.setCellValueFactory(new PropertyValueFactory<>("operatorName"));
        logView.setItems(observableList);
        logView.getColumns().add(operatorName);

        phoneNumber = new TableColumn("Phone Number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        logView.setItems(observableList);
        logView.getColumns().add(phoneNumber);

        operatorStatus.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue == "Unavailable") {
                    try {
                        putRequest(LOGIN_URL+operator.getId(),1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    try {
                        putRequest(LOGIN_URL+operator.getId(),0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Performs GET requests to the given url
     * @param urlToRead The url to perform the GET request
     * @return Returns the JSON code as a String
     * @throws Exception
     */
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
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

    /**
     * Open's the view to look up person based on their ID
     * @param event On button click
     */
    public void openLookUpPerson(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/operator/Xmls/LookUpPerson.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Look Up Person");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }


    /**
     * Open's the view to display the list of operators in the database.
     * @param event On button click
     */
    public void openOperatorList(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/operator/Xmls/OperatorList.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Look Up Person");
            stage.setScene(new Scene(root));
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Open's the view to display reports of the Operators.
     * @param event On button click
     */
    public void openReportsView(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/operator/Xmls/ReportsView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Admin - Reports");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Button to logout the user, open's a view to login as another user and update the status of the user
     * @param event On button press
     * @throws Exception
     */
    @FXML
    void logoutPress(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/operator/Xmls/Login.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Stage primaryStage = (Stage) operatorStatus.getScene().getWindow();
        putRequest(LOGIN_URL+operator.getId(),3);
        primaryStage.close();
        stage.setTitle("Login View");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void putRequest(String put_url, int status) throws IOException, JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",operator.getId());
        jsonObject.put("firstName",operator.getFirstName());
        jsonObject.put("lastName",operator.getLastName());
        jsonObject.put("accesibility",operator.getAccesibility());
        jsonObject.put("userName",operator.getUserName());
        jsonObject.put("password",operator.getPassword());
        jsonObject.put("location",operator.getLocation());
        jsonObject.put("status",status);
        jsonObject.put("ipAddress",operator.getIpAddress());
        jsonObject.put("image",operator.getImage());

        URL url = new URL(put_url);
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

    private void updateStatus() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",operator.getId());
        jsonObject.put("firstName",operator.getFirstName());
        jsonObject.put("lastName",operator.getLastName());
        jsonObject.put("accesibility",operator.getAccesibility());
        jsonObject.put("userName",operator.getUserName());
        jsonObject.put("password",operator.getPassword());
        jsonObject.put("location",operator.getLocation());
        jsonObject.put("status",2);
        jsonObject.put("ipAddress", InetAddress.getLocalHost().getHostAddress());
        jsonObject.put("image",operator.getImage());

        URL url = new URL("http://proj-309-sb-5.cs.iastate.edu:8080/operators/" + operator.getId());
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

}
