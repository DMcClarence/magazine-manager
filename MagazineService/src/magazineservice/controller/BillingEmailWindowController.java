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
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    /**`
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && newValue != oldValue) {
                if(treeView.getSelectionModel().getSelectedItem().isLeaf()) {
                    try {
                        // get email history for customer
                        HashMap<YearMonth, String> customerHistory = MagazineService.getDBController().getBillingHistoryForCustomer(treeView.getSelectionModel().getSelectedItem().getParent().getValue());
                        if(customerHistory.containsKey(YearMonth.parse(treeView.getSelectionModel().getSelectedItem().getValue()))) {
                            billingEmail.setText(customerHistory.get(YearMonth.parse(treeView.getSelectionModel().getSelectedItem().getValue())));
                        }
                        else {
                            billingEmail.setText(null);
                        }
                    }
                    catch(DateTimeParseException dtpe) {
                        // Not a email header, show nothing.
                        billingEmail.setText(null);
                    }
                }
                else {
                    billingEmail.setText(null);
                }
            }
        });
        
        Task<Void> constructTreeViewTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for(PayingCustomer pc : MagazineService.getDBController().getAllPayingCustomers()) {
                    HashMap<YearMonth, String> customerHistory = MagazineService.getDBController().getBillingHistoryForCustomer(pc.getEmail());

                    if(customerHistory != null) {
                        TreeItem<String> customer = new TreeItem<String>(pc.getEmail());

                        for(YearMonth ym : customerHistory.keySet()) {
                            customer.getChildren().add(new TreeItem<String>(ym.toString()));
                        }

                        Platform.runLater(() -> customersHeader.getChildren().add(customer));
                    }
                }
                
                return null;
            }
        };
        
        new Thread(constructTreeViewTask).start();
    }   
}
