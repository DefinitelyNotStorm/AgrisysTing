package dk.agrisysting.agrisysting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 500, 400);

        stage.setTitle("Agrisys PPT");
        stage.setScene(scene);
        stage.show();
    }
}