package controller;

import com.sun.glass.ui.Cursor;
import com.sun.glass.ui.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import main.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    Inventory inventory;

    //Parts variables
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private javafx.scene.control.TextField partSearchInput;
    @FXML
    private TableColumn<Part, Integer> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInv;
    @FXML
    private TableColumn<Part, Double> partCost;
    @FXML
    private javafx.scene.control.Button addPartButton;
    @FXML
    private javafx.scene.control.Button modifyPartButton;
    @FXML
    private javafx.scene.control.Button deletePartButton;

    //Product variables
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private javafx.scene.control.TextField productSearchField;
    @FXML
    private TableColumn<Product, Integer> productId;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productInv;
    @FXML
    private TableColumn<Product, Double> productCost;
    @FXML
    private javafx.scene.control.Button addProductButton;
    @FXML
    private javafx.scene.control.Button modifyProductButton;
    @FXML
    private javafx.scene.control.Button deleteProductButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        partsTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    public void toAddPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyPart(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Modify Part Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Modify Product Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddProduct(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeletePart()
    {
        if (partsTableView.getSelectionModel().getSelectedItem() != null)
        {
            Part part = partsTableView.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Are you sure you want to delete: " + part.getName());
            alert.setContentText("Click OK to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK)
            {
                try
                {
                    inventory.deletePart(part);
                    partsTableView.setItems(inventory.getAllParts());
                    partsTableView.refresh();

                }
                catch (Exception e)
                {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Error: Part Deletion!");
                    alert2.setHeaderText("The part you selected could not be deleted.");
                }
            }
        }
    }

    public void onDeleteProduct()
    {
        if (productTableView.getSelectionModel().getSelectedItem() != null)
        {
            Product product = productTableView.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Are you sure you want to delete: " + product.getName());
            alert.setContentText("Click OK to confirm");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK)
            {
                try
                {
                    inventory.deleteProduct(product);
                    productTableView.setItems(inventory.getAllProducts());
                    productTableView.refresh();

                }
                catch (Exception e)
                {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Error: Product Deletion!");
                    alert2.setHeaderText("The product you selected could not be deleted.");
                }
            }
        }
    }

    public void onExit()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Click OK to confirm");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            Stage currentStage = (Stage)partsTableView.getScene().getWindow();
            currentStage.close();
        }

    }

    public boolean checkIfOnlyNumbers(String s)
    {
        boolean results = false;

        for(int i = 0; i <= s.length()-1; i++)
        {
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9')
            {
                results = true;
            }
            else
            {
                results = false;
            }
        }

        return results;
    }

    public void onPartSearch(KeyEvent event) throws IOException
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            String searchinput = partSearchInput.getText();

            // Perform lookup based on whether the search searchinput is just the PartID or the Name of the Part
            if(checkIfOnlyNumbers(searchinput) && !searchinput.isEmpty())
            {
                Part part = inventory.lookupPart(Integer.valueOf(searchinput));
                partsTableView.getSelectionModel().select(part);
            }
            else
            {
                // if the search searchinput is a String, then filter the results to show Parts that have Names within the searchinput
                ObservableList<Part> results = FXCollections.observableArrayList();
                results.addAll(inventory.lookupPart(searchinput));
                partsTableView.setItems(results);
            }
        }
    }


    public void onProductSearch()
    {
    }
}