package net.decusatis.mybicycleshopflaming.DAO;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import net.decusatis.mybicycleshopflaming.Entities.Product;

import java.util.List;

/**
 * Project: Bike Dolphin
 * Package: android.carolynbicycleshop.bikedolphin.DAO
 * <p>
 * User: carolyn.sher
 * Date: 10/22/2022
 * Time: 2:12 PM
 * <p>
 * Created with Android Studio
 * To change this template use File | Settings | File Templates.
 */
@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM PRODUCTS ORDER BY productID ASC")
    List<Product> getAllProducts();
}
