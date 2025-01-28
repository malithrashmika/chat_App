package lk.ijse.chatapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Server.fxml"))));
        stage.setTitle("Server Form");
        stage.centerOnScreen();
        stage.show();

        Stage stage2 = new Stage();

        stage2.setScene(new Scene(FXMLLoader.load(getClass().getResource("Client.fxml"))));
        stage2.setTitle("Client Form");
        stage2.centerOnScreen();
        stage2.show();
    }

}