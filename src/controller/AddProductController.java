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
import main.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The AddProductController provides functionality for the Add Product screen of the application.
 *
 * @author Najee Burnette
 */
public class AddProductController implements Initializable {

    Inventory inventory;
    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    //Inputs, Labels and Buttons
    @FXML private Button addButton;
    @FXML private TextField idTextField;
    @FXML private TextField invInput;
    @FXML private TextField maxInput;
    @FXML private TextField minInput;
    @FXML private TextField nameInput;
    @FXML private TextField partSearchQuery;
    @FXML private TextField priceInput;
    @FXML private Button removePartButton;
    @FXML private Button saveButton;
    @FXML private Label errorLabel;

    //TableViews
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partId;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> partInv;
    @FXML private TableColumn<Part, Double> partCost;

    @FXML private TableView<Part> assocPartsTableView;
    @FXML private TableColumn<Part, Integer> assocPartId;
    @FXML private TableColumn<Part, String> assocPartInv;
    @FXML private TableColumn<Part, Integer> assocPartName;
    @FXML private TableColumn<Part, Double> assocPartPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        partsTableView.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This method returns the user back to the main controller without adding any Part to the Inventory
     * @param actionEvent applied to Cancel Button
     * @throws IOException
     */
    public void backToMain (javafx.event.ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds a part from the parts list to the associated parts list of the product being modified.
     * @param event
     */
    public void onActionAddButton(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            errorAlert();
        } else {
            assocParts.add(selectedPart);
            assocPartsTableView.setItems(assocParts);
        }
    }

    /**
     * Displays error message prompts when parts are not selected to adding or removing from associated part list.
     */
    private void errorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Error");
        alert.setHeaderText("No Part Selected.");
        alert.showAndWait();
    }

    /**
     * Initiates a search based on the query in the part text field, then refreshes the part list with the results.
     * Parts are searched by ID, partial name or full name.
     *
     * @param event applied to the Product search button.
     */
    public void partResultHandler(ActionEvent event)
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

    /**
     * Reads the user input and compares it with the names of parts in the list and returns parts containing
     * the search query.
     *
     * @param partialName
     * @return a list of the parts matching the query
     */
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

    /**
     * Reads the user input and compares it with the IDs of parts in the list and returns parts containing
     * the exact ID.
     *
     * @param partId
     * @return the part with the matching the ID
     */
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

    /**
     * Prompts the user to confirm removal of part form associated parts list, part is removed if confirmed.
     * @param event
     */
    public void onActionRemoveButton(ActionEvent event)
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

    /**
     *This method checks input text fields for validity and if conditions are met, adds the Product to the Inventory
     * and the parts to the Products associated part list.
     *
     * @param event applied to Save button
     * @throws IOException
     */
    public void onSaveButton(ActionEvent event) throws IOException {
        ObservableList<Part> allParts = Inventory.getAllParts();

        //make unique ID
        int id = (int) (Math.random() * 100);
        boolean dup = false;

        do {
            for (Part part : allParts) {
                if (id == part.getId())
                {
                    dup = true;
                }
                else {dup = false;}
            }
            if(dup == true) {
                id = (int) (Math.random() * 100);
            }
        }while(dup);

        String name = "";
        int stock = 0;
        double price = 0.00;
        int max = 0;
        int min = 0;


        //error message for invalid inputs
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

        ///check price input
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

            // Create a new Product object, add the associated parts, and add the Product to the inventory
            Product newProduct = new Product(id, name, price, stock, min, max);
            for(int i = 0; i <= assocParts.size()-1; i++)
            {
                newProduct.addAssociatedPart(assocParts.get(i));
            }
            inventory.addProduct(newProduct);
            returnToMain();
        }
    }
    
    /**
     * This method returns the user back to the main controller after a Part is added.
     * @throws IOException
     */
    private void returnToMain() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage)saveButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }


}
