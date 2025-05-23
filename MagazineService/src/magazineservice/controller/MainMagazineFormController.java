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
import magazineservice.model.MagazineServiceDatabase;
import magazineservice.model.MainMagazine;
import magazineservice.view.EditableForm;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class MainMagazineFormController implements Initializable, EditableForm {
    @FXML
    private BorderPane viewableForm;
    
    @FXML
    private VBox fieldContainer;
    
    @FXML
    private HBox titleField;

    @FXML
    private Label titleFieldLabel;

    @FXML
    private Label displayedTitle;
    
    @FXML
    private TextField titleFieldTextBox;

    @FXML
    private HBox weeklyCostField;
    
    @FXML
    private Label weeklyCostFieldLabel;

    @FXML
    private Label displayedWeeklyCost;
    
    @FXML
    private TextField weeklyCostFieldTextBox;

    @FXML
    private HBox typeField;

    @FXML
    private Label typeFieldLabel;

    @FXML
    private Label mainLabel;
    
    private MainMagazine mainMagazineRef = null;
    
    private TreeItem<String> treeItemRef = null;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        titleFieldTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
//            if(newValue != null && newValue != oldValue){
//                displayedTitle.setText(titleFieldTextBox.getText());
//                if(treeItemRef != null) {
//                    treeItemRef.setValue(titleFieldTextBox.getText());  
//                }
//            }}
//        );
//        
//        displayedTitle.textProperty().addListener((observable, oldValue, newValue) -> {
//            if(newValue != null && newValue != oldValue){
//                titleFieldTextBox.setText(displayedTitle.getText());
//                if(treeItemRef != null) {
//                    treeItemRef.setValue(displayedTitle.getText());   
//                }
//            }}
//        );
//        
//        weeklyCostFieldTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
//            if(newValue != null && newValue != oldValue){
//                displayedWeeklyCost.setText(weeklyCostFieldTextBox.getText());
//            }}
//        );
//        
//        displayedWeeklyCost.textProperty().addListener((observable, oldValue, newValue) -> {
//            if(newValue != null && newValue != oldValue){
//                weeklyCostFieldTextBox.setText(displayedWeeklyCost.getText());
//            }}
//        );
    }
    
    /**
     *
     * @param mainMagazine
     */
    public void setMainMagazineRef(MainMagazine mainMagazine) {
        this.mainMagazineRef = mainMagazine;
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
        if(mainMagazineRef != null) {
            displayedTitle.setText(mainMagazineRef.getTitle());
            displayedWeeklyCost.setText(String.format("%.2f", mainMagazineRef.getWeeklyCost()));           
        }

    }
    
    /**
     *
     */
    public void updateRefData() {
        if(mainMagazineRef != null) {
            mainMagazineRef.setName(titleFieldTextBox.getText());
            mainMagazineRef.setWeeklyCost(Double.parseDouble(weeklyCostFieldTextBox.getText()));
        }
    }
    
    /**
     *
     * @param editable
     */
    @Override
    public void setEditable(boolean editable) {
        // Hide Displayed Title, Show Title Text Field
        displayedTitle.setManaged(!(editable));
        displayedTitle.setVisible(!(editable));
        titleFieldTextBox.setManaged(editable);
        titleFieldTextBox.setVisible(editable);
        
        // Hide Displayed Weekly Cost, Show Weekly Cost Text Field
        displayedWeeklyCost.setManaged(!(editable));
        displayedWeeklyCost.setVisible(!(editable));
        weeklyCostFieldTextBox.setManaged(editable);
        weeklyCostFieldTextBox.setVisible(editable);
        
        if(editable && mainMagazineRef != null) {
            titleFieldTextBox.setText(mainMagazineRef.getTitle());
            weeklyCostFieldTextBox.setText(String.format("%.2f", mainMagazineRef.getWeeklyCost()));
        }
        else if(!(editable) && mainMagazineRef != null) {
            if(MagazineService.getDBController().getSupplementMagazine(titleFieldTextBox.getText()) != null) {
                titleFieldTextBox.setText(displayedTitle.getText());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Title Already in Use.");
                alert.showAndWait();
            } 
            else {
                displayedTitle.setText(titleFieldTextBox.getText());
                if(treeItemRef != null) {
                    treeItemRef.setValue(displayedTitle.getText());   
                }
            }
            displayedWeeklyCost.setText(String.format("%.2f", Double.valueOf(weeklyCostFieldTextBox.getText())));
        }
        
        // Update Object in Database
        updateRefData();
    }
    
    /**
     *
     * @return
     */
    public MagazineServiceDatabase createMagazineService() {
        if((titleFieldTextBox.getText() == null || titleFieldTextBox.getText().strip().isEmpty()) ||
           (weeklyCostFieldTextBox.getText() == null || weeklyCostFieldTextBox.getText().strip().isEmpty())) {
            return null;
        }
        
        return new MagazineServiceDatabase(titleFieldTextBox.getText(), 
                                            Double.parseDouble(weeklyCostFieldTextBox.getText()));
    }
    
    /**
     *
     */
    public void clearFields() {
        titleFieldTextBox.clear();
        weeklyCostFieldTextBox.clear();
    }
}
