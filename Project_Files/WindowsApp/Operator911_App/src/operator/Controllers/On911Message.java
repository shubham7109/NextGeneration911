package operator.Controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import operator.Client;
import operator.LoggedInView;
import operator.Models.DeployModel;
import operator.Models.OperatorModel;
import operator.Models.PersonModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Controller class for On911Message.fxml
 * @author Shubham Sharma
 */
public class On911Message implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    @FXML protected GoogleMapView mapView;
    @FXML private TextArea messages;
    @FXML private TextField input;
    @FXML private SplitPane splitPane1;
    @FXML private SplitPane splitPane2;
    @FXML private SplitPane splitPane3;
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
    @FXML private Button ambulance;
    @FXML private Button fireBrigade;
    @FXML private Button stateTroopers;
    @FXML private Button countyOfficers;
    @FXML private Button swatTeam;
    @FXML private Button firstResponders;
    private String time;
    private String URL = "http://proj-309-sb-5.cs.iastate.edu:8080/persons/";
    private double LAT;
    private double LONG;
    private ArrayList<DeployModel> deployModels;
    private ArrayList<DeployModel> ambulanceArray;
    private ArrayList<DeployModel> stateTroopersArray;
    private ArrayList<DeployModel> fireBrigadeArray;
    private ArrayList<DeployModel> countyOfficersArray;
    private ArrayList<DeployModel> swatTeamArray;
    private ArrayList<DeployModel> firstRespondersArray;
    private OperatorModel operatorModel;
    private GoogleMap map;
    private ArrayList<Marker> markerArrayList = new ArrayList<>();
    private ArrayList<MarkerOptions> markerOptionsArrayList = new ArrayList<>();
    private PersonModel personModel;



    /**
     * Constructor to set the instance variables
     * @param operatorModel Operator information for chat implementation
     * @param personModel Person information for location
     * @throws Exception
     */
    public On911Message(OperatorModel operatorModel, PersonModel personModel) throws Exception {
        if(personModel != null){
            LAT = Double.parseDouble(personModel.getLatitude());
            LONG = Double.parseDouble(personModel.getLongitude());
            this.personModel = personModel;
            this.operatorModel = operatorModel;
        }
    }

    /**
     * Creates the map to display the callers location
     * and all the nearby available deployment options
     */
    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        options.center(new LatLong(LAT, LONG))
                .mapMaker(true)
                .zoomControl(true)
                .zoom(15)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);

        map = mapView.createMap(options);

        LatLong callerLocation = new LatLong(LAT, LONG);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(callerLocation);

        Marker callerMarker = new Marker(markerOptions);

        for(int i=0; i<deployModels.size(); i++){
            markerOptionsArrayList.add(new MarkerOptions());
            LatLong latLong = new LatLong(Double.parseDouble(deployModels.get(i).getLatitude()),Double.parseDouble(deployModels.get(i).getLongitude()));
            markerOptionsArrayList.get(i).position(latLong);

            markerArrayList.add(new Marker(markerOptionsArrayList.get(i)));

            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content(deployModels.get(i).getType());

            InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
            //infoWindow.open(map, markerArrayList.get(i));

            map.addMarker(markerArrayList.get(i));

            int finalI = i;
            map.addUIEventHandler(markerArrayList.get(i), UIEventType.click, (JSObject obj) -> {
                infoWindow.open(map,markerArrayList.get(finalI));
            });
        }
        callerLocation = new LatLong(LAT, LONG);
        map.setCenter(callerLocation);
        map.addMarker(callerMarker);
        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("CALLER LOCATION");
        InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(map, callerMarker);

        map.addUIEventHandler(callerMarker, UIEventType.click, (JSObject obj) -> {
            infoWindow.open(map,callerMarker);
        });

    }

    /**
     * Method called when the directions between two locations are
     * received from the google API
     * @param directionsResult Result of the directions received
     * @param directionStatus Status of the received directions
     */
    @Override
    public void directionsReceived(DirectionsResult directionsResult, DirectionStatus directionStatus) {

    }

    /**
     * Opens ambulance deployment options
     * @param ae on button press
     */
    @FXML void ambulanceOnClick(ActionEvent ae){

        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 100);
        newWindow.setTitle("Deploy Ambulance");
        newWindow.setScene(scene);

        for (int i=0; i<ambulanceArray.size(); i++){
            Double distance = Math.round(ambulanceArray.get(i).distance(LAT,LONG,"M") * 1000.0)/1000.0;
            Label label = new Label("Ambulance available "+distance+" miles from caller");
            label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(label,0,i,1,1);
            Button deploy = new Button("DEPLOY");
            deploy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(deploy,1,i,1,1);

            int finalI = i;
            deploy.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    for(int k=0; k< deployModels.size(); k++){
                        for(int j=0; j<ambulanceArray.size(); j++){
                            if(deployModels.get(k).getId().equals(ambulanceArray.get(finalI).getId())){
                                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                                infoWindowOptions.content(deployModels.get(k).getType());
                                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                                infoWindow.open(map, markerArrayList.get(k));
                                break;
                            }
                        }
                    }
                }
            });
        }
        newWindow.show();
    }


    /**
     * Opens fire brigade deployment options
     * @param ae on button press
     */
    @FXML void fireBrigadeOnClick(ActionEvent ae){

        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 100);
        newWindow.setTitle("Deploy Fire Brigade");
        newWindow.setScene(scene);

        for (int i=0; i<fireBrigadeArray.size(); i++){
            Double distance = Math.round(fireBrigadeArray.get(i).distance(LAT,LONG,"M") * 1000.0)/1000.0;
            Label label = new Label("Fire Brigade available "+distance+" miles from caller");
            label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(label,0,i,1,1);
            Button deploy = new Button("DEPLOY");
            deploy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(deploy,1,i,1,1);

            int finalI = i;
            deploy.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    for(int k=0; k< deployModels.size(); k++){
                        for(int j=0; j<fireBrigadeArray.size(); j++){
                            if(deployModels.get(k).getId().equals(fireBrigadeArray.get(finalI).getId())){
                                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                                infoWindowOptions.content(deployModels.get(k).getType());
                                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                                infoWindow.open(map, markerArrayList.get(k));
                                break;
                            }
                        }
                    }
                }
            });
        }
        newWindow.show();

    }

    /**
     * Opens ambulance state troopers options
     * @param ae on button press
     */
    @FXML void stateTroopersOnClick(ActionEvent ae){

        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 100);
        newWindow.setTitle("Deploy Fire Brigade");
        newWindow.setScene(scene);

        for (int i=0; i<stateTroopersArray.size(); i++){
            Double distance = Math.round(stateTroopersArray.get(i).distance(LAT,LONG,"M") * 1000.0)/1000.0;
            Label label = new Label("State Troopers available "+distance+" miles from caller");
            label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(label,0,i,1,1);
            Button deploy = new Button("DEPLOY");
            deploy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(deploy,1,i,1,1);

            int finalI = i;
            deploy.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    for(int k=0; k< deployModels.size(); k++){
                        for(int j=0; j<stateTroopersArray.size(); j++){
                            if(deployModels.get(k).getId().equals(stateTroopersArray.get(finalI).getId())){
                                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                                infoWindowOptions.content(deployModels.get(k).getType());
                                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                                infoWindow.open(map, markerArrayList.get(k));
                                break;
                            }
                        }
                    }
                }
            });
        }
        newWindow.show();
    }

    /**
     * Opens county officers deployment options
     * @param ae on button press
     */
    @FXML void countyOfficersOnClick(ActionEvent ae){

        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 100);
        newWindow.setTitle("Deploy Fire Brigade");
        newWindow.setScene(scene);

        for (int i=0; i<countyOfficersArray.size(); i++){
            Double distance = Math.round(countyOfficersArray.get(i).distance(LAT,LONG,"M") * 1000.0)/1000.0;
            Label label = new Label("County Officers available "+distance+" miles from caller");
            label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(label,0,i,1,1);
            Button deploy = new Button("DEPLOY");
            deploy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(deploy,1,i,1,1);

            int finalI = i;
            deploy.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    for(int k=0; k< deployModels.size(); k++){
                        for(int j=0; j<countyOfficersArray.size(); j++){
                            if(deployModels.get(k).getId().equals(countyOfficersArray.get(finalI).getId())){
                                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                                infoWindowOptions.content(deployModels.get(k).getType());
                                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                                infoWindow.open(map, markerArrayList.get(k));
                                break;
                            }
                        }
                    }
                }
            });
        }
        newWindow.show();

    }

    /**
     * Opens SWAT team deployment options
     * @param ae on button press
     */
    @FXML void swatTeamOnClick(ActionEvent ae){

        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 100);
        newWindow.setTitle("Deploy Fire Brigade");
        newWindow.setScene(scene);

        for (int i=0; i<swatTeamArray.size(); i++){
            Double distance = Math.round(swatTeamArray.get(i).distance(LAT,LONG,"M") * 1000.0)/1000.0;
            Label label = new Label("S.W.A.T. Team available "+distance+" miles from caller");
            label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(label,0,i,1,1);
            Button deploy = new Button("DEPLOY");
            deploy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(deploy,1,i,1,1);

            int finalI = i;
            deploy.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    for(int k=0; k< deployModels.size(); k++){
                        for(int j=0; j<swatTeamArray.size(); j++){
                            if(deployModels.get(k).getId().equals(swatTeamArray.get(finalI).getId())){
                                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                                infoWindowOptions.content(deployModels.get(k).getType());
                                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                                infoWindow.open(map, markerArrayList.get(k));
                                break;
                            }
                        }
                    }
                }
            });
        }
        newWindow.show();

    }


    /**
     * Opens first responders deployment options
     * @param ae on button press
     */
    @FXML void firstRespondersOnClick(ActionEvent ae){

        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 100);
        newWindow.setTitle("Deploy Fire Brigade");
        newWindow.setScene(scene);

        for (int i=0; i<firstRespondersArray.size(); i++){
            Double distance = Math.round(firstRespondersArray.get(i).distance(LAT,LONG,"M") * 1000.0)/1000.0;
            Label label = new Label("First Responders available "+distance+" miles from caller");
            label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(label,0,i,1,1);
            Button deploy = new Button("DEPLOY");
            deploy.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
            grid.add(deploy,1,i,1,1);

            int finalI = i;
            deploy.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    for(int k=0; k< deployModels.size(); k++){
                        for(int j=0; j<firstRespondersArray.size(); j++){
                            if(deployModels.get(k).getId().equals(firstRespondersArray.get(finalI).getId())){
                                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                                infoWindowOptions.content(deployModels.get(k).getType());
                                InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
                                infoWindow.open(map, markerArrayList.get(k));
                                break;
                            }
                        }
                    }
                }
            });
        }
        newWindow.show();

    }

    /**
     * To send a message over the server
     * @param ae On enter press
     * @throws Exception
     */
    @FXML
    public void onEnter(ActionEvent ae) throws Exception {
        String message = "911 Operator:\n";
        message += input.getText();
        input.setText("");

        messages.appendText(message + "\n\n");
        //this.connection.send(message);
    }

    /**
     * End the 911 message and set the operator's status to available
     * @param event On enter press
     * @throws Exception
     */
    @FXML
    public void closeWindowAction(ActionEvent event) throws Exception {

        Stage stage = new Stage();
        stage.setTitle("Operator");
        LoggedInView loggedInView = new LoggedInView(operatorModel.getUserName());
        try {
            putRequest("http://proj-309-sb-5.cs.iastate.edu:8080/logs");
            loggedInView.start(stage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        Stage primaryStage = (Stage) closeButton.getScene().getWindow();
        primaryStage.close();
    }

    private void putRequest(String put_url) throws IOException, JSONException {

        //PUT LOGIN [STATUS]
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",operatorModel.getId());
        jsonObject.put("firstName",operatorModel.getFirstName());
        jsonObject.put("lastName",operatorModel.getLastName());
        jsonObject.put("accesibility",operatorModel.getAccesibility());
        jsonObject.put("userName",operatorModel.getUserName());
        jsonObject.put("password",operatorModel.getPassword());
        jsonObject.put("location",operatorModel.getLocation());
        jsonObject.put("status",0);
        jsonObject.put("ipAddress",operatorModel.getIpAddress());
        jsonObject.put("image",operatorModel.getImage());

        URL url = new URL("http://proj-309-sb-5.cs.iastate.edu:8080/login/"+operatorModel.getId());
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

        // PUT LOG

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yy");
        String date =  sdf.format(cal.getTime());
        jsonObject = new JSONObject();
        jsonObject.put("id",String.valueOf(ThreadLocalRandom.current().nextInt(0, 10000 + 1)));
        jsonObject.put("date",date);
        sdf = new SimpleDateFormat("HH:mm");
        date =  sdf.format(cal.getTime());
        jsonObject.put("time",date);
        jsonObject.put("callLength",time);
        jsonObject.put("operatorName",operatorModel.getFirstName()+ " " + operatorModel.getLastName());
        jsonObject.put("phoneNumber",personModel.getPhoneNumber());


        url = new URL(put_url);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(String.format(jsonObject.toString()));
        osw.flush();
        osw.close();
        System.err.println(connection.getResponseCode());
    }


    /**
     * Initialize the view of the controller and start a conneciton
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mapView.addMapInializedListener(this);
        messages.setWrapText(true);
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

        // TODO CHANGE THIS
        Platform.runLater(()->{
            int id = 111;
            setUpUrl(id);
            try {
                setPerons();
                setDeploys();
                splitPane1.setDividerPosition(0,0.23);
                splitPane2.setDividerPosition(0,0.70);
                splitPane3.setDividerPosition(0,0.70);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setDeploys() throws Exception {
        JSONArray deployArray = new JSONArray(getHTML("http://proj-309-sb-5.cs.iastate.edu:8080/deploy"));
        deployModels = new ArrayList<>();
        for(int i=0; i< deployArray.length(); i++){
            deployModels.add(new DeployModel(deployArray.getJSONObject(i)));
        }

        ambulanceArray = new ArrayList<>();
        for(int i=0; i< deployModels.size(); i++){
            if(deployModels.get(i).getType().equals("Ambulance")){
                ambulanceArray.add(deployModels.get(i));
            }
        }

        stateTroopersArray = new ArrayList<>();
        for(int i=0; i< deployModels.size(); i++){
            if(deployModels.get(i).getType().equals("State Trooper")){
                stateTroopersArray.add(deployModels.get(i));
            }
        }

        countyOfficersArray = new ArrayList<>();
        for(int i=0; i< deployModels.size(); i++){
            if(deployModels.get(i).getType().equals("County Officer")){
                countyOfficersArray.add(deployModels.get(i));
            }
        }

        swatTeamArray = new ArrayList<>();
        for(int i=0; i< deployModels.size(); i++){
            if(deployModels.get(i).getType().equals("Swat Team")){
                swatTeamArray.add(deployModels.get(i));
            }
        }

        fireBrigadeArray = new ArrayList<>();
        for(int i=0; i< deployModels.size(); i++){
            if(deployModels.get(i).getType().equals("Fire Brigade")){
                fireBrigadeArray.add(deployModels.get(i));
            }
        }

        firstRespondersArray = new ArrayList<>();
        for(int i=0; i< deployModels.size(); i++){
            if(deployModels.get(i).getType().equals("First Responder")){
                firstRespondersArray.add(deployModels.get(i));
            }
        }
    }

    private void setPerons() throws Exception {
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

