package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Inventory;
import main.Part;
import main.Product;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    Inventory inventory;
    Product product;
    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML private Label errorLabel;
    @FXML private TextField idInput;
    @FXML private TextField invInput;
    @FXML private TextField maxInput;
    @FXML private TextField minInput;
    @FXML private TextField nameInput;
    @FXML private Button partSearchButton;
    @FXML private TextField partSearchQuery;
    @FXML private TextField priceInput;

    @FXML private TableView<Part> partsTableView;
    @FXML private TableView<Part> assocPartsTableView;

    @FXML private TableColumn<Part, Integer> partId;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> partInv;
    @FXML private TableColumn<Part, Double> partCost;

    @FXML private TableColumn<Part, Integer> assocPartId;
    @FXML private TableColumn<Part, String> assocPartInv;
    @FXML private TableColumn<Part, Integer> assocPartName;
    @FXML private TableColumn<Part, Double> assocPartCost;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        product = MainController.getProductToModify();
        assocParts = product.getAllAssociatedParts();

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTableView.setItems(Inventory.getAllParts());
        assocPartsTableView.setItems(assocParts);

        idInput.setText(String.valueOf(product.getId()));
        nameInput.setText(product.getName());
        priceInput.setText(String.valueOf(product.getPrice()));
        invInput.setText(String.valueOf(product.getStock()));
        maxInput.setText(String.valueOf(product.getMax()));
        minInput.setText(String.valueOf(product.getMin()));

    }


    public void partResultHandler(javafx.event.ActionEvent actionEvent) {
    }

    public void onActionAddButton(javafx.event.ActionEvent actionEvent) {
    }

    public void onActionRemoveButton(javafx.event.ActionEvent actionEvent) {
    }

    public void backToMain (javafx.event.ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }

    public void onSaveButton(javafx.event.ActionEvent actionEvent) {

    }



}
