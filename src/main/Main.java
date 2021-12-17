package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setTitle("Inventory Manager");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    public static void main(String[] args){

        Outsourced tire1 = new Outsourced(1, "Haley Tire", 16.00, 8, 4, 20, "Harley Boys Tires");
        Outsourced tire2 = new Outsourced(2, "Small Harley Tire", 12.00, 4, 2, 16, "Harley Boys Tires");
        Outsourced tire5 = new Outsourced(5, "Big Miles Tire", 20.00, 10, 4, 20, "Big Miles Depot");
        InHouse tire3 = new InHouse(3, "Black Tire", 11.00, 8, 8, 24, 10);
        InHouse tire4 = new InHouse(4, "Large Black Tire", 14.00, 12, 8, 24, 12);
        Product bike1 = new Product(12,"Ninja Bike", 200.00, 12, 8, 16);
        Product bike2 = new Product(10,"Wasp Bike", 130.00, 8, 6, 10);
        Product bike3 = new  Product(13,"Sports Bike", 240.00, 10, 8, 14);

        Inventory.addPart(tire1);
        Inventory.addPart(tire2);
        Inventory.addPart(tire3);
        Inventory.addPart(tire4);
        Inventory.addPart(tire5);
        Inventory.addProduct(bike1);
        Inventory.addProduct(bike2);
        Inventory.addProduct(bike3);

        launch(args);
    }
}
