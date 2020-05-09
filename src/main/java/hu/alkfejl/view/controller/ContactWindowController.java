package hu.alkfejl.view.controller;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Address;
import ezvcard.property.Birthday;
import ezvcard.property.Kind;
import hu.alkfejl.controller.ContactController;
import hu.alkfejl.model.Contact;
import hu.alkfejl.utils.Utils;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static ezvcard.parameter.ImageType.JPEG;

public class ContactWindowController implements Initializable {

    @FXML
    private TableView<Contact> table;

    @FXML
    private TableColumn<Contact, String> nameCol;

    @FXML
    private TableColumn<Contact, String> emailCol;

    @FXML
    private TableColumn<Contact, Image> pictureCol;

    @FXML
    private TableColumn<Contact, String> birthCol;

    @FXML
    private TableColumn<Contact, String> positionCol;

    @FXML
    private TableColumn<Contact, String> organizationCol;

    @FXML
    private TableColumn<Contact, String> addressCol;

    @FXML
    private TableColumn<Contact, String> workAddressCol;

    @FXML
    private TableColumn<Contact, String> phoneCol;

    @FXML
    private TableColumn<Contact, String> workPhoneCol;

    @FXML
    private TableColumn<Contact, String> modifiedOnCol;


    @FXML
    private TableColumn<Contact, Void> actionsCol;

    @FXML
    public void refreshTable(){
        populateTable();
    }



    @FXML
    public void addContact(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/hu/alkfejl/add_contact_dialog.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
            e.printStackTrace();
        }
        refreshTable();
    }

    public ContactWindowController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTable();
    }

    private void editContact(Contact c) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/hu/alkfejl/add_contact_dialog.fxml"));
            Parent root = loader.load();
            DialogContactController controller = loader.getController();
            controller.initContact(c);

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportContact(Contact c) throws ParseException {
        VCard vcard = new VCard();

        Address addr = new Address();
        addr.setExtendedAddress(c.getAddress());

        Date birthDate=new SimpleDateFormat("yyyy-MM-dd").parse(c.getBirth());
        Birthday birthday = new Birthday(birthDate);

        vcard.setOrganization(c.getOrganization());
        vcard.setFormattedName(c.getName());
        vcard.setKind(Kind.individual());
        vcard.addAddress(addr);
        vcard.addEmail(c.getEmail());
        vcard.addEmail(c.getWorkEmail());
        vcard.addTelephoneNumber(c.getWorkPhone());
        vcard.addExpertise(c.getPosition());
        vcard.setBirthday(birthday);



        File file = new File("/exported/"+c.getName()+".vcf");
        try {
            Ezvcard.write(vcard).go(file);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The contact has been exported", ButtonType.OK);
            alert.setHeaderText("Export");
            alert.setTitle("Export");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteContact(Contact c){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete contact: '" + c.getName() + "'",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){

                Task<Boolean> task = new Task<>(){
                    @Override
                    protected Boolean call() throws Exception{
                        return ContactController.getInstance().delete(c);
                    }
                };

                Thread deleteThread = new Thread(task);
                deleteThread.start();

                task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, event1 ->{
                    refreshTable();
                });

            }
        });
    }

    public void populateTable(){
        List<Contact> list = ContactController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        organizationCol.setCellValueFactory(new PropertyValueFactory<>("organization"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        workAddressCol.setCellValueFactory(new PropertyValueFactory<>("workAddress"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        workPhoneCol.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
        modifiedOnCol.setCellValueFactory(new PropertyValueFactory<>("modifiedOn"));
        pictureCol.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(99);
            imageview.setFitWidth(85);

            //Set up the Table
            TableCell<Contact, Image> cell = new TableCell<Contact, Image>() {
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageview.setImage(item);
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        pictureCol.setCellValueFactory(new PropertyValueFactory<Contact, Image>("profilePicture"));


        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Delete");
                private final Button editBtn = new Button("Edit");
                private final Button exportBtn = new Button("Export");

                {
                    deleteBtn.setOnAction(event -> {
                        Contact c = getTableView().getItems().get(getIndex());
                        deleteContact(c);
                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        Contact c = getTableView().getItems().get(getIndex());
                        editContact(c);
                        refreshTable();
                    });
                    exportBtn.setOnAction(event -> {
                        Contact c = getTableView().getItems().get(getIndex());
                        try {
                            exportContact(c);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox container = new HBox();
                        container.getChildren().addAll(deleteBtn, editBtn, exportBtn);
                        setGraphic(container);
                    }
                }
            };

        });
    }

}
