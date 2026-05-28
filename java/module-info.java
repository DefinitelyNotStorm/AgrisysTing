module dk.agrisysting.agrisysting {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens dk.agrisysting.agrisysting to javafx.fxml;
    opens dk.agrisysting.agrisysting.controller to javafx.fxml;

    exports dk.agrisysting.agrisysting;
    exports dk.agrisysting.agrisysting.controller;
}