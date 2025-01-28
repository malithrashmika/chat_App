module lk.ijse.chatapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.chatapplication to javafx.fxml;
    exports lk.ijse.chatapplication;
    exports lk.ijse.chatapplication.Controllers;
    opens lk.ijse.chatapplication.Controllers to javafx.fxml;
}