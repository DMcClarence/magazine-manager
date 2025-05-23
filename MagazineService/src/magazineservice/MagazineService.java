/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package magazineservice;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import magazineservice.controller.LaunchController;
import magazineservice.controller.MagazineServiceDatabaseController;
import magazineservice.controller.ViewSceneController;
import magazineservice.model.MagazineServiceDatabase;

/**
 *
 * @author 34085068
 */
public class MagazineService extends Application {
    private static MagazineServiceDatabase db = null;
    private static MagazineServiceDatabaseController dbController = null;
    private static File dbFile = null;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static MagazineServiceDatabase getDatabase() {
        return MagazineService.db;
    }
    
    public static void setDatabase(MagazineServiceDatabase db) {
        MagazineService.db = db;
    }
    
    public static MagazineServiceDatabaseController getDBController() {
        return MagazineService.dbController;
    }
    
    public static void setDBController(MagazineServiceDatabaseController dbController) {
        MagazineService.dbController = dbController;
    }
    
    public static void setDBFile(File dbFile) {
        MagazineService.dbFile = dbFile;
    }
    
    public static File getDBFile() {
        return MagazineService.dbFile;
    }
    @Override
    public void start(Stage primaryStage) {        
        try {
            FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("view/ViewScene.fxml"));
            Parent root = viewLoader.load();
            ViewSceneController vsc = viewLoader.getController();
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Magazine Manager");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(e -> {
                if(db != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("""
                                                             Would you like to save any changes before exiting?
                                                             The program will exit regardless.""");
                    Optional<ButtonType> pressed = alert.showAndWait();
                    ButtonType button = pressed.orElse(ButtonType.CANCEL);
                    if(button == ButtonType.OK) {
                        if(dbFile != null) {
                            MagazineService.getDBController().serializeDB(MagazineService.getDBFile());
                        }
                        Platform.exit();
                    }
                }
                else {
                    Platform.exit();
                }
            });
            primaryStage.show();
            
            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Magazine Manager");
            
            FXMLLoader launchLoader = new FXMLLoader(getClass().getResource("view/Launch.fxml"));
            Parent launchRoot = launchLoader.load();
            LaunchController lc = launchLoader.getController();
            
            Scene launch = new Scene(launchRoot);
            stage.setScene(launch);
            stage.setOnCloseRequest(e -> {
                primaryStage.close();
            });
            stage.showAndWait();
            
            if(db != null) {
                vsc.getTreeViewController().update();
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
