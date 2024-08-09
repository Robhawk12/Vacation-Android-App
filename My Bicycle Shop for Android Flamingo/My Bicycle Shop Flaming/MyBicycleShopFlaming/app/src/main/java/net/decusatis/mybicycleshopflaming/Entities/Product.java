package net.decusatis.mybicycleshopflaming.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Project: Bike Dolphin
 * Package: android.carolynbicycleshop.bikedolphin.Entities
 * <p>
 * User: carolyn.sher
 * Date: 10/22/2022
 * Time: 2:02 PM
 * <p>
 * Created with Android Studio
 * To change this template use File | Settings | File Templates.
 */
@Entity(tableName ="products")
public class Product {
    @PrimaryKey(autoGenerate=true)
    private int productID;

    private String productName;
    private double productPrice;

    public Product(int productID, String productName, double productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}

