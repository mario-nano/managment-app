import creators.SceneCreator;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    SceneCreator sc = new SceneCreator();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setScene(sc.createStartMenu(window));
        window.setTitle("Planetto");
        window.show();
    }
}
