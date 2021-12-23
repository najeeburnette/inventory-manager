package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.InHouse;
import main.Inventory;
import main.Outsourced;
import main.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The ModifyPartController provides functionality for the Modify Part screen of the application.
 * the Inventory
 *
 * @author Najee Burnette
 */
public class ModifyPartController implements Initializable
{
    //Text Fields
    @FXML private TextField idInput;
    @FXML private TextField invInput;
    @FXML private TextField machineIdCompanyInput;
    @FXML private TextField priceInput;
    @FXML private TextField maxInput;
    @FXML private TextField minInput;
    @FXML private TextField nameInput;

    //Buttons and Labels
    @FXML private RadioButton inHouseButton;
    @FXML private Button saveButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private Label errorLabel;
    @FXML private Label machineIdCompanyLabel;

    Inventory inventory;
    Part part;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        part = MainController.getPartToModify();

        if(part instanceof InHouse)
        {
            inHouseButton.setSelected(true);
            machineIdCompanyLabel.setText("Machine ID");
            machineIdCompanyInput.setText(String.valueOf(((InHouse)part).getMachineId()));
        }

        if(part instanceof Outsourced)
        {
            outsourcedButton.setSelected(true);
            machineIdCompanyLabel.setText("Company Name");
            machineIdCompanyInput.setText(String.valueOf(((Outsourced)part).getCompanyName()));
        }

        idInput.setText(String.valueOf(part.getId()));
        nameInput.setText(part.getName());
        priceInput.setText(String.valueOf(part.getPrice()));
        invInput.setText(String.valueOf(part.getStock()));
        maxInput.setText(String.valueOf(part.getMax()));
        minInput.setText(String.valueOf(part.getMin()));
    }

    /**
     * This method changes the label in the UI to prompt for a Machine ID when part being added is In-House.
     */
    public void onActionInHouse() { machineIdCompanyLabel.setText("Machine ID");}

    /**
     * This method changes the label in the UI to prompt for a Company Name when the part being added is Outsourced
     */
    public void onActionOutsourced(){machineIdCompanyLabel.setText("Company Name");}

    /**
     * This method checks input text fields for validity and if conditions are met and replaces the Part in Inventory
     * with the updated part.
     * @throws IOException
     */
    public void onSaveButton() throws  IOException
    {
        int id = part.getId();
        String name = "";
        int stock = 0;
        double price = 0.00;
        int max = 0;
        int min = 0;
        int machineId = 0;
        String companyName = "";

        StringBuilder error = new StringBuilder("Error: ");

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
            int currentPartIndex = inventory.getAllParts().indexOf(part);

            if (inHouseButton.isSelected()) {
                InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
                inventory.updatePart(currentPartIndex, updatedPart);
            } else {
                Outsourced updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                inventory.updatePart(currentPartIndex, updatedPart);
            }
            returnToMain();
        }
    }

    /**
     * This method returns the user back to the main controller after a Part is added.
     * @throws IOException
     */
    private void returnToMain() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage)saveButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
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

}
