module com.example.fiftyclash {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.smartcardio;


    opens com.example.fiftyclash to javafx.fxml;
    opens com.example.fiftyclash.controllers to javafx.fxml;
    exports com.example.fiftyclash;
}