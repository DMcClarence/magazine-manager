/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import magazineservice.MagazineService;
import magazineservice.model.AssociateCustomer;
import magazineservice.model.CreditCard;
import magazineservice.model.Customer;
import magazineservice.model.DirectDebit;
import magazineservice.model.PayingCustomer;
import magazineservice.view.EditableForm;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class ViewSceneController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private SplitPane layout;
    
    private ScrollPane scrollPane;

    @FXML
    private BorderPane formPane;

    private Button editButton;

    private Button deleteButton;

    private ButtonBar editBar;
    
    @FXML
    private MenuBarController menuBarController;
    
    @FXML
    private MagazineServiceTreeViewController treeViewController;
    
    private EditableForm formController;

    private ButtonBar doneBar;
    
    private Button done;
    
    private ButtonBar deleteBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doneBar = new ButtonBar();
        done = new Button("Done");      
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");
        editBar = new ButtonBar();
        deleteBar = new ButtonBar();
        scrollPane = new ScrollPane();
        
        BorderPane.setMargin(scrollPane, new Insets(5, 5, 5, 5));
        scrollPane.fitToWidthProperty().setValue(Boolean.TRUE);
        
        editBar.getButtons().add(editButton);
        editButton.setOnAction(e -> { 
            formController.setEditable(true);
            if(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getParent() == treeViewController.getMainHeader()) {
                formPane.setTop(null);
            }
            else {
                formPane.setTop(deleteBar);
            }
            formPane.setBottom(doneBar);
        });
        BorderPane.setMargin(editBar, new Insets(2, 10, 10, 10));
        
        deleteBar.getButtons().add(deleteButton);
        deleteButton.setOnAction(e -> {
            if(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getParent() == treeViewController.getPayingHeader()) {
                 try {
                    if(!((PayingCustomer)MagazineService.getDBController().getCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue())).getAssociates().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("""
                                             Deleting this Customer will result in 
                                             the deletion of all their Associates.
                                             Are you sure you want to continue?""");
                        Optional<ButtonType> pressed = alert.showAndWait();
                        ButtonType button = pressed.orElse(ButtonType.CANCEL);
                        if(button == ButtonType.OK) {
                            MagazineService.getDBController().removeCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue());
                            formPane.setTop(null);
                            formPane.setCenter(null);
                            formPane.setBottom(null);
                            formController = null;
                            treeViewController.getTreeView().getSelectionModel().clearSelection();
                            treeViewController.clearTreeView();
                            treeViewController.update();
                        }
                    }
                    else {
                        MagazineService.getDBController().removeCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue());
                        formPane.setTop(null);
                        formPane.setCenter(null);
                        formPane.setBottom(null);
                        formController = null;
                        treeViewController.getTreeView().getSelectionModel().clearSelection();
                        treeViewController.clearTreeView();
                        treeViewController.update();
                    }
                }
                catch(ClassCastException cce) {
                    cce.printStackTrace();
                }               
            }
            else if(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getParent() == treeViewController.getAssociatesHeader()) {
                MagazineService.getDBController().removeCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue());
                formPane.setTop(null);
                formPane.setCenter(null);
                formPane.setBottom(null);
                formController = null;
                treeViewController.getTreeView().getSelectionModel().clearSelection();
                treeViewController.clearTreeView();
                treeViewController.update();
            }
            else if(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getParent() == treeViewController.getSupplementsHeader()) {
                if(MagazineService.getDBController().getNumOfSubbedCustomers(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue()) > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Can't Delete Supplement while there are Customers Subscribed");
                    alert.showAndWait();
                }
                else {
                    MagazineService.getDBController().removeSupplementMagazine(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue());
                    formPane.setTop(null);
                    formPane.setCenter(null);
                    formPane.setBottom(null);
                    formController = null;
                    treeViewController.getTreeView().getSelectionModel().clearSelection();
                    treeViewController.clearTreeView();
                    treeViewController.update();
                }
            }
        });
        BorderPane.setMargin(deleteBar, new Insets(10, 10, 5, 10));

        doneBar.getButtons().add(done);
        done.setOnAction(e -> { 
            formController.setEditable(false);
            formPane.setTop(null);
            formPane.setBottom(editBar);
        }); 
        BorderPane.setMargin(doneBar, new Insets(2, 10, 10, 10));
        
        treeViewController.getTreeView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && newValue != oldValue) {
                FXMLLoader loader;
                if(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getParent() == treeViewController.getMainHeader()) {
                    try {
                        loader = new FXMLLoader(getClass().getResource("../view/MainMagazineForm.fxml"));
                        formPane.setTop(null);
                        scrollPane.setContent(loader.load());
                        formPane.setCenter(scrollPane);
                        formPane.setBottom(editBar);
                        MainMagazineFormController mmfc;
                        formController = mmfc = loader.getController();
                        mmfc.setMainMagazineRef(MagazineService.getDBController().getMainMagazine());
                        mmfc.setTreeItemRef(treeViewController.getTreeView().getSelectionModel().getSelectedItem());
                        mmfc.displayData();
                    }
                    catch(IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                else if(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getParent() == treeViewController.getSupplementsHeader()) {
                    try {
                        loader = new FXMLLoader(getClass().getResource("../view/SupplementMagazineForm.fxml"));
                        formPane.setTop(null);
                        scrollPane.setContent(loader.load());
                        formPane.setCenter(scrollPane);
                        formPane.setBottom(editBar);
                        SupplementMagazineFormController smfc;
                        formController = smfc = loader.getController();
                        smfc.setSupplementMagazineRef(MagazineService.getDBController().getSupplementMagazine(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue()));
                        smfc.setTreeItemRef(treeViewController.getTreeView().getSelectionModel().getSelectedItem());
                        smfc.displayData();
                    }
                    catch(IOException ioe) {
                        ioe.printStackTrace();
                    }                   
                }
                else if(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getParent() == treeViewController.getPayingHeader()) {
                    try {
                        loader = new FXMLLoader(getClass().getResource("../view/PayingCustomerForm.fxml"));
                        formPane.setTop(null);
                        scrollPane.setContent(loader.load());
                        formPane.setCenter(scrollPane);
                        formPane.setBottom(editBar);
                        PayingCustomerFormController pcfc;
                        formController = pcfc = loader.getController();
                        pcfc.setPayingCustomerRef((PayingCustomer)MagazineService.getDBController().getCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue()));
                        if(((PayingCustomer)MagazineService.getDBController().getCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue())).getPaymentMethod() instanceof DirectDebit) {
                            pcfc.setDirectDebitRef((DirectDebit)((PayingCustomer)MagazineService.getDBController().getCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue())).getPaymentMethod());
                        }
                        else if(((PayingCustomer)MagazineService.getDBController().getCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue())).getPaymentMethod() instanceof CreditCard) {
                            pcfc.setCreditCardRef((CreditCard)((PayingCustomer)MagazineService.getDBController().getCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue())).getPaymentMethod());
                        }
                        pcfc.setTreeItemRef(treeViewController.getTreeView().getSelectionModel().getSelectedItem());
                        pcfc.displayData();
                    }
                    catch(IOException ioe) {
                        ioe.printStackTrace();
                    }     
                    catch(ClassCastException cce) {
                        cce.printStackTrace();
                    }
                }
                else if(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getParent() == treeViewController.getAssociatesHeader()) {
                    try {
                        loader = new FXMLLoader(getClass().getResource("../view/AssociateCustomerForm.fxml"));
                        formPane.setTop(null);
                        scrollPane.setContent(loader.load());
                        formPane.setCenter(scrollPane);
                        formPane.setBottom(editBar);
                        AssociateCustomerFormController acfc;
                        formController = acfc = loader.getController();
                        acfc.setAssociateCustomerRef((AssociateCustomer)MagazineService.getDBController().getCustomer(treeViewController.getTreeView().getSelectionModel().getSelectedItem().getValue()));
                        acfc.setTreeItemRef(treeViewController.getTreeView().getSelectionModel().getSelectedItem());
                        acfc.displayData();
                    }
                    catch(IOException ioe) {
                        ioe.printStackTrace();
                    }  
                    catch(ClassCastException cce) {
                        cce.printStackTrace();
                    }
                }
                else {
                    formPane.setTop(null);
                    formPane.setCenter(null);
                    formPane.setBottom(null);
                }
            }
        });
        
        menuBarController.setTreeViewControllerRef(treeViewController);
    }
    
    public void setEditable(boolean editable) {
        formController.setEditable(editable);
    }
    
    public MagazineServiceTreeViewController getTreeViewController() {
        return this.treeViewController;
    }
}
