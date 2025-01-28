package lk.ijse.chatapplication.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ClientController {


    @FXML
    private Button client_sendImagebtn;

    @FXML
    private Button client_sendbtn;
    @FXML
    private TextField client_textfield;

    @FXML
    private VBox client_messageContainer;
    @FXML
    private ScrollPane sp_main;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public void initialize() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 4000);

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                appendMessage("Connected to server.");

                while (true) {
                    String type = dataInputStream.readUTF(); // Read message type (text/image)

                    if ("text".equals(type)) {
                        String message = dataInputStream.readUTF(); // Read text message
                        Platform.runLater(() -> {
                           appendMessage("Client 2: "+message);
                            autoScroll();
                        });
                    } else if ("image".equals(type)) {
                        int length = dataInputStream.readInt(); // Read image byte length
                        byte[] imageBytes = new byte[length];
                        dataInputStream.readFully(imageBytes);

                        // Convert byte array to Image
                        Image image = new Image(new ByteArrayInputStream(imageBytes));
                        Platform.runLater(() -> {
                            ImageView imageView = new ImageView(image);
                            imageView.setFitWidth(200);
                            imageView.setPreserveRatio(true);
                            client_messageContainer.getChildren().add(imageView);
                            autoScroll();
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void client_sendbtn_OnAction(ActionEvent event) throws IOException {
        String message = client_textfield.getText();

        // Send text type identifier
        dataOutputStream.writeUTF("text");
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();

        // Display message in the client's UI
        appendMessage("Client 1 : "+message);
        autoScroll();
        client_textfield.clear();
    }

    @FXML
    void client_sendImagebtn_OnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());

            // Send image type identifier
            dataOutputStream.writeUTF("image");
            dataOutputStream.writeInt(imageBytes.length);
            dataOutputStream.write(imageBytes);
            dataOutputStream.flush();

            // Display image in the client's UI
            Image image = new Image(selectedFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            client_messageContainer.getChildren().add(imageView);
            autoScroll();
        }
    }
    private void autoScroll() {
        Platform.runLater(() -> sp_main.setVvalue(1.0));
    }

    @FXML
    void Discoonect_OnAction(ActionEvent event) throws IOException {
        appendMessage("Client disconnected.");
       socket.close();


    }
    private void appendMessage(String message) {
        Platform.runLater(() -> {
            Label label = new Label(message);
            label.setStyle("-fx-background-color: lightgray; -fx-padding: 5; -fx-border-radius: 5; -fx-background-radius: 5;");
            client_messageContainer.getChildren().add(label);

            // Auto-scroll to the bottom
            autoScroll();
        });
    }
}
