package sample;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class My_server {

    public static void main(String[] args) throws IOException, InterruptedException {


        MultiServer multiServer = new MultiServer(1234);

        Thread.sleep(10000);
        System.out.println("Now starting server:");
        while(true){
            multiServer.sendToAll("Hey all!");
            Thread.sleep(1000);
        }


    }

}
