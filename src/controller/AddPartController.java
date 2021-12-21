package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.InHouse;
import main.Inventory;
import main.Outsourced;
import main.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddPartController implements Initializable
{

    @FXML private RadioButton outsourcedButton;
    @FXML private RadioButton inHouseButton;
    @FXML private Button cancelButton;
    @FXML private TextField idField;
    @FXML private TextField invInput;
    @FXML private TextField priceInput;
    @FXML private TextField maxInput;
    @FXML private TextField minInput;
    @FXML private TextField nameInput;
    @FXML private Label machineCompanyLabel;
    @FXML private TextField machineIdCompanyInput;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;

    Inventory inventory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

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

    public void onInHouse(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Machine ID");
    }

    public void onOutsourced(ActionEvent actionEvent) {
        machineCompanyLabel.setText("Company Name");
    }

    public void onSaveButton() throws IOException
    {
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
        int machineId = 0;
        String companyName = "";

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

        //check machine id/ company name input
        if (machineIdCompanyInput.getText().isBlank()) {
            if (inHouseButton.isSelected()) {
                error.append("\n - Machine ID field empty.");
            } else {
                error.append("\n - Company Name field empty.");
            }
        }

        //checking if machine Id is an integer
        else if (inHouseButton.isSelected())
        {
            try {
                machineId = Integer.parseInt(machineIdCompanyInput.getText());
            }
            catch (NumberFormatException e) {
                error.append("\n - Machine ID is not an integer.");
            }
        }
        else
        {
            companyName = machineIdCompanyInput.getText();
        }

        //Show error messages if there are
        if (error.toString().compareTo("Error: ") != 0)
        {
            errorLabel.setText(error.toString());
            errorLabel.setVisible(true);
        }
        else {
            errorLabel.setVisible(false);

            if (inHouseButton.isSelected()) {
                InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                inventory.addPart(newPart);
            } else {
                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                inventory.addPart(newPart);
            }
            returnToMain();
        }
    }

    private void returnToMain() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage)saveButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }

}

