/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package magazineservice;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import magazineservice.controller.MenuBarController;

/**
 *
 * @author 34085068
 */
public class MagazineService extends Application {
    // private static MagazineServiceDatabase db;
    
    @Override
    public void start(Stage primaryStage) {        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/ViewScene.fxml"));
            Scene scene = new Scene(root);
            
                MenuBarController.setStage(primaryStage);

            primaryStage.setTitle("Magazine Manager");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
