/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import magazineservice.MagazineService;
import magazineservice.model.SupplementMagazine;
import magazineservice.view.EditableForm;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class SupplementMagazineSelectorController implements Initializable, EditableForm {
    @FXML
    private ListView<CheckBox> supplementList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for(SupplementMagazine sm : MagazineService.getDBController().getAllSupplementMagazines()) {
            CheckBox checkbox = new CheckBox(sm.getTitle());
            checkbox.setStyle("-fx-padding: 5px;");
            supplementList.getItems().add(checkbox);
        }
        supplementList.setOnScroll(e -> e.consume());
        setEditable(false);
    }  
    
    @Override
    public void setEditable(boolean editable) {
        for(CheckBox cb : getSupplementList()) {
            cb.setDisable(!(editable));
        }
    }
    
    public ObservableList<CheckBox> getSupplementList() {
        return this.supplementList.getItems();
    }
}
