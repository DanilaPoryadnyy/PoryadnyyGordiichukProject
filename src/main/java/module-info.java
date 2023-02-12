module com.example.poryadnyygordiichukproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.poryadnyygordiichukproject to javafx.fxml;
    exports com.example.poryadnyygordiichukproject;
}