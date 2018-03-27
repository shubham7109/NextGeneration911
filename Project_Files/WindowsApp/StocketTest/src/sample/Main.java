package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends Application {

    private boolean isServer = false;
    private NetworkConnection connection = isServer ? createServer() : createClient();
    private TextArea messages = new TextArea();

    public Main() throws UnknownHostException {
    }

    @Override
    public void init() throws Exception{
        connection.startConnection();
    }

    @Override
    public void stop() throws Exception{
        connection.closeConnection();
    }

    private Server createServer(){
        return new Server(5555,data ->{
            Platform.runLater(()->{
                messages.appendText(data.toString() + "\n");
            });
        });
    }

    private Client createClient() throws UnknownHostException {
        return new Client(InetAddress.getLocalHost().getHostAddress(), 5555, data ->{
            Platform.runLater(()->{
                messages.appendText(data.toString() + "\n");
            });

        });
    }

    private Parent createContent(){
        messages.setPrefHeight(550);
        TextField input = new TextField();
        input.setOnAction(event -> {

            String message = input.getText();
            input.clear();

            messages.appendText(message+"\n");

            try {
                connection.send(message);
            } catch (Exception e) {
                messages.appendText("Failed to send\n");
            }
        });

        VBox root = new VBox(20,messages,input);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
