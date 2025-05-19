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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import magazineservice.MagazineService;
import magazineservice.model.MagazineServiceDatabase;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class LaunchController implements Initializable {
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private VBox vbox;
    
    @FXML
    private Label title;
    
    @FXML
    private HBox buttons;
    
    @FXML
    private Button createButton;
    
    @FXML
    private Button loadButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    public void createNewFile(ActionEvent e) {
        Alert fileAlert;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File((System.getProperty("user.dir") + "/magazine-services")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("DAT Files", "*.dat"));
        
        MagazineService.setDBFile(fileChooser.showSaveDialog((Stage)vbox.getScene().getWindow()));
        if(MagazineService.getDBFile() != null) {
            MagazineService.getDBFile().setWritable(true);
            
            try {
                MagazineService.setDatabase(this.openCreateMainMagazineWindow());
                
                if(MagazineService.getDatabase() != null) {
                    MagazineService.setDBController(new MagazineServiceDatabaseController(MagazineService.getDatabase()));
                    MagazineService.getDBFile().createNewFile();
                    MagazineService.getDBController().serializeDB(MagazineService.getDBFile());
                    fileAlert = new Alert(Alert.AlertType.INFORMATION);
                    fileAlert.setContentText("File Created");
                }
                else {
                    fileAlert = new Alert(Alert.AlertType.ERROR);
                    fileAlert.setContentText("File Not Created");
                }
                
                fileAlert.showAndWait();
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
            }
            
            if(MagazineService.getDatabase() != null) {
                ((Stage)vbox.getScene().getWindow()).close();
            }
        }
    }
    
    public MagazineServiceDatabase openCreateMainMagazineWindow() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage)vbox.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Create Magazine (Magazine Manager)");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CreateMainMagazineWindow.fxml"));
            Parent root = loader.load();
            CreateMainMagazineWindowController cmmwc = loader.getController();
            
            Scene scene = new Scene(root, 350, 350);
            stage.setScene(scene);
            stage.showAndWait();
            
            return cmmwc.getMagazineServiceDatabase();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        
        return null;
    }
    
    @FXML
    public void openExistingFile(ActionEvent e) {
        Alert fileAlert;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File((System.getProperty("user.dir") + "/magazine-services")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("DAT Files", "*.dat"));
        
        MagazineService.setDBFile(fileChooser.showOpenDialog((Stage)vbox.getScene().getWindow()));
        if(MagazineService.getDBFile() != null) {
            MagazineService.setDBController(new MagazineServiceDatabaseController());
            MagazineService.setDatabase(MagazineService.getDBController().deserializeDB(MagazineService.getDBFile()));
            if(MagazineService.getDatabase() != null) {
                fileAlert = new Alert(Alert.AlertType.INFORMATION);
                fileAlert.setContentText("File Loaded Successfully");
            }
            else {
                fileAlert = new Alert(Alert.AlertType.ERROR);
                fileAlert.setContentText("File Not Loaded");
            }
                
            fileAlert.showAndWait();
            
            if(MagazineService.getDatabase() != null) {
                ((Stage)vbox.getScene().getWindow()).close();
            }
        }
    }
}
