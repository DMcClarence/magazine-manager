/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import magazineservice.model.AssociateCustomer;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class CreateAssociateCustomerWindowController implements Initializable {
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private ButtonBar createCancelBar;
    
    @FXML
    private Button createButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private AssociateCustomerFormController associateCustomerFormController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        associateCustomerFormController.setEditable(true);
    }
    
    @FXML
    public void onCreateButtonClicked(ActionEvent e) {
        Stage currentWindow = (Stage)createButton.getScene().getWindow();
        currentWindow.close();
    }
    
    @FXML
    public void onCancelButtonClicked(ActionEvent e) {
        Stage currentWindow = (Stage)cancelButton.getScene().getWindow();
        associateCustomerFormController.clearFields();
        currentWindow.close();
    }
    
    public int createAssociateCustomer() {        
        return associateCustomerFormController.createAssociateCustomer();
    }
}
