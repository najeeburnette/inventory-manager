package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public  static void addPart(Part newPart){allParts.add(newPart);}

    public  static void addProduct(Product newProduct){allProducts.add(newProduct);}

    public static Part lookupPart(int partId){
        Part temp = null;
        for (Part part : allParts){
            if( part.getId() == partId){ temp = part;}
        }
        return temp;
    }

    public static Product lookupProduct(int productId){
        Product temp = null;
        for (Product product : allProducts){
            if(product.getId() == productId){temp = product;}
        }
        return temp;
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> result = FXCollections.observableArrayList();
        
            for (int i = 0; i < allParts.size(); i++) {
                if(allParts.get(i).getName().contains(partName)){
                    result.add(allParts.get(i));
                }
            }
            return result;
        }


    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> result = FXCollections.observableArrayList();
        
            for (int i = 0; i < allProducts.size(); i++) {
                if (allParts.get(i).getName().contains(productName)) {
                    result.add(allProducts.get(i));
                }
            }
        

        return result;

    }

    public static void updatePart(int index, Part selectedPart){allParts.set(index, selectedPart);}

    public static void updateProduct(int index, Product selectedProduct){allProducts.set(index, selectedProduct);}

    public static boolean deletePart(Part selectedPart){return allParts.remove(selectedPart);}

    public static boolean deleteProduct(Product selectedProduct){return allProducts.remove(selectedProduct);}

    public static ObservableList<Part> getAllParts(){return allParts;}

    public static ObservableList<Product> getAllProducts(){return allProducts;}
}
