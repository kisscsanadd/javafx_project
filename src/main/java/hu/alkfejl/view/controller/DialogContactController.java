package hu.alkfejl.view.controller;

import hu.alkfejl.App;
import hu.alkfejl.controller.ContactController;
import hu.alkfejl.model.Contact;
import hu.alkfejl.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class DialogContactController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    DatePicker birthPicker;

    @FXML
    TextField emailField;

    @FXML
    TextField workEmailField;

    @FXML
    TextField addressField;

    @FXML
    TextField workAddressField;

    @FXML
    TextField phoneField;

    @FXML
    TextField workPhoneField;

    @FXML
    TextField organizationField;

    @FXML
    TextField positionField;

    @FXML
    Label errorMsg;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    @FXML
    Button pictureChooser;

    private Contact contact = new Contact();
    private List<Contact> contacts;

    public DialogContactController() {
    }

    @FXML
    private void save(ActionEvent event) {
        // if contact has an id then it is an edit, otherwise it is a new instance
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                boolean result;
                if (contact.getId() == 0) { // new instance
                    result = ContactController.getInstance().add(contact);
                } else {
                    result = ContactController.getInstance().update(contact);
                }
                return result;
            }
        };

        Thread updateThread = new Thread(task);
        updateThread.start();

        task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                event1 -> {
                    Boolean result = task.getValue();
                    if (result) {
                        ((Node) event.getSource()).getScene().getWindow().hide();
                    } else {
                        Utils.showWarning("Nem sikerult a mentes");
                    }
                });
    }



    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contact.nameProperty().bindBidirectional(nameField.textProperty());
        contact.emailProperty().bindBidirectional(emailField.textProperty());
        // Little tricky
        contact.birthProperty().bindBidirectional(new SimpleStringProperty(birthPicker.getValue().toString()));
        contact.workEmailProperty().bindBidirectional(workEmailField.textProperty());
        contact.addressProperty().bindBidirectional(addressField.textProperty());
        contact.workAddressProperty().bindBidirectional(workAddressField.textProperty());
        contact.phoneProperty().bindBidirectional(phoneField.textProperty());
        contact.workPhoneProperty().bindBidirectional(workPhoneField.textProperty());
        contact.organizationProperty().bindBidirectional(organizationField.textProperty());
        contact.positionProperty().bindBidirectional(positionField.textProperty());

        addButton.disableProperty().bind(nameField.textProperty().isEmpty()
                .or(errorMsg.textProperty().isNotEmpty())));

        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\S+@\\S+\\.\\S+")){
                errorMsg.setText("");
            }
            else{
                errorMsg.setText("Invalid email");
            }
        });
    }

    private void FieldValidator() {
        addButton.disableProperty().bind(nameField.textProperty().isEmpty());

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var contact: contacts) {
                if (newValue.equals(contact.getName())) {
                    match = true;
                }
            }

            if (!match) {
                errorMsgName.setText("");
                FieldValidator();
            } else {
                errorMsgName.setText("A contact with this name has already exist");
                addButton.disableProperty().bind(errorMsgName.textProperty().isNotEmpty());
            }
        });
    }

    private void InitTable() {

        contact.nameProperty().bind(nameField.textProperty());
        contact.birthProperty().bind(birthField.textProperty());
        contact.emailProperty().bind(emailField.textProperty());
        contact.workEmailProperty().bind(workEmailField.textProperty());
        contact.addressProperty().bind(addressField.textProperty());
        contact.workAddressProperty().bind(workAddressField.textProperty());
        contact.phoneProperty().bind(phoneField.textProperty());
        contact.workPhoneProperty().bind(workPhoneField.textProperty());
        contact.organizationProperty().bind(organizationField.textProperty());
        contact.positionProperty().bind(positionField.textProperty());
        contact.organizationProperty().bind(organizationField.textProperty());
    }

    public void initContact(Contact c){
        c.copyTo(this.contact);

    }

}
