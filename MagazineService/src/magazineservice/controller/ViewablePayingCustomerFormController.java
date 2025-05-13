/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class ViewablePayingCustomerFormController implements Initializable {
    @FXML
    private BorderPane viewableForm;
    
    @FXML
    private HBox fieldContainer;
    
    @FXML
    private HBox nameField;

    @FXML
    private Label nameFieldLabel;

    @FXML
    private Label displayedName;

    @FXML
    private HBox emailField;

    @FXML
    private Label displayedEmail;

    @FXML
    private HBox typeField;

    @FXML
    private Label typeFieldLabel;

    @FXML
    private Label payingLabel;
    
    @FXML
    private HBox paymentMethodLabel;
    
    @FXML
    private Label paymentMethodFieldLabel;
    
    @FXML
    private Label paymentMethodChoice;
    
    @FXML
    private Button paymentDetailsButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
