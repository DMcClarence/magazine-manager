/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magazineservice.MagazineService;
import magazineservice.model.SupplementMagazine;
import magazineservice.view.EditableForm;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class SupplementMagazineFormController implements Initializable, EditableForm {
    @FXML
    private BorderPane editableForm;
    
    @FXML
    private VBox fieldContainer;
    
    @FXML
    private HBox titleField;
    
    @FXML
    private TextField titleFieldTextBox;

    @FXML
    private Label titleFieldLabel;
    
    @FXML
    private Label displayedTitle;
    
    @FXML
    private HBox weeklyCostField;

    @FXML
    private TextField weeklyCostFieldTextBox;
    
    @FXML
    private Label displayedWeeklyCost;

    @FXML
    private Label weeklyCostFieldLabel;
    
    @FXML
    private HBox typeField;

    @FXML
    private Label typeFieldLabel;

    @FXML
    private Label supplementLabel;
    
    @FXML
    private HBox numOfSubsField;
    
    @FXML
    private Label numOfSubsFieldLabel;
    
    @FXML
    private Label displayedCount;
    
    private SupplementMagazine supplementMagazineRef = null;
    
    private TreeItem<String> treeItemRef = null;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    /**
     *
     * @param supplementMagazine
     */
    public void setSupplementMagazineRef(SupplementMagazine supplementMagazine) {
        this.supplementMagazineRef = supplementMagazine;
    }
    
    /**
     *
     * @param treeItem
     */
    public void setTreeItemRef(TreeItem<String> treeItem) {
        this.treeItemRef = treeItem;
    }
    
    /**
     *
     */
    public void displayData() {
        if(supplementMagazineRef != null) {
            displayedTitle.setText(supplementMagazineRef.getTitle());
            displayedWeeklyCost.setText(String.format("%.2f", supplementMagazineRef.getWeeklyCost()));  
            displayedCount.setText(Integer.toString(MagazineService.getDBController().getNumOfSubbedCustomers(supplementMagazineRef.getTitle())));
        }

    }
    
    /**
     *
     */
    public void updateRefData() {
        if(supplementMagazineRef != null) {
            // supplementMagazineRef.setName(titleFieldTextBox.getText());
            supplementMagazineRef.setWeeklyCost(Double.parseDouble(weeklyCostFieldTextBox.getText()));
        }
    }
    
    /**
     *
     * @param editable
     */
    @Override
    public void setEditable(boolean editable) {
        // Hide Displayed Title, Show Title Text Field
        if(supplementMagazineRef == null) {
            displayedTitle.setManaged(!(editable));
            displayedTitle.setVisible(!(editable));
            titleFieldTextBox.setManaged(editable);
            titleFieldTextBox.setVisible(editable); 
        }
        
        // Hide Displayed Weekly Cost, Show Weekly Cost Text Field
        displayedWeeklyCost.setManaged(!(editable));
        displayedWeeklyCost.setVisible(!(editable));
        weeklyCostFieldTextBox.setManaged(editable);
        weeklyCostFieldTextBox.setVisible(editable);
        
        if(editable && supplementMagazineRef != null) {
            titleFieldTextBox.setText(supplementMagazineRef.getTitle());
            weeklyCostFieldTextBox.setText(String.format("%.2f", supplementMagazineRef.getWeeklyCost()));
        }
        else if(!(editable) && supplementMagazineRef != null) {
            displayedTitle.setText(titleFieldTextBox.getText());
            try {
                displayedWeeklyCost.setText(String.format("%.2f", Double.valueOf(weeklyCostFieldTextBox.getText())));
            }
            catch(NumberFormatException nfe) {
                weeklyCostFieldTextBox.setText(displayedWeeklyCost.getText());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Weekly Cost Update Failed.");
                alert.showAndWait();
            }
        }
        
        // Update Object in Database
        updateRefData();
    }
    
    /**
     *
     * @return
     */
    public int createSupplementMagazine() {
        if((titleFieldTextBox.getText() == null || titleFieldTextBox.getText().strip().isEmpty()) ||
           (weeklyCostFieldTextBox.getText() == null || weeklyCostFieldTextBox.getText().strip().isEmpty())) {
            return 1;
        }
        
        if(MagazineService.getDBController().getSupplementMagazine(titleFieldTextBox.getText()) != null ||
                titleFieldTextBox.getText().matches(MagazineService.getDBController().getMainMagazine().getTitle())) {
            return 2;
        }
        
        try {
           SupplementMagazine supplementMagazine = new SupplementMagazine(titleFieldTextBox.getText(), 
                                                                        Double.parseDouble(weeklyCostFieldTextBox.getText())); 
           MagazineService.getDBController().addSupplementMagazine(supplementMagazine);
        }
        catch(NumberFormatException nfe) {
            return 3;
        }
        catch(IllegalArgumentException iae) {
            return 3;
        }
        
        return 0;
    }
    
    /**
     *
     */
    public void clearFields() {
        titleFieldTextBox.clear();
        weeklyCostFieldTextBox.clear();
    }   
}
