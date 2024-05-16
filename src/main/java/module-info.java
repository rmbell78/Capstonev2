module com.example.capstone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.capstone.controllers to javafx.fxml;
    exports com.example.capstone.controllers;
}