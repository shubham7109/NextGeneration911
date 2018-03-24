package operator;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main911Call extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Xmls/On911Call.fxml"));

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");

                Stage stage = new Stage();
                stage.setTitle("Operator");
                LoggedInView loggedInView = new LoggedInView();
                try {
                    loggedInView.start(stage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.setMaximized(true);

        stage.show();

    }
}
