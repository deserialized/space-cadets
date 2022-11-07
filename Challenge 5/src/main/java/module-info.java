module com.example.challenge_5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.challenge_5 to javafx.fxml;
    exports com.example.challenge_5;
}