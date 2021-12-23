package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory that holds the lists of all Parts and Products.
 * @author Najee Burnette
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the existing Part list in the Inventory.
     *
     * @param newPart the new added part
     */
    public  static void addPart(Part newPart){allParts.add(newPart);}

    /**
     * Adds a new product to the existing Product list in the Inventory.
     *
     * @param newProduct the new product
     */
    public  static void addProduct(Product newProduct){allProducts.add(newProduct);}

    /**
     * Searches the part list for the Part by part id.
     *
     * @param partId the part id
     * @return the part or null if not found
     */
    public static Part lookupPart(int partId){
        Part temp = null;
        for (Part part : allParts){
            if( part.getId() == partId){ temp = part;}
        }
        return temp;
    }

    /**
     * Searches the product list for the Product by product id.
     *
     * @param productId The product ID.
     * @return The product or null if not found
     */
    public static Product lookupProduct(int productId){
        Product temp = null;
        for (Product product : allProducts){
            if(product.getId() == productId){temp = product;}
        }
        return temp;
    }

    /**
     * Searches the part list for the Part by name.
     *
     * @param partName The part name
     * @return the list of parts found
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> result = FXCollections.observableArrayList();
        
            for (int i = 0; i < allParts.size(); i++) {
                if(allParts.get(i).getName().contains(partName)){
                    result.add(allParts.get(i));
                }
            }
            return result;
        }

    /**
     * Searches the product list for the Product by name.
     *
     * @param productName The product name
     * @return the list of products found
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> result = FXCollections.observableArrayList();
        
            for (int i = 0; i < allProducts.size(); i++) {
                if (allParts.get(i).getName().contains(productName)) {
                    result.add(allProducts.get(i));
                }
            }

        return result;

    }


    /**
     * Updates the Part at the passed index with the new Part also passed.
     *
     * @param index the index of the Part being updated.
     * @param selectedPart the new Part
     */
    public static void updatePart(int index, Part selectedPart){allParts.set(index, selectedPart);}

    /**
     * Updates the Product at the passed index with the new Product that is also passed in this method.
     *
     * @param index the index of the Product being updated.
     * @param selectedProduct the new Product
     */
    public static void updateProduct(int index, Product selectedProduct){allProducts.set(index, selectedProduct);}

    /**
     * Removes the selected Part from the Inventory.
     *
     * @param selectedPart the selected part to remove
     * @return returns a boolean that is true if part was deleted
     */

    public static boolean deletePart(Part selectedPart){return allParts.remove(selectedPart);}

    /**
     * Removes the selected Product from the Inventory.
     *
     * @param selectedProduct The selected product to remove
     * @return returns a boolean that is true if part was deleted
     */

    public static boolean deleteProduct(Product selectedProduct){return allProducts.remove(selectedProduct);}

    /**
     * Gets the list of all Parts.
     *
     * @return the list of all parts
     */
    public static ObservableList<Part> getAllParts(){return allParts;}

    /**
     * Gets the list of all Products.
     *
     * @return the list of all Products
     */
    public static ObservableList<Product> getAllProducts(){return allProducts;}
}
