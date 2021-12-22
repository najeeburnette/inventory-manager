package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import main.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    //Parts variables
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private javafx.scene.control.TextField partSearchQuery;
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
    private javafx.scene.control.TextField productSearchQuery;
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

    Inventory inventory;

    /**
     * The selected part object from the table view.
     */
    public static Part partToModify;

    /**
     * The selected product object from the table view.
     */
    public static Product productToModify;

    /**
     * Gets the part object selected in the part table.
     *
     * @return A part object, null if no part selected.
     */
    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     * Gets the product object selected by the user in the product table.
     *
     * @return A product object, null if no product selected.
     */
    public static Product getProductToModify() {
        return productToModify;
    }


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

        partToModify = partsTableView.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            errorAlert(1);
        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPartMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part Menu");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void toModifyProduct(javafx.event.ActionEvent actionEvent) throws IOException {

        productToModify = productTableView.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            errorAlert(2);
        }
        else {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProductMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Product Menu");
            stage.setScene(scene);
            stage.show();
        }
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

    public void partResultHandler(ActionEvent actionEvent)
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
          catch (NumberFormatException e)
          {
              //ignore
          }
      }

       partsTableView.setItems(parts);
       partSearchQuery.setText("");
    }

    public void productResultHandler(ActionEvent actionEvent)
    {
        String q = productSearchQuery.getText();

        ObservableList<Product> products = searchByProductName(q);

        if(products.size() == 0){
            try
            {
                int id = Integer.parseInt(q);
                Product pd = searchByProductId(id);
                if (pd != null) {products.add(pd);}
            }
            catch (NumberFormatException e)
            {
                //ignore
            }
        }

        productTableView.setItems(products);
        productSearchQuery.setText("");
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

    private ObservableList<Product> searchByProductName(String partialName)
    {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product p : allProducts)
        {
            if(p.getName().contains(partialName))
            {
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }

    private Product searchByProductId(int productId)
    {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product p : allProducts)
        {
            if(p.getId() == productId) {
                return p;
            }
        }
        return null;
    }


        /**
         * Displays various alert messages.
         */
        private void errorAlert(int alertNum) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Alert alertError = new Alert(Alert.AlertType.ERROR);

            switch (alertNum) {
                case 1:
                    alertError.setTitle("Error");
                    alertError.setHeaderText("No Part Selected.");
                    alertError.showAndWait();
                    break;
                case 2:
                    alertError.setTitle("Error");
                    alertError.setHeaderText("No Product Selected.");
                    alertError.showAndWait();
                    break;
            }
        }

}