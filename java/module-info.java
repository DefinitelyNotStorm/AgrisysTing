module dk.agrisysting.agrisysting {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.agrisysting.agrisysting to javafx.fxml;
    exports dk.agrisysting.agrisysting;
}