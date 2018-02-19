package operator.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    public ComboBox operatorStatus;

    @FXML
    public void initialize() {
        operatorStatus.getItems().removeAll(operatorStatus.getItems());
        operatorStatus.getItems().addAll("Available", "Unavailable", "Offline");
        operatorStatus.getSelectionModel().select("Choose Status");
    }


    public void openLookUpPerson(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("LookUpPerson.fxml"));
            fxmlLoader.setController(new LookUpPerson_Controller());
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Look Up Person");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void openOperatorList(ActionEvent event){
        System.out.println("openOperatorList");
    }

    public void setOperatorStatus(ActionEvent event){

    }




}
