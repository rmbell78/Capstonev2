module com.example.capstone {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.capstone.controllers to javafx.fxml;
    exports com.example.capstone.controllers;
}