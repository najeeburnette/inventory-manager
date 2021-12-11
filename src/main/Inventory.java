package main;

import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts;
    private static ObservableList<Product> allProducts;

    public  static void addPart(Part newPart){allParts.add(newPart);}

    public  static void addProduct(Product newProduct){allProducts.add(newProduct);}

    public static Part lookupPart(int partId){
        Part temp = null;
        for (Part part : allParts){
            if(partId == part.getId()){ temp = part;}
        }
        return temp;
    }

    public static Product lookupProduct(int productId){
        Product temp = null;
        for (Product product : allProducts){
            if(productId == product.getId()){ temp = product;}
        }
        return temp;
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> foundParts;

        if(partName.length()==0){
            foundParts = allParts;
        }
        else {
            for (int i = 0; i < allParts.size(); i++) {
                if()
            }
        }
    }

    public static ObservableList<Product> lookupProduct(String partName){}

    public static void updatePart(int index, Part selectedPart){allParts.set(index, selectedPart);}

    public static void updateProduct(int index, Product selectedProduct){allProducts.set(index, selectedProduct);}

    public static boolean deletePart(Part selectedPart){return allParts.remove(selectedPart);}

    public static boolean deleteProduct(Product selectedProduct){return allParts.remove(selectedProduct);}

    public static ObservableList<Part> getAllParts(){return allParts;}

    public static ObservableList<Product> getAllProducts(){return allProducts;}
}
