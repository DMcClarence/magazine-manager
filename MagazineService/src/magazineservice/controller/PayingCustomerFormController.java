/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package magazineservice.controller;

import java.net.URL;
import java.time.Year;
import java.time.YearMonth;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magazineservice.MagazineService;
import magazineservice.model.AssociateCustomer;
import magazineservice.model.CreditCard;
import magazineservice.model.DirectDebit;
import magazineservice.model.PayingCustomer;
import magazineservice.view.EditableForm;

/**
 * FXML Controller class
 *
 * @author 34085068
 */
public class PayingCustomerFormController implements Initializable, EditableForm {
    private final int NO_OF_EXPIRY_YEARS = 20; // 20 is Arbitrary. No Expiry will be more than 20 years in the future.
    
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
    private Label payingLabel;
    
    @FXML
    private HBox paymentMethodLabel;
    
    @FXML
    private Label paymentMethodFieldLabel;
    
    @FXML
    private ComboBox<String> paymentMethodChoices;
    
    @FXML
    private HBox accountNumberField;
    
    @FXML
    private Label accountNumberFieldLabel;
    
    @FXML
    private Label displayedAccountNumber;
    
    @FXML
    private TextField accountNumberFieldTextBox;
    
    @FXML
    private HBox cardNumberField;
    
    @FXML
    private Label cardNumberFieldLabel;
    
    @FXML
    private Label displayedCardNumber;
    
    @FXML
    private TextField cardNumberFieldTextBox;
    
    @FXML
    private HBox expiryField;
    
    @FXML
    private Label expiryFieldLabel;
    
    @FXML
    private Label displayedExpiry;
    
    @FXML
    private ComboBox<String> selectedMonth;
    
    @FXML
    private ComboBox<String> selectedYear;
    
    @FXML
    private Label associatesLabel;
    
    @FXML 
    private ListView<String> associatesList;
    
    @FXML
    private SupplementMagazineSelectorController supplementListController;
    
    private PayingCustomer payingCustomerRef = null;
    
    private DirectDebit directDebitRef = null;
    
    private CreditCard creditCardRef = null;
    
    private TreeItem<String> treeItemRef = null;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paymentMethodChoices.getItems().addAll("Direct Debit", "Credit Card");
        selectedMonth.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        int currentYear = Year.now().getValue();
        for(int i = currentYear; i < currentYear + NO_OF_EXPIRY_YEARS; i++) { 
            selectedYear.getItems().add(Integer.toString(i));
        }
        
        paymentMethodChoices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { 
            if(newValue != null && newValue != oldValue){ 
                switch(paymentMethodChoices.getSelectionModel().getSelectedIndex()) {
                    case 0 -> { // Direct Debit
                        System.out.println("Direct Debit");
                        accountNumberField.setManaged(true);
                        accountNumberField.setVisible(true);
                        cardNumberField.setManaged(false);
                        cardNumberField.setVisible(false);
                        expiryField.setManaged(false);
                        expiryField.setVisible(false);
                    }
                    case 1 -> { // Credit Card
                        System.out.println("Credit Card");
                        cardNumberField.setManaged(true);
                        cardNumberField.setVisible(true);
                        expiryField.setManaged(true);
                        expiryField.setVisible(true);
                        accountNumberField.setManaged(false);
                        accountNumberField.setVisible(false);
                    }
                }
            }  
        });
        
        associatesList.setOnScroll(e -> e.consume());
    }
        
    /**
     *
     * @param payingCustomer
     */
    public void setPayingCustomerRef(PayingCustomer payingCustomer) {
        this.payingCustomerRef = payingCustomer;
    }
    
    /**
     *
     * @param directDebit
     */
    public void setDirectDebitRef(DirectDebit directDebit) {
        this.directDebitRef = directDebit;
    }
    
    /**
     *
     * @param creditCard
     */
    public void setCreditCardRef(CreditCard creditCard) {
        this.creditCardRef = creditCard;
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
        if(payingCustomerRef != null) {
            displayedName.setText(payingCustomerRef.getName());
            displayedEmail.setText(payingCustomerRef.getEmail());
            if(directDebitRef != null) {
                paymentMethodChoices.getSelectionModel().select(0);
                displayedAccountNumber.setText(directDebitRef.getAccountNumber());
            }
            else if(creditCardRef != null) {
               paymentMethodChoices.getSelectionModel().select(1);
               displayedCardNumber.setText(creditCardRef.getCardNumber());
               selectedMonth.getSelectionModel().select((creditCardRef.getExpiry().getMonthValue() - 1));
               selectedYear.getSelectionModel().select((NO_OF_EXPIRY_YEARS - 1) - ((Year.now().getValue() + (NO_OF_EXPIRY_YEARS - 1)) - creditCardRef.getExpiry().getYear()));
            }
            
            for(CheckBox cb : supplementListController.getSupplementList()) {
                if(payingCustomerRef.getSuppMags().contains(MagazineService.getDBController().getSupplementMagazine(cb.getText()))) {
                    cb.setSelected(true);
                }
                else {
                    cb.setSelected(false);
                }
            }
            
            if(payingCustomerRef.getAssociates().isEmpty()) {
                associatesLabel.setVisible(false);
                associatesLabel.setManaged(false);
                associatesList.setManaged(false);
                associatesList.setVisible(false);
            }
            else {
                associatesLabel.setVisible(true);
                associatesLabel.setManaged(true);
                associatesList.setManaged(true);
                associatesList.setVisible(true);
                
                associatesList.getItems().clear();
                for(AssociateCustomer ac : payingCustomerRef.getAssociates()) {
                    associatesList.getItems().add(ac.getEmail());
                }
            }     
        }
    }
    
    /**
     *
     */
    public void updateRefData() {
        if(payingCustomerRef != null) {
            payingCustomerRef.setName(displayedName.getText());
            payingCustomerRef.setEmail(displayedEmail.getText());
            if(directDebitRef != null) {
                payingCustomerRef.setPaymentMethod(directDebitRef);   
            }
            else if(creditCardRef != null) {
                payingCustomerRef.setPaymentMethod(creditCardRef);
            }
            
            for(CheckBox cb: supplementListController.getSupplementList()) {
                if(cb.isSelected()) {
                    MagazineService.getDBController().addToSubscription(cb.getText(), payingCustomerRef.getEmail());
                }
                else {
                    MagazineService.getDBController().removeFromSubscription(cb.getText(), payingCustomerRef.getEmail());
                }
            }
        }
    }
    
    /**
     *
     * @param editable
     */
    @Override
    public void setEditable(boolean editable) {
        // Hide Displayed Title, Show Title Text Field
        displayedName.setManaged(!(editable));
        displayedName.setVisible(!(editable));
        nameFieldTextBox.setManaged(editable);
        nameFieldTextBox.setVisible(editable);
        
        // Hide Displayed Weekly Cost, Show Weekly Cost Text Field
        if(payingCustomerRef == null) {
            displayedEmail.setManaged(!(editable));
            displayedEmail.setVisible(!(editable));
            emailFieldTextBox.setManaged(editable);
            emailFieldTextBox.setVisible(editable);
        }
        
        displayedAccountNumber.setManaged(!(editable));
        displayedAccountNumber.setVisible(!(editable));
        accountNumberFieldTextBox.setManaged(editable);
        accountNumberFieldTextBox.setVisible(editable);
        
        displayedCardNumber.setManaged(!(editable));
        displayedCardNumber.setVisible(!(editable));
        cardNumberFieldTextBox.setManaged(editable);
        cardNumberFieldTextBox.setVisible(editable);
        
        displayedExpiry.setManaged(!(editable));
        displayedExpiry.setVisible(!(editable));
        
        selectedMonth.setDisable(!(editable));
        selectedYear.setDisable(!(editable));
        
        paymentMethodChoices.setDisable(!(editable));
        
        supplementListController.setEditable(editable);
        
        // Update Label/Text Field with Accurate Data for Selected Customer
        if(editable && payingCustomerRef != null) {
            nameFieldTextBox.setText(payingCustomerRef.getName());
            emailFieldTextBox.setText(payingCustomerRef.getEmail());
            if(payingCustomerRef.getPaymentMethod() instanceof DirectDebit) {
                try {
                    directDebitRef = (DirectDebit)payingCustomerRef.getPaymentMethod();
                    accountNumberFieldTextBox.setText(directDebitRef.getAccountNumber());
                }
                catch(ClassCastException cce) {
                    cce.printStackTrace();
                }
            }
            else if(payingCustomerRef.getPaymentMethod() instanceof CreditCard) {
                try {
                    creditCardRef = (CreditCard)payingCustomerRef.getPaymentMethod();
                    cardNumberFieldTextBox.setText(creditCardRef.getCardNumber());
                    selectedMonth.getSelectionModel().select((creditCardRef.getExpiry().getMonthValue() - 1));
                    selectedYear.getSelectionModel().select((NO_OF_EXPIRY_YEARS - 1) - ((Year.now().getValue() + (NO_OF_EXPIRY_YEARS - 1)) - creditCardRef.getExpiry().getYear()));
                }
                catch(ClassCastException cce) {
                    cce.printStackTrace();
                }
            }
            
            for(CheckBox cb : supplementListController.getSupplementList()) {
                if(payingCustomerRef.getSuppMags().contains(MagazineService.getDBController().getSupplementMagazine(cb.getText()))) {
                    cb.setSelected(true);
                }
                else {
                    cb.setSelected(false);
                }
            }
        }
        else if(!(editable) && payingCustomerRef != null) {
            displayedName.setText(nameFieldTextBox.getText());
            displayedEmail.setText(emailFieldTextBox.getText());
            switch(paymentMethodChoices.getSelectionModel().getSelectedIndex()) {
                case 0 -> {
                    if(accountNumberFieldTextBox.getText() != null && !accountNumberFieldTextBox.getText().strip().isEmpty()) {
                       displayedAccountNumber.setText(accountNumberFieldTextBox.getText());
                        if(directDebitRef != null) {
                            try {
                                directDebitRef.setAccountNumber(displayedAccountNumber.getText());
                            }
                            catch(IllegalArgumentException iae) {
                                displayedAccountNumber.setText(directDebitRef.getAccountNumber());
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("""
                                                      An Error Occured. 
                                                      Payment Method Not Updated.
                                                      Make Sure All Fields are Complete and 
                                                      Account Number has 8 digits.""");
                                alert.showAndWait();
                            }
                        }
                        else {
                            try {
                                directDebitRef = new DirectDebit(displayedAccountNumber.getText());
                                creditCardRef = null;
                            }
                            catch(IllegalArgumentException iae) {
                                paymentMethodChoices.getSelectionModel().select(1);
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("""
                                                     An Error Occured. 
                                                     Payment Method Not Updated.
                                                     Make Sure All Fields are Complete and 
                                                     Account Number has 8 digits.""");
                                alert.showAndWait();
                            }
                        }
                    }
                    else {
                        if(directDebitRef != null) {
                            paymentMethodChoices.getSelectionModel().select(0);
                            displayedAccountNumber.setText(directDebitRef.getAccountNumber());
                        }
                        else if(creditCardRef != null) {
                           paymentMethodChoices.getSelectionModel().select(1);
                           displayedCardNumber.setText(creditCardRef.getCardNumber());
                           selectedMonth.getSelectionModel().select((creditCardRef.getExpiry().getMonthValue() - 1));
                           selectedYear.getSelectionModel().select((NO_OF_EXPIRY_YEARS - 1) - ((Year.now().getValue() + (NO_OF_EXPIRY_YEARS - 1)) - creditCardRef.getExpiry().getYear()));
                        }
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("""
                                             An Error Occured. 
                                             Payment Method Not Updated.
                                             Make Sure All Fields are Complete and 
                                             Account Number has 8 digits.""");
                        alert.showAndWait();
                    }
                }
                case 1 -> {
                    if(cardNumberFieldTextBox.getText() != null && 
                       !cardNumberFieldTextBox.getText().strip().isEmpty() &&
                       selectedYear.getSelectionModel().selectedItemProperty() != null &&
                       selectedMonth.getSelectionModel().selectedItemProperty() != null) {
                        displayedCardNumber.setText(cardNumberFieldTextBox.getText());
                        
                        if(creditCardRef != null) {
                            try {
                                creditCardRef.setCardNumber(displayedCardNumber.getText());
                                creditCardRef.setExpiry((selectedYear.getSelectionModel().getSelectedIndex() + Year.now().getValue()),
                                                        (selectedMonth.getSelectionModel().getSelectedIndex() + 1));                                  
                            }
                            catch(IllegalArgumentException iae) {
                                displayedCardNumber.setText(creditCardRef.getCardNumber());
                                selectedMonth.getSelectionModel().select((creditCardRef.getExpiry().getMonthValue() - 1));
                                selectedYear.getSelectionModel().select((NO_OF_EXPIRY_YEARS - 1) - ((Year.now().getValue() + (NO_OF_EXPIRY_YEARS - 1)) - creditCardRef.getExpiry().getYear()));
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("""
                                                     An Error Occured. 
                                                     Payment Method Not Updated.
                                                     Make Sure All Fields are Complete, 
                                                     Card Number has 16 digits and Expiry is 
                                                     a Future Date.""");
                                alert.showAndWait();
                            }
                        }
                        else {
                            try {
                                creditCardRef = new CreditCard(displayedCardNumber.getText(), 
                                                               YearMonth.of((selectedYear.getSelectionModel().getSelectedIndex() + Year.now().getValue()),
                                                                            (selectedMonth.getSelectionModel().getSelectedIndex() + 1)));
                                directDebitRef = null;                                
                            }
                            catch(IllegalArgumentException iae) {
                                paymentMethodChoices.getSelectionModel().select(0);
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("""
                                                     An Error Occured. 
                                                     Payment Method Not Updated.
                                                     Make Sure All Fields are Complete, 
                                                     Card Number has 16 digits and Expiry is 
                                                     a Future Date.""");
                                alert.showAndWait();
                            }
                        }
                    }
                    else {
                        if(directDebitRef != null) {
                            paymentMethodChoices.getSelectionModel().select(0);
                            displayedAccountNumber.setText(directDebitRef.getAccountNumber());
                        }
                        else if(creditCardRef != null) {
                           paymentMethodChoices.getSelectionModel().select(1);
                           displayedCardNumber.setText(creditCardRef.getCardNumber());
                           selectedMonth.getSelectionModel().select((creditCardRef.getExpiry().getMonthValue() - 1));
                           selectedYear.getSelectionModel().select((NO_OF_EXPIRY_YEARS - 1) - ((Year.now().getValue() + (NO_OF_EXPIRY_YEARS - 1)) - creditCardRef.getExpiry().getYear()));
                        }
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("""
                                             An Error Occured. 
                                             Payment Method Not Updated.
                                             Make Sure All Fields are Complete, 
                                             Card Number has 16 digits and Expiry is 
                                             a Future Date.""");
                        alert.showAndWait();
                    }
                }     
            }
        }       
        
        // Update Object in Database
        updateRefData();
        System.out.println(payingCustomerRef);
    }
    
    /**
     *
     * @return
     */
    public int createPayingCustomer() {
        if((nameFieldTextBox.getText() == null || nameFieldTextBox.getText().strip().isEmpty()) ||
           (emailFieldTextBox.getText() == null || emailFieldTextBox.getText().strip().isEmpty())) {
            return 1;
        }
        
        if(accountNumberFieldTextBox.getText() != null && !accountNumberFieldTextBox.getText().strip().isEmpty()) {
            directDebitRef = new DirectDebit(accountNumberFieldTextBox.getText());
            creditCardRef = null;
        }
        else if(cardNumberFieldTextBox.getText() != null && 
                !cardNumberFieldTextBox.getText().strip().isEmpty() &&
                selectedYear.getSelectionModel().selectedItemProperty() != null &&
                selectedMonth.getSelectionModel().selectedItemProperty() != null) {
            creditCardRef = new CreditCard(cardNumberFieldTextBox.getText(), 
                                           YearMonth.of((selectedYear.getSelectionModel().getSelectedIndex() + Year.now().getValue()), 
                                                        (selectedMonth.getSelectionModel().getSelectedIndex() + 1)));
            directDebitRef = null;
        }
        else {
            return 1;
        }
        
        if(MagazineService.getDBController().getCustomer(emailFieldTextBox.getText()) != null) {
            return 2;
        }
        
        PayingCustomer payingCustomer = null;
        if(directDebitRef != null) {
            try {
                payingCustomer = new PayingCustomer(nameFieldTextBox.getText(), emailFieldTextBox.getText(), 
                                                    MagazineService.getDBController().getMainMagazine(),
                                                    directDebitRef);
            }
            catch(IllegalArgumentException iae) {
                return 3;
            }
            
        }
        else if(creditCardRef != null) {
            try {
               payingCustomer = new PayingCustomer(nameFieldTextBox.getText(), emailFieldTextBox.getText(), 
                                                   MagazineService.getDBController().getMainMagazine(),
                                                   creditCardRef); 
            }
            catch(IllegalArgumentException iae) {
                return 4;
            }
        }
        
        MagazineService.getDBController().addCustomer(payingCustomer);
        
        for(CheckBox cb: supplementListController.getSupplementList()) {
            if(cb.isSelected()) {
                MagazineService.getDBController().addToSubscription(cb.getText(), payingCustomer.getEmail());
            }
            else {
                MagazineService.getDBController().removeFromSubscription(cb.getText(), payingCustomer.getEmail());
            }
        }
        
        return 0;
    }
    
    /**
     *
     */
    public void clearFields() {
        nameFieldTextBox.clear();
        emailFieldTextBox.clear();
    } 
}
