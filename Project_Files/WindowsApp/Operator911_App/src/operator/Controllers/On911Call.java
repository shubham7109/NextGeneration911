package operator.Controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class On911Call implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    @FXML
    protected GoogleMapView mapView;
    @FXML
    private URL location;

    private double LAT = 42.033996;
    private double LONG = -93.641397;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
    }
}

