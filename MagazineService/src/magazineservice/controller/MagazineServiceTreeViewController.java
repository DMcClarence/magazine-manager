/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import magazineservice.MagazineService;
import magazineservice.model.AssociateCustomer;
import magazineservice.model.Customer;
import magazineservice.model.PayingCustomer;
import magazineservice.model.SupplementMagazine;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class MagazineServiceTreeViewController implements Initializable {
    @FXML
    private TreeView<String> treeView;

    @FXML
    private TreeItem<String> root;
    
    @FXML
    private TreeItem<String> customersHeader;
    
    @FXML
    private TreeItem<String> payingHeader;
    
    @FXML
    private TreeItem<String> associatesHeader;
    
    @FXML
    private TreeItem<String> magazinesHeader;
    
    @FXML
    private TreeItem<String> mainHeader;
    
    @FXML
    private TreeItem<String> supplementsHeader;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void update() {
        for(Customer c : MagazineService.getDBController().getAllCustomers()) {
            TreeItem<String> customer = new TreeItem<String>(c.getEmail());
            if(c instanceof PayingCustomer) {
                if(!payingHeader.getChildren().contains(customer)) {
                    payingHeader.getChildren().add(customer);
                }
            }
            else if(c instanceof AssociateCustomer) {
                if(!associatesHeader.getChildren().contains(customer)) {
                    associatesHeader.getChildren().add(customer);
                }
            }
        }
        
        TreeItem<String> mainMagazine = new TreeItem<String>(MagazineService.getDBController().getMainMagazine().getTitle());
        if(!mainHeader.getChildren().contains(mainMagazine)) {
            mainHeader.getChildren().add(mainMagazine);
        }
        
        
        for(SupplementMagazine sm : MagazineService.getDBController().getAllSupplementMagazines()) {
            TreeItem<String> supplementMagazine = new TreeItem<String>(sm.getTitle());
            
            if(!supplementsHeader.getChildren().contains(supplementMagazine)) {
                supplementsHeader.getChildren().add(supplementMagazine);
            }
        }
        
        treeView.refresh();
    }
    
    public TreeView<String> getTreeView() {
        return this.treeView;
    }
    
    public TreeItem<String> getMainHeader() {
        return this.mainHeader;
    }
    
    public TreeItem<String> getSupplementsHeader() {
        return this.supplementsHeader;
    }
    
    public TreeItem<String> getPayingHeader() {
        return this.payingHeader;
    }
    
    public TreeItem<String> getAssociatesHeader() {
        return this.associatesHeader;
    }
    
    public void clearTreeView() {
        mainHeader.getChildren().clear();
        supplementsHeader.getChildren().clear();
        payingHeader.getChildren().clear();
        associatesHeader.getChildren().clear();
        
        treeView.refresh();
    }
}
