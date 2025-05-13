/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private Menu fileMenu;
    
    @FXML
    private Menu createMenu;
    
    @FXML
    private Menu customerMenu;
    
    @FXML
    private Menu magazineMenu;

    @FXML
    private MenuItem newServiceOption;
    
    @FXML
    private MenuItem loadServiceOption;
    
    @FXML
    private MenuItem saveServiceOption;
    
    @FXML
    private MenuItem associateOption;
    
    @FXML
    private MenuItem payingOption;
    
    @FXML
    private MenuItem supplementOption;
    
    private static Stage primaryStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    public void CreateNewFile(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File((System.getProperty("user.dir") + "/magazine-services")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("DAT Files", "*.dat"));
        
        File db = fileChooser.showSaveDialog(primaryStage);
        if(db != null) {
            db.setWritable(true);
            
            try {
                this.OpenCreateMainMagazineWindow();
                db.createNewFile();
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    
    @FXML
    public void OpenExistingFile(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File((System.getProperty("user.dir") + "/magazine-services")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("DAT Files", "*.dat"));
        fileChooser.showOpenDialog(primaryStage);
    }
    
    @FXML
    public void OpenCreateAssociateCustomerWindow(ActionEvent e) {
        try {
            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Create Customer (Magazine Manager)");
            Parent root = FXMLLoader.load(getClass().getResource("../view/EditableAssociateCustomerForm.fxml"));
            Scene scene = new Scene(root, 350, 350);
            stage.setScene(scene);
            stage.showAndWait();   
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    @FXML
    public void OpenCreatePayingCustomerWindow(ActionEvent e) {
        try {
            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Create Customer (Magazine Manager)");
            Parent root = FXMLLoader.load(getClass().getResource("../view/EditablePayingCustomerForm.fxml"));
            Scene scene = new Scene(root, 350, 350);
            stage.setScene(scene);
            stage.showAndWait();   
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    @FXML
    public void OpenCreateSupplementMagazineWindow(ActionEvent e) {
        try {
            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Create Magazine (Magazine Manager)");
            Parent root = FXMLLoader.load(getClass().getResource("../view/EditableSupplementMagazineForm.fxml"));
            Scene scene = new Scene(root, 350, 350);
            stage.setScene(scene);
            stage.showAndWait();   
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public void OpenCreateMainMagazineWindow() {
        try {
            Stage stage = new Stage();
            stage.initOwner(primaryStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Create Magazine (Magazine Manager)");
            Parent root = FXMLLoader.load(getClass().getResource("../view/EditableMainMagazineForm.fxml"));
            Scene scene = new Scene(root, 350, 350);
            stage.setScene(scene);
            stage.showAndWait();   
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public static void setStage(Stage stage) {
        MenuBarController.primaryStage = stage;
    }
}
