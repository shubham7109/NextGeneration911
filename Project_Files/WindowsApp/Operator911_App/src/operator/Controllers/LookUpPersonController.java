package operator.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import operator.Models.PersonModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Controller class for LookUpPerson.fxml
 * @author Shubham Sharma
 */
public class LookUpPersonController {


    @FXML private TextField ID_text;
    @FXML private TextField id;
    @FXML private TextField phoneNumber;
    @FXML private TextField gender;
    @FXML private TextField firstName;
    @FXML private TextField middleName;
    @FXML private TextField lastName;
    @FXML private TextField homeAddress;
    @FXML private TextField city;
    @FXML private TextField state;
    @FXML private TextField zipcode;
    @FXML private TextField dateOfBirth;
    @FXML private TextField licencePlateNumber;
    @FXML private TextField vehicle;
    @FXML private TextField bloodType;
    @FXML private TextField heightCentimeters;
    @FXML private TextField weightKilograms;

    @FXML private Button lookButton;

    private PersonModel personModel;

    private String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/persons";

    /**
     * This method is called by the FXMLLoader
     * when initialization is complete
     */
    @FXML
    void initialize() {

        // set handlers
        lookButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    searchPerson();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Performs a search of the person when the enter key is pressed
     * @param ae On enter press
     * @throws Exception
     */
    @FXML
    public void onEnter(ActionEvent ae) throws Exception {
        searchPerson();
    }

    private void searchPerson() throws Exception {
        URL = "http://proj-309-sb-5.cs.iastate.edu:8080/persons/"+ID_text.getText();
        String response = getHTML(URL);
        if(response.length() == 0 || ID_text.getText().length() == 0){
            Label secondLabel = new Label("This ID does not exist!");

            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);

            Scene secondScene = new Scene(secondaryLayout, 230, 100);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("Error!");
            newWindow.setScene(secondScene);

            newWindow.show();
        }
        else{
            JSONObject jsonObject = new JSONObject(response);
            personModel= new PersonModel(jsonObject);
            id.setText(String.valueOf(personModel.getId()));
            phoneNumber.setText(String.valueOf(personModel.getPhoneNumber()));
            gender.setText(String.valueOf(personModel.getGender()));
            firstName.setText(personModel.getFirstName());
            middleName.setText(personModel.getMiddleName());
            lastName.setText(personModel.getLastName());
            homeAddress.setText(personModel.getHomeAddress());
            city.setText(personModel.getCity());
            state.setText(personModel.getState());
            zipcode.setText(personModel.getZipcode());
            dateOfBirth.setText(personModel.getDateOfBirth());
            licencePlateNumber.setText(personModel.getLicencePlateNumber());
            vehicle.setText(personModel.getVehicle());
            bloodType.setText(personModel.getBloodType());
            heightCentimeters.setText(personModel.getHeightCentimeters());
            weightKilograms.setText(personModel.getWeightKilograms());
            System.out.println(jsonObject);
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
}
