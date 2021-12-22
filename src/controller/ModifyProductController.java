package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    @FXML private Button saveButton;

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


    private ObservableList<Part> searchByPartName(String partialName)
    {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part p : allParts)
        {
            if(p.getName().contains(partialName))
            {
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    private Part searchByPartId(int partId)
    {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part p : allParts)
        {
            if(p.getId() == partId) {
                return p;
            }
        }
        return null;
    }

    public void partResultHandler(javafx.event.ActionEvent actionEvent)
    {
        String q = partSearchQuery.getText();

        ObservableList<Part> parts = searchByPartName(q);

        if(parts.size() == 0){
            try
            {
                int id = Integer.parseInt(q);
                Part pt = searchByPartId(id);
                if (pt != null) {parts.add(pt);}
            }
            catch (NumberFormatException ignored)
            {

            }
        }

        partsTableView.setItems(parts);
        partSearchQuery.setText("");
    }

    public void onActionAddButton(javafx.event.ActionEvent actionEvent) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            errorAlert();
        } else {
            assocParts.add(selectedPart);
            assocPartsTableView.setItems(assocParts);
        }
    }

    public void onActionRemoveButton(javafx.event.ActionEvent actionEvent)
    {
        Part selectedPart = assocPartsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            errorAlert();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Are you sure you want to delete: " + selectedPart.getName());
            alert.setContentText("Click OK to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                assocParts.remove(selectedPart);
                assocPartsTableView.setItems(assocParts);
            }
        }
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

    public void onSaveButton(javafx.event.ActionEvent actionEvent) throws IOException
    {
        int id = product.getId();
        String name = "";
        int stock = 0;
        double price = 0.00;
        int max = 0;
        int min = 0;

        StringBuilder error = new StringBuilder("Error: ");

        //checking name input
        if (nameInput.getText().isBlank()) {
            error.append("\n - Name field is empty.");
        } else {
            name = nameInput.getText();
        }

        boolean invIsInt = false;
        boolean maxIsInt = false;
        boolean minIsInt = false;

        //check inventory input
        try {
            stock = Integer.parseInt(invInput.getText());
            invIsInt = true;
        } catch (NumberFormatException e) {
            error.append("\n - Inventory is not an integer.");
        }

        //check price input
        try {
            price = Double.parseDouble(priceInput.getText());
        } catch (NumberFormatException e) {
            error.append("\n - Price/Cost not valid value.");
        }

        //check max input
        try {
            max = Integer.parseInt(maxInput.getText());
            maxIsInt = true;
        } catch (NumberFormatException e) {
            error.append("\n - Max value not an integer.");
        }

        //check min input
        try {
            min = Integer.parseInt(minInput.getText());
            minIsInt = true;
        } catch (NumberFormatException e) {
            error.append("\n - Min value not an integer.");
        }

        //compare min and max
        if (maxIsInt && minIsInt && min >= max) {
            error.append("\n - Min must be lower than max.");
        }

        //confirm inventory lies between min and max
        if (invIsInt && maxIsInt && minIsInt &&
                (stock > max) || (stock < min)) {
            error.append("\n - Inventory is not between min and max.");
        }

        //Show error messages if there are
        if (error.toString().compareTo("Error: ") != 0)
        {
            errorLabel.setText(error.toString());
            errorLabel.setVisible(true);
        }
        else
        {

            errorLabel.setVisible(false);
            //int currentProductIndex = inventory.getAllProducts().indexOf(product);

            Product updatedProduct = new Product(id, name, price, stock, min, max);
            for(int i = 0; i <= assocParts.size()-1; i++)
            {
                updatedProduct.addAssociatedPart(assocParts.get(i));
            }
            //Inventory.addProduct(updatedProduct);
            //Inventory.deleteProduct(product);
            inventory.updateProduct(inventory.getAllProducts().indexOf(product), updatedProduct);
            //inventory.updateProduct(currentProductIndex, updatedProduct);


          /*
            errorLabel.setVisible(false);

            Product prod = new Product(id, name, price, stock, min, max);
            for(int i = 0; i <= parts.size()-1; i++)
            {
                prod.addAssociatedPart(parts.get(i));
            }
            inventory.updateProduct(inventory.getAllProducts().indexOf(product), prod);

           */

            returnToMain(actionEvent);

        }

    }
    private void returnToMain(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void errorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Error");
        alert.setHeaderText("No Part Selected.");
        alert.showAndWait();
    }

    private void testAlert(Product product) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Test");
        alert.setHeaderText(
                product.getName() + "\n" +
                product.getId() + "\n" +
                product.getMax() + "\n" +
                product.getMin() + "\n" +
                product.getPrice() + "\n" +
                product.getAllAssociatedParts());
        alert.showAndWait();
    }

}
