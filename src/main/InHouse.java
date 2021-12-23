package main;

/**
 * A subclass of Part which indicates the object is an In-House part.
 * @author Najee Burnette
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for an instance of an In-House object.
     *
     * @param id the ID for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level of the part
     * @param min the minimum level for the part
     * @param max the maximum level for the part
     * @param machineId the machine ID for the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * The setter for the machineId.
     *
     * @param machineId the machineId of the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * The getter for the machineId.
     *
     * @return machineId of the part
     */
    public int getMachineId() {
        return machineId;
    }
}

