package operator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import operator.Models.LogModel;
import operator.Models.OperatorModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Controller {

    @FXML
    private Button lookUpPerson;
    public ComboBox operatorStatus;

    @FXML private TableView<LogModel> logView;

    @FXML private TableColumn<LogModel, String> date;
    @FXML private TableColumn<LogModel, String> time;
    @FXML private TableColumn<LogModel, String> callLength;
    @FXML private TableColumn<LogModel, String> operatorName;
    @FXML private TableColumn<LogModel, String> phoneNumber;
    private String URL = "http://localhost:8080/logs";
    private ArrayList<LogModel> logModels;
    @FXML
    public void initialize() {
        operatorStatus.getItems().removeAll(operatorStatus.getItems());
        operatorStatus.getItems().addAll("Online", "Offline", "OnCall");
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/operator/LookUpPerson.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/operator/OperatorList.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Look Up Person");
            stage.setScene(new Scene(root));
            stage.show();



        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void setOperatorStatus(ActionEvent event){

    }




}
