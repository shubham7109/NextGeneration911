package operator.Controllers;

import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import operator.Main911Call;
import operator.Models.LogModel;
import operator.Models.OperatorModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

public class Controller {

    @FXML public ComboBox operatorStatus;
    @FXML private Label operatorsName;
    @FXML private TableView<LogModel> logView;
    @FXML private Label timeLabel;
    @FXML private TableColumn<LogModel, String> date;
    @FXML private TableColumn<LogModel, String> time;
    @FXML private TableColumn<LogModel, String> callLength;
    @FXML private TableColumn<LogModel, String> operatorName;
    @FXML private TableColumn<LogModel, String> phoneNumber;
    @FXML private Button logoutButton;

    private String username;
    private String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/logs";
    private String LOGIN_URL = "http://proj-309-sb-5.cs.iastate.edu:8080/login/";
    private ArrayList<LogModel> logModels;
    private Timer timer;
    private OperatorModel operator;

    public Controller(String username){
        this.username = username;
        System.out.println(this.username);
    }

    @FXML
    public void initialize() {
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

        callLength = new TableColumn("Call Length(min)");
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

    @FXML
    void logoutPress(ActionEvent event) throws IOException, JSONException {
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

    @FXML
    void simulateCallPress(ActionEvent event) {

        // TODO Update this method !
        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 200);
        newWindow.setTitle("Incoming Call");
        newWindow.setScene(scene);

        Text scenetitle = new Text("Incoming call from: (847)-943-7754");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        grid.add(scenetitle, 0, 0, 2, 1);

        Button button_accept = new Button("Accept Call");
        button_accept.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        HBox hbBtn_accept = new HBox(10);
        hbBtn_accept.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn_accept.getChildren().add(button_accept);
        grid.add(hbBtn_accept, 1, 4);

        Button button_decline = new Button("Decline Call");
        button_decline.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        HBox hbBtn_decline = new HBox(10);
        hbBtn_decline.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn_decline.getChildren().add(button_decline);
        grid.add(hbBtn_decline, 2, 4);

        newWindow.show();

        button_accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {


                Stage stage = new Stage();
                stage.setTitle("On Call");
                Main911Call main911Call = new Main911Call();
                try {
                    main911Call.start(stage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                newWindow.close();
            }
        });

        button_decline.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                newWindow.close();
            }
        });



    }





}
