/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author 340850068
 */
public class ViewableAssociateCustomerFormController implements Initializable {
   @FXML
    private BorderPane viewableForm;

    @FXML
    private VBox fieldContainer;

    @FXML
    private HBox nameField;
    
    @FXML
    private Label nameFieldLabel;
    
    @FXML
    private Label displayedName;

    @FXML
    private HBox emailField;
    
    @FXML
    private Label emailFieldLabel;
    
    @FXML
    private Label displayedEmail;

    @FXML
    private HBox typeField;
    
    @FXML
    private Label typeFieldLabel;
    
    @FXML
    private Label associateType;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
