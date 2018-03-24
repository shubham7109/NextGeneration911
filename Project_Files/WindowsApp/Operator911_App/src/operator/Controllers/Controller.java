package operator.Controllers;

import javafx.application.Platform;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import operator.Main911Call;
import operator.Models.LogModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML public ComboBox operatorStatus;

    @FXML private TableView<LogModel> logView;
    @FXML private Label timeLabel;
    @FXML private TableColumn<LogModel, String> date;
    @FXML private TableColumn<LogModel, String> time;
    @FXML private TableColumn<LogModel, String> callLength;
    @FXML private TableColumn<LogModel, String> operatorName;
    @FXML private TableColumn<LogModel, String> phoneNumber;

    private String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/logs";
    private ArrayList<LogModel> logModels;
    private Timer timer;

    @FXML
    public void initialize() {
        operatorStatus.getItems().removeAll(operatorStatus.getItems());
        operatorStatus.getItems().addAll("Online", "Offline", "OnCall");
        operatorStatus.getSelectionModel().select("Choose Status");
        operatorStatus.getSelectionModel().select("Choose Status");


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

                Stage primaryStage = (Stage) operatorStatus.getScene().getWindow();
            }
        });

        button_decline.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                newWindow.close();
            }
        });



    }





}
