module com.example.recu3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mariadb.jdbc;


    opens com.example.recu3 to javafx.fxml;
    exports com.example.recu3;
}