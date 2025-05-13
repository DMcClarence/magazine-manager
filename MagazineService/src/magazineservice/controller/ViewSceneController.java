/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

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

    @FXML
    private BorderPane formPane;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ButtonBar editDeleteBar;
    
    private ButtonBar doneBar;
    
    private Button done;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doneBar = new ButtonBar();
        done = new Button("Done");

        doneBar.getButtons().add(done);
        done.setOnAction(evt -> {try {
                                    formPane.setCenter(FXMLLoader.load(getClass().getResource("../view/ViewableAssociateCustomerForm.fxml")));
                                    formPane.setBottom(editDeleteBar);
                                 }
                                 catch(IOException ioe) {
                                    ioe.printStackTrace();
                                 }}); 
        BorderPane.setAlignment(doneBar, Pos.CENTER);
        BorderPane.setMargin(doneBar, new Insets(10, 10, 10, 10));
    }
    
    public void editButtonClicked(ActionEvent e) {
        try {
            formPane.setBottom(editDeleteBar);
            formPane.setCenter(FXMLLoader.load(getClass().getResource("../view/EditableAssociateCustomerForm.fxml")));
            formPane.setBottom(doneBar);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
