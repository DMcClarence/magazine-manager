/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import magazineservice.MagazineService;
import magazineservice.model.PayingCustomer;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class BillingEmailWindowController implements Initializable {
    @FXML
    private TreeView<String> treeView;
    
    @FXML
    private TreeItem<String> root;
    
    @FXML
    private TreeItem<String> customersHeader;
    
    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Label billingEmail;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for(PayingCustomer pc : MagazineService.getDBController().getAllPayingCustomers()) {
            HashMap<YearMonth, String> temp = MagazineService.getDBController().getBillingHistoryForCustomer(pc.getEmail());
            
            if(temp != null) {
                TreeItem<String> customer = new TreeItem<String>(pc.getEmail());
                customersHeader.getChildren().add(customer);
                
                for(YearMonth ym : temp.keySet()) {
                    customer.getChildren().add(new TreeItem<String>(ym.toString()));
                }
            }
        }
        
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && newValue != oldValue) {
                if(treeView.getSelectionModel().getSelectedItem().isLeaf()) {
                    try {
                        HashMap<YearMonth, String> temp = MagazineService.getDBController().getBillingHistoryForCustomer(treeView.getSelectionModel().getSelectedItem().getParent().getValue());
                        if(temp.containsKey(YearMonth.parse(treeView.getSelectionModel().getSelectedItem().getValue()))) {
                            billingEmail.setText(temp.get(YearMonth.parse(treeView.getSelectionModel().getSelectedItem().getValue())));
                        }
                        else {
                            billingEmail.setText(null);
                        }
                    }
                    catch(DateTimeParseException dtpe) {
                        billingEmail.setText(null);
                    }
                }

            }
        });
    }   
}
