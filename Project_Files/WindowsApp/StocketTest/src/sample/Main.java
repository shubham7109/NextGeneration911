package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class Main extends Application {

    private boolean isServer = false;
    private NetworkConnection firstConnection = isServer ? createServer() : createClient();
    private NetworkConnection mainConnection  = isServer ? createMainServer() : createMainClient();
    private TextArea messages = new TextArea();
    private boolean firstMessage = true;
    private String IP = "";
    boolean firstTime = true;

    public Main() throws Exception {
    }

        @Override
        public void init() throws Exception{
            IP = getHTML("http://proj-309-sb-5.cs.iastate.edu:8080/makecall");
            firstConnection.startConnection();
        }

    private Server createMainServer(){
        return new Server(7777,data ->{
            Platform.runLater(()->{
                messages.appendText(data.toString() + "\n");
            });
        });
    }


    private Client createMainClient() throws UnknownHostException {
        return new Client(IP, 7777, data ->{
            Platform.runLater(()->{
                messages.appendText(data.toString() + "\n");
            });

        });
    }

    private Client createClient() throws Exception {
        if(firstTime){
            IP = getHTML("http://proj-309-sb-5.cs.iastate.edu:8080/makecall");
            firstTime = false;
        }
        return new Client(IP, 5555, data ->{
            Platform.runLater(()->{
                messages.appendText(data.toString() + "\n");
            });

        });
    }

    private Server createServer(){
        return new Server(5555,data ->{
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
                if(firstMessage)
                {
                    firstConnection.send(message);
                    firstMessage=false;
                    //firstConnection.closeConnection();
                    mainConnection.startConnection();
                }
                else
                    mainConnection.send(message);
            } catch (Exception e) {
                messages.appendText("Failed to send\n");
            }
        });

        VBox root = new VBox(20,messages,input);
        return root;
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

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Line Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Portfolio 1");

        series1.getData().add(new XYChart.Data("Jan", 23));
        series1.getData().add(new XYChart.Data("Feb", 14));
        series1.getData().add(new XYChart.Data("Mar", 15));
        series1.getData().add(new XYChart.Data("Apr", 24));
        series1.getData().add(new XYChart.Data("May", 34));
        series1.getData().add(new XYChart.Data("Jun", 36));
        series1.getData().add(new XYChart.Data("Jul", 22));
        series1.getData().add(new XYChart.Data("Aug", 45));
        series1.getData().add(new XYChart.Data("Sep", 43));
        series1.getData().add(new XYChart.Data("Oct", 17));
        series1.getData().add(new XYChart.Data("Nov", 29));
        series1.getData().add(new XYChart.Data("Dec", 25));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Portfolio 2");
        series2.getData().add(new XYChart.Data("Jan", 33));
        series2.getData().add(new XYChart.Data("Feb", 34));
        series2.getData().add(new XYChart.Data("Mar", 25));
        series2.getData().add(new XYChart.Data("Apr", 44));
        series2.getData().add(new XYChart.Data("May", 39));
        series2.getData().add(new XYChart.Data("Jun", 16));
        series2.getData().add(new XYChart.Data("Jul", 55));
        series2.getData().add(new XYChart.Data("Aug", 54));
        series2.getData().add(new XYChart.Data("Sep", 48));
        series2.getData().add(new XYChart.Data("Oct", 27));
        series2.getData().add(new XYChart.Data("Nov", 37));
        series2.getData().add(new XYChart.Data("Dec", 29));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Portfolio 3");
        series3.getData().add(new XYChart.Data("Jan", 44));
        series3.getData().add(new XYChart.Data("Feb", 35));
        series3.getData().add(new XYChart.Data("Mar", 36));
        series3.getData().add(new XYChart.Data("Apr", 33));
        series3.getData().add(new XYChart.Data("May", 31));
        series3.getData().add(new XYChart.Data("Jun", 26));
        series3.getData().add(new XYChart.Data("Jul", 22));
        series3.getData().add(new XYChart.Data("Aug", 25));
        series3.getData().add(new XYChart.Data("Sep", 43));
        series3.getData().add(new XYChart.Data("Oct", 44));
        series3.getData().add(new XYChart.Data("Nov", 45));
        series3.getData().add(new XYChart.Data("Dec", 44));

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2, series3);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
