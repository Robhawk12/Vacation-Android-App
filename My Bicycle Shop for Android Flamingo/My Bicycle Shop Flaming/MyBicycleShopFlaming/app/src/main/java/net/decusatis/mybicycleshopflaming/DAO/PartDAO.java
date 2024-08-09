package net.decusatis.mybicycleshopflaming.DAO;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import net.decusatis.mybicycleshopflaming.Entities.Part;

import java.util.List;

/**
 * Project: Bike Dolphin
 * Package: android.carolynbicycleshop.bikedolphin.DAO
 * <p>
 * User: carolyn.sher
 * Date: 10/22/2022
 * Time: 2:16 PM
 * <p>
 * Created with Android Studio
 * To change this template use File | Settings | File Templates.
 */
@Dao
public interface PartDAO {
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Part part);

    @Update
    void update(Part part);

    @Delete
    void delete(Part part);

    @Query("SELECT * FROM PARTS ORDER BY partID ASC")
    List<Part> getAllParts();
}
