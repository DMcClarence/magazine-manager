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
}
