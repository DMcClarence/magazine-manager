/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magazineservice.MagazineService;
import magazineservice.model.AssociateCustomer;
import magazineservice.model.PayingCustomer;
import magazineservice.view.EditableForm;

/**
 * FXML Controller class
 *
 * @author 340850068
 */
public class AssociateCustomerFormController implements Initializable, EditableForm{
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
    private TextField nameFieldTextBox;

    @FXML
    private HBox emailField;

    @FXML
    private Label displayedEmail;
    
    @FXML
    private TextField emailFieldTextBox;

    @FXML
    private HBox typeField;

    @FXML
    private Label typeFieldLabel;

    @FXML
    private Label associateLabel;

    @FXML
    private ComboBox<String> benefactorChoices;
    
    @FXML
    private SupplementMagazineSelectorController supplementListController;
    
    private AssociateCustomer associateCustomerRef = null;
    
    private TreeItem<String> treeItemRef = null;
    
    private PayingCustomer benefactorRef = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for(PayingCustomer pc : MagazineService.getDBController().getAllPayingCustomers()) {
            benefactorChoices.getItems().add(pc.getEmail());
        }
        
        benefactorChoices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && newValue != oldValue) {
                try {
                    benefactorRef = (PayingCustomer)MagazineService.getDBController().getCustomer(benefactorChoices.getSelectionModel().getSelectedItem());   
                }
                catch(ClassCastException cce) {
                    cce.printStackTrace();
                }
            }
        });
    }
        
    public void setAssociateCustomerRef(AssociateCustomer associateCustomer) {
        this.associateCustomerRef = associateCustomer;
    }
    
    public void setTreeItemRef(TreeItem<String> treeItem) {
        this.treeItemRef = treeItem;
    }
    
    public void displayData() {
        if(associateCustomerRef != null) {
            displayedName.setText(associateCustomerRef.getName());
            displayedEmail.setText(associateCustomerRef.getEmail());
            benefactorChoices.getSelectionModel().select(associateCustomerRef.getBenefactor().getEmail());
            
            for(CheckBox cb : supplementListController.getSupplementList()) {
                if(associateCustomerRef.getSuppMags().contains(MagazineService.getDBController().getSupplementMagazine(cb.getText()))) {
                    cb.setSelected(true);
                }
                else {
                    cb.setSelected(false);
                }
            }
        }

    }
    
    public void updateRefData() {
        if(associateCustomerRef != null) {
            associateCustomerRef.setName(nameFieldTextBox.getText());
            associateCustomerRef.setEmail(emailFieldTextBox.getText());
            associateCustomerRef.setBenefactor(benefactorRef);
            
            for(CheckBox cb : supplementListController.getSupplementList()) {
                if(cb.isSelected()) {
                    MagazineService.getDBController().addToSubscription(cb.getText(), associateCustomerRef.getEmail());
                }
                else {
                    MagazineService.getDBController().removeFromSubscription(cb.getText(), associateCustomerRef.getEmail());
                }
            }
        }
    }
    
    @Override
    public void setEditable(boolean editable) {
        // Hide Displayed Title, Show Title Text Field
        displayedName.setManaged(!(editable));
        displayedName.setVisible(!(editable));
        nameFieldTextBox.setManaged(editable);
        nameFieldTextBox.setVisible(editable);
        
        // Hide Displayed Weekly Cost, Show Weekly Cost Text Field
        displayedEmail.setManaged(!(editable));
        displayedEmail.setVisible(!(editable));
        emailFieldTextBox.setManaged(editable);
        emailFieldTextBox.setVisible(editable);
        
        benefactorChoices.setDisable(!(editable));
        
        supplementListController.setEditable(editable);
        
        // Update Label/Text Field with Accurate Data for Selected Customer
        if(editable && associateCustomerRef != null) {
            nameFieldTextBox.setText(associateCustomerRef.getName());
            emailFieldTextBox.setText(associateCustomerRef.getEmail());
            benefactorChoices.getSelectionModel().select(associateCustomerRef.getBenefactor().getEmail());
            
            for(CheckBox cb : supplementListController.getSupplementList()) {
                if(associateCustomerRef.getSuppMags().contains(MagazineService.getDBController().getSupplementMagazine(cb.getText()))) {
                    cb.setSelected(true);
                }
                else {
                    cb.setSelected(false);
                }
            }
        }
        else if(!(editable) && associateCustomerRef != null) {
            displayedName.setText(nameFieldTextBox.getText());
            displayedEmail.setText(emailFieldTextBox.getText());
            try {
                benefactorRef = (PayingCustomer)MagazineService.getDBController().getCustomer(benefactorChoices.getSelectionModel().getSelectedItem());
            }
            catch(ClassCastException cce) {
                cce.printStackTrace();
            }
        }   
        
        // Update Object in Database
        updateRefData();
        System.out.println(associateCustomerRef);
    }
    
    public int createAssociateCustomer() {
        if((nameFieldTextBox.getText() == null || nameFieldTextBox.getText().strip().isEmpty()) ||
           (emailFieldTextBox.getText() == null || emailFieldTextBox.getText().strip().isEmpty()) || 
           (benefactorRef == null)) {
            return 1;
        }
        
        if(MagazineService.getDBController().getCustomer(emailFieldTextBox.getText()) != null) {
            return 2;
        }
        
        AssociateCustomer associateCustomer = new AssociateCustomer(nameFieldTextBox.getText(), emailFieldTextBox.getText(), 
                                                                    MagazineService.getDBController().getMainMagazine(), 
                                                                    benefactorRef);
        
        MagazineService.getDBController().addCustomer(associateCustomer);
        
        for(CheckBox cb : supplementListController.getSupplementList()) {
            if(cb.isSelected()) {
                MagazineService.getDBController().addToSubscription(cb.getText(), associateCustomer.getEmail());
            }
            else {
                MagazineService.getDBController().removeFromSubscription(cb.getText(), associateCustomer.getEmail());
            }
        }
        
        return 0;
    }
    
    public void clearFields() {
        nameFieldTextBox.clear();
        emailFieldTextBox.clear();
    } 
}
