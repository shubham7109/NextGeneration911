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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import operator.Client;
import operator.NetworkConnection;
import operator.Server;

import java.net.URL;
import java.util.ResourceBundle;


public class On911Call implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    @FXML
    protected GoogleMapView mapView;
    @FXML
    private URL location;
    @FXML
    private TextArea messages;
    @FXML
    private TextField input;



    private double LAT = 42.033996;
    private double LONG = -93.641397;

    private boolean isServer = false;
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
        return new Client("10.36.40.35", 5555, data ->{
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        try {
            connection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

