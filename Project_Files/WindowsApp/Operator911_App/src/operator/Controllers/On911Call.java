package operator.Controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import operator.Client;
import operator.Models.PersonModel;
import operator.NetworkConnection;
import operator.Server;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class On911Call implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    @FXML protected GoogleMapView mapView;
    @FXML private TextArea messages;
    @FXML private TextField input;

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
    @FXML private Label timeElapsed;
    @FXML private Button closeButton;
    private String time;
    private String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/persons/";
    private double LAT = 42.033996;
    private double LONG = -93.641397;
    private PersonModel personModel;
    private String IP;
    private boolean isServer = true    ;
    private NetworkConnection connection = isServer ? createServer() : createClient();

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        options.center(new LatLong(LAT, LONG))
                .mapMaker(true)
                .zoomControl(true)
                .zoom(16)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);

        GoogleMap map = mapView.createMap(options);

        LatLong freddy = new LatLong(LAT, LONG);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(freddy);
        Marker freddymarker = new Marker(markerOptions);

        map.addMarker(freddymarker);

    }

    @Override
    public void directionsReceived(DirectionsResult directionsResult, DirectionStatus directionStatus) {

    }

    private Server createServer(){
        return new Server(5555,data ->{
            Platform.runLater(()->{
                messages.appendText(data.toString()+"\n");
            });
        });
    }

    private Client createClient(){
        return new Client("10.26.17.136", 5555, data ->{
            messages.appendText(data.toString() + "\n");
        });
    }

    @FXML
    public void onEnter(ActionEvent ae) throws Exception {
        String message = isServer ? "Server: " : "Client: ";
        message += input.getText();
        input.setText("");

        messages.appendText(message + "\n");
        connection.send(message);
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) throws IOException, JSONException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        putRequest("http://proj-309-sb-5.cs.iastate.edu:8080/logs");
        stage.close();
    }

    private void putRequest(String put_url) throws IOException, JSONException {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yy");
        String date =  sdf.format(cal.getTime());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date",date);
        sdf = new SimpleDateFormat("HH:mm");
        date =  sdf.format(cal.getTime());
        jsonObject.put("time",date);
        jsonObject.put("callLength",time);
        jsonObject.put("operatorName","Shubham Sharma");
        jsonObject.put("phoneNumber","765-765-7654");



        URL url = new URL(put_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(String.format(jsonObject.toString()));
        osw.flush();
        osw.close();
        System.err.println(connection.getResponseCode());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);

        Timer timer = new Timer();
        timeElapsed.setAlignment(Pos.CENTER);
        long startTime = System.currentTimeMillis();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    time = String.valueOf((System.currentTimeMillis() - startTime)/1000);
                    timeElapsed.setText("On Call for:\n"+time+ " seconds");
                });
            }
        }, 1000, 1000);

        try {
            IP = InetAddress.getLocalHost().getHostAddress();
            connection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO CHANGE THIS
        Platform.runLater(()->{
            int id = 111;
            setUpUrl(id);
            try {
                setPerons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setPerons() throws Exception {
        JSONObject jsonObject = new JSONObject(getHTML(URL));
        personModel= new PersonModel(jsonObject);
        id.setText(String.valueOf(personModel.getId()));
        id.deselect();
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

    private void setUpUrl(int id){
        URL = URL + id;
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

