/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import magazineservice.MagazineService;
import magazineservice.model.MagazineServiceDatabase;
import magazineservice.model.PayingCustomer;
import magazineservice.service.PaymentEmailGenerator;

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
    
    private MagazineServiceTreeViewController treeViewControllerRef;

    /**
     * Initializes the treeViewControllerRef class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     *
     * @param e
     */
    @FXML
    public void createNewFile(ActionEvent e) {
        Alert fileAlert;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File((System.getProperty("user.dir") + "/magazine-services")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("DAT Files", "*.dat"));
        
        MagazineService.setDBFile(fileChooser.showSaveDialog((Stage)menuBar.getScene().getWindow()));
        if(MagazineService.getDBFile() != null) {
            MagazineService.getDBFile().setWritable(true);
            
            try {
                MagazineService.setDatabase(this.openCreateMainMagazineWindow());
                
                if(MagazineService.getDatabase() != null) {
                    MagazineService.setDBController(new MagazineServiceDatabaseController(MagazineService.getDatabase()));
                    MagazineService.getDBFile().createNewFile();
                    MagazineService.getDBController().serializeDB(MagazineService.getDBFile());
                    treeViewControllerRef.clearTreeView();
                    treeViewControllerRef.update();
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
        }
    }
    
    /**
     *
     * @param e
     */
    @FXML
    public void openExistingFile(ActionEvent e) {
        Alert fileAlert;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File((System.getProperty("user.dir") + "/magazine-services")));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("DAT Files", "*.dat"));
        
        MagazineService.setDBFile(fileChooser.showOpenDialog((Stage)menuBar.getScene().getWindow()));
        if(MagazineService.getDBFile() != null) {
            MagazineService.setDBController(new MagazineServiceDatabaseController());
            MagazineService.setDatabase(MagazineService.getDBController().deserializeDB(MagazineService.getDBFile()));
            if(MagazineService.getDatabase() != null) {
                treeViewControllerRef.clearTreeView();
                treeViewControllerRef.update();
                fileAlert = new Alert(Alert.AlertType.INFORMATION);
                fileAlert.setContentText("File Loaded Successfully");
            }
            else {
                fileAlert = new Alert(Alert.AlertType.ERROR);
                fileAlert.setContentText("File Not Loaded");
            }
                
            fileAlert.showAndWait();
        }
    }
    
    /**
     *
     * @param e
     */
    @FXML
    public void openCreateAssociateCustomerWindow(ActionEvent e) {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage)menuBar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Create Customer (Magazine Manager)");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CreateAssociateCustomerWindow.fxml"));
            Parent root = loader.load();
            CreateAssociateCustomerWindowController cacwc = loader.getController();
            
            Scene scene = new Scene(root, 350, 350);
            stage.setScene(scene);
            stage.showAndWait();
            
            Alert fileAlert = new Alert(Alert.AlertType.ERROR);
            int status = cacwc.createAssociateCustomer();
            switch(status) {
                case 0: {
                   treeViewControllerRef.clearTreeView();
                   treeViewControllerRef.update();
                   fileAlert.setAlertType(Alert.AlertType.INFORMATION);
                   fileAlert.setContentText("Customer Created Successfully"); 
                   break;
                }
                case 1: {
                    fileAlert.setContentText("Customer Creation Failed. No fields can be empty.");
                    break;
                }
                case 2: {
                    fileAlert.setContentText("Customer Creation Failed. Email already in use.");
                    break;
                }
                case 3: {
                    fileAlert.setContentText("Customer Creation Failed. \nThere was an error in the data entered.");
                    break;
                }
                default: {
                    fileAlert.setContentText("Customer Creation Failed. An error occured.");
                    break;
                }
            }
            
            fileAlert.showAndWait();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     *
     * @param e
     */
    @FXML
    public void openCreatePayingCustomerWindow(ActionEvent e) {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage)menuBar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            // stage.setAlwaysOnTop(true);
            stage.setTitle("Create Customer (Magazine Manager)");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CreatePayingCustomerWindow.fxml"));
            Parent root = loader.load();
            CreatePayingCustomerWindowController cpcwc = loader.getController();
            
            Scene scene = new Scene(root, 350, 350);
            stage.setScene(scene);
            stage.showAndWait();
            
            Alert fileAlert = new Alert(Alert.AlertType.ERROR);
            
            int status = cpcwc.createPayingCustomer();
            switch(status) {
                case 0: {
                   treeViewControllerRef.clearTreeView();
                   treeViewControllerRef.update();
                   fileAlert.setAlertType(Alert.AlertType.INFORMATION);
                   fileAlert.setContentText("Customer Created Successfully"); 
                   break;
                }
                case 1: {
                    fileAlert.setContentText("Customer Creation Failed. No fields can be empty.");
                    break;
                }
                case 2: {
                    fileAlert.setContentText("Customer Creation Failed. Email already in use.");
                    break;
                }
                case 3: {
                    fileAlert.setContentText("Customer Creation Failed.\nEmail or Account Number (8 digit number) is invalid");
                    break; 
                }
                case 4: {
                    fileAlert.setContentText("""
                                             Customer Creation Failed. 
                                             Card Number or Expiry is invalid. 
                                             Card number must be a 16 digit number 
                                             and Expiry must be in the future.""");
                    break;
                    
                }
                default: {
                    fileAlert.setContentText("Customer Creation Failed. An error occured.");
                    break;
                }
            }
            
            fileAlert.showAndWait();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     *
     * @param e
     */
    @FXML
    public void openCreateSupplementMagazineWindow(ActionEvent e) {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage)menuBar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Create Magazine (Magazine Manager)");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CreateSupplementMagazineWindow.fxml"));
            Parent root = loader.load();
            CreateSupplementMagazineWindowController csmwc = loader.getController();
            
            Scene scene = new Scene(root, 350, 350); 
            stage.setScene(scene);
            stage.showAndWait();
            
            Alert fileAlert = new Alert(Alert.AlertType.ERROR);
            int status = csmwc.createSupplementMagazine();
            switch(status) {
                case 0: {
                   treeViewControllerRef.clearTreeView();
                   treeViewControllerRef.update();
                   fileAlert.setAlertType(Alert.AlertType.INFORMATION);
                   fileAlert.setContentText("Supplement Created Successfully"); 
                   break;
                }
                case 1: {
                    fileAlert.setContentText("Supplement Creation Failed. No fields can be empty.");
                    break;
                }
                case 2: {
                    fileAlert.setContentText("Supplement Creation Failed. Title already in use.");
                    break;
                }
                case 3: {
                    fileAlert.setContentText("Supplement Creation Failed. \nThere is a problem with the weekly cost.");
                    break;
                }
                default: {
                    fileAlert.setContentText("Supplement Creation Failed. An error occured.");
                    break;
                }
            }
            
            fileAlert.showAndWait();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     *
     * @return
     */
    public MagazineServiceDatabase openCreateMainMagazineWindow() {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage)menuBar.getScene().getWindow());
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
    
    /**
     *
     * @param e
     */
    public void showBillingInformationWindow(ActionEvent e) {
        try {
            Stage stage = new Stage();
            stage.initOwner((Stage)menuBar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setAlwaysOnTop(true);
            stage.setTitle("Billing Emails (Magazine Manager)");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/BillingEmailWindow.fxml"));
            Parent root = loader.load();
            BillingEmailWindowController bewc = loader.getController();
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch(IOException ioe) {
            
        }
    }
            
    /**
     *
     */
    @FXML
    public void saveDBtoFile() {
        if(MagazineService.getDBFile() != null) {
            MagazineService.getDBController().serializeDB(MagazineService.getDBFile());
            Alert fileAlert = new Alert(Alert.AlertType.INFORMATION);
            fileAlert.setContentText("File Saved.");
            fileAlert.showAndWait();
        }
    }
    
    /**
     *
     * @param controller
     */
    public void setTreeViewControllerRef(MagazineServiceTreeViewController controller) {
        this.treeViewControllerRef = controller;
    }
    
    /**
     *
     */
    @FXML
    public void generateMonthlyEmails() {
        YearMonth nextMonth = YearMonth.now().plusMonths(1);
        PaymentEmailGenerator peg = new PaymentEmailGenerator();
        peg.setPeriod(nextMonth);
        
        Task<Void> generateEmailsTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for(PayingCustomer pc : MagazineService.getDBController().getAllPayingCustomers()) {
                    peg.setRecipient(pc);
                    MagazineService.getDBController().saveEmail(pc.getEmail(), nextMonth, peg.generate());
                    peg.setRecipient(null);
                }
                
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Emails Generated");
                    alert.showAndWait();
                });
                    
                return null;
            }
        };
        
        new Thread(generateEmailsTask).start();
    }
}
