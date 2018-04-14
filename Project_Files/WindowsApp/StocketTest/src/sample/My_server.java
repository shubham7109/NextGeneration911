package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;


public class My_server extends Application{

        private boolean isServer = false;
        private NetworkConnection mainConnection  = isServer ? createMainServer() : createMainClient();
        private TextArea messages = new TextArea();
        private String IP = "10.25.69.139";

        public My_server() throws Exception {
        }

        @Override
        public void init() throws Exception{
            mainConnection.startConnection();
            IP = "10.25.69.139";
        }

        private Server createMainServer(){
            return new Server(8082,data ->{
                Platform.runLater(()->{
                    messages.appendText(data.toString() + "\n");
                });
            });
        }


        private Client createMainClient() throws UnknownHostException {
            return new Client(IP, 8082, data ->{
                Platform.runLater(()->{
                    messages.appendText(data.toString() + "\n");
                });

            });
        }


        private Parent createContent(){
            messages.setPrefHeight(100);
            messages.setPrefWidth(300);
            TextField input = new TextField();
            input.setOnAction(event -> {

                String message = input.getText();
                input.clear();
                messages.appendText(message+"\n");
                try {
                     mainConnection.send(message);
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
