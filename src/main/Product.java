package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product object containing associated parts of the product.
 * @author Najee Burnette
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for an instance of a Product object.
     *
     * @param id the ID for the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the inventory level of the product
     * @param min the minimum level for the product
     * @param max the maximum level for the product
     */
    public Product(int id, String name, double price, int stock,int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets id of the Product.
     *
     * @param id the id of the Product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets id of the Product.
     *
     * @return the id of the Product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets name of the Product.
     *
     * @param name the name of the Product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets name of the Product.
     *
     * @return the name of the Product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets price of the Product.
     *
     * @param price the name of the Product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets price of the Product.
     *
     * @return the price of the Product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets amount of stock of the Product.
     *
     * @param stock the amount of stock of the Product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets amount of stock of the Product.
     *
     * @return the amount of stock of the Product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the minimum stock of the Product.
     *
     * @param min the minimum stock of the Product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the minimum of stock of the Product.
     *
     * @return the minimum stock of the Product
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the maximum stock of the Product.
     *
     * @param max the maximum stock of the Product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the maximum of stock of the Product.
     *
     * @return the maximum stock of the Product
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds the Part to the list of associated Parts of the Product
     *
     * @param part the part of the Product
     */
    public void addAssociatedPart(Part part){associatedParts.add(part);}

    /**
     * Delete the Part from the list of associated Parts of the Product
     *
     * @param selectedAssociatedPart the selected associated part
     * @return the boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        try {associatedParts.remove(selectedAssociatedPart);}
        catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * Gets the list of all associated parts of the Product.
     *
     * @return the all associated parts of the Product.
     */
    public ObservableList<Part> getAllAssociatedParts(){return associatedParts;}
}
