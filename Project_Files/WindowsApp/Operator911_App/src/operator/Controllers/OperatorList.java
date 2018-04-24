package operator.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import operator.Models.OperatorModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for OperatorList.fxml
 * @author Shubham Sharma
 */
public class OperatorList implements Initializable{

    private String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/operators";
    private ArrayList<OperatorModel> operatorModels;
    @FXML private TableView<OperatorModel> tableView;
    @FXML private TableColumn<OperatorModel, String> operatorName;
    @FXML private TableColumn<OperatorModel, String> locations;
    @FXML private TableColumn<OperatorModel, String> status;
    @FXML private TableColumn<OperatorModel, String> id;

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

    /**
     * Initializes the view for the controller and
     * performs get requests operator list
     * @param location url of the location
     * @param resources resources information
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        operatorModels = new ArrayList<>();

        try{
            String response = getHTML(URL);
            JSONArray jsonArray = new JSONArray(response);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                operatorModels.add(new OperatorModel(jsonObject));
                System.out.println(jsonObject);
            }
        }catch (Exception e){

        }

        ObservableList<OperatorModel> observableList = FXCollections.observableArrayList(operatorModels);

        //tableView = new TableView<>();
        operatorName = new TableColumn("Operator Name");
        operatorName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableView.setItems(observableList);
        tableView.getColumns().add(operatorName);

        locations = new TableColumn("Location");
        locations.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableView.setItems(observableList);
        tableView.getColumns().add(locations);

        status = new TableColumn("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableView.setItems(observableList);
        tableView.getColumns().add(status);

        id = new TableColumn("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.setItems(observableList);
        tableView.getColumns().add(id);
    }
}
