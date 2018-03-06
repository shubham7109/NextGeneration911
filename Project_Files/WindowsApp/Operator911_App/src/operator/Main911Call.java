package operator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main911Call extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Xmls/On911Call.fxml"));

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");

                Stage stage = new Stage();
                stage.setTitle("Look Up Person");
                Main main = new Main();
                try {
                    main.start(stage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        stage.setScene(new Scene(root));
        stage.show();
    }
}
