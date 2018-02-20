package operator;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Button lookUpPerson;
    public ComboBox operatorStatus;

    @FXML
    public void initialize() {
        operatorStatus.getItems().removeAll(operatorStatus.getItems());
        operatorStatus.getItems().addAll("Available", "Unavailable", "Offline");
        operatorStatus.getSelectionModel().select("Choose Status");
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

    public void setOperatorStatus(ActionEvent event){

    }




}
