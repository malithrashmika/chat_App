package lk.ijse.chatapplication.Controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.chatapplication.Main;

import java.io.IOException;


public class Userlogin {
    public AnchorPane rootNode;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField password;

    @FXML
    private TextField user_name;


    @FXML
    void User_OnAction(ActionEvent event) {

    }

    @FXML
    void password_OnAction(ActionEvent event) {


    }

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        System.out.println("button Pressed");
        System.out.println(user_name.getText());
        System.out.println(password.getText());
       if (user_name.getText().equals("malith") && password.getText().equals("1234")) {
           FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Server.fxml"));
           Scene scene = new Scene(fxmlLoader.load(), 670, 600);
           Stage stage = new Stage();
           stage.setScene(scene);
           stage.setTitle("Client 1");
           stage.show();

           Stage stage2 = new Stage();
           FXMLLoader fxmlLoader1 = new FXMLLoader(Main.class.getResource("Client.fxml"));
           Scene scene1 = new Scene(fxmlLoader1.load(), 670, 600);
           stage2.setScene(scene1);
           stage2.setTitle("Client 2");
           stage2.show();



       }
    }

}
