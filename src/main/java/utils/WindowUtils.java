package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class WindowUtils {

    public static void updateWindow(String address, Node button) {
        Stage stage = (Stage) button.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WindowUtils.class.getResource("../" + address));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(loader.getRoot()));
        stage.centerOnScreen();
        stage.show();
    }

    public static void openNewWindow(String address, Button button) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WindowUtils.class.getResource("../" + address));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();

        stage.setTitle("Company");
        stage.setResizable(false);
        stage.setScene(new Scene(loader.getRoot()));
        stage.centerOnScreen();
        stage.show();
    }

    public static void closeWindow(Button button) {
        button.getScene().getWindow().hide();
    }
}
