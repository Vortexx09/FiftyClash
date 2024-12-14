module com.example.fiftyclash {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.smartcardio;
    requires org.junit.jupiter.api;
    requires org.apiguardian.api;

    opens com.example.fiftyclash to javafx.fxml;
    opens com.example.fiftyclash.controllers to javafx.fxml;
    exports com.example.fiftyclash;
}