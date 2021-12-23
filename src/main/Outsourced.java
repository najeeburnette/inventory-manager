package main;

/**
 * A subclass of Part which indicates the object is an Outsourced part.
 * @author Najee Burnette
 */

public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor for an instance of an Outsourced object.
     *
     * @param id the ID for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level of the part
     * @param min the minimum level for the part
     * @param max the maximum level for the part
     * @param companyName the company name of the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the companyName of the part.
     *
     * @param companyName the name of the Company from the Outsourced Part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * The gets the companyName of the part.
     *
     * @return companyName of the part
     */
    public String getCompanyName() {
        return companyName;
    }

}
