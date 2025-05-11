package data;

import java.io.Serializable;

public class Sale implements Serializable {
    private static final long serialVersionUID = 5L;

    public String foodCategory;
    public String productCaregory;
    public int amount;
    public String storename;
    public Sale(String foodCategory, String productCaregory, int amount, String storename) {
        this.foodCategory = foodCategory;
        this.productCaregory = productCaregory;
        this.amount = amount;
        this.storename = storename;
    }
    public int getAmount(){
        return amount;
    }

    public String getStorename(){
        return storename;
    }
    
    @Override
    public String toString() {
        return "Sale{\n" +
            "foodCategory='" + foodCategory + '\'' +
            ", productCaregory='" + productCaregory + '\'' +
            ", amount=" + amount +
            ", storename='" + storename + '\'' +
            "\n}";
    }
}
