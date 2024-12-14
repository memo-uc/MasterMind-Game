package gamePackage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
public class Main extends Application {
    public void start(Stage primaryStage) {
        GameControler controler = new GameControler();
        primaryStage.setTitle("MasterMind Game");
        primaryStage.setResizable(false);
        controler.startTimer();
        VBox Layout = controler.createGameLayout();
        Scene scene = new Scene(Layout,800 ,600);
        scene.getStylesheets().add(getClass().getResource("/gamePackage/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
    launch(args);
    }
}