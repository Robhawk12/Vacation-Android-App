package demo.robertjohnson.livedatavacation.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import demo.robertjohnson.livedatavacation.entity.Vacation;
@Dao
public interface VacationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Query("DELETE FROM vacations")
    void deleteAll();

    @Query("SELECT * FROM vacations ORDER BY name ASC")
    LiveData<List<Vacation>> getAllVacations();

    @Query("SELECT * FROM vacations WHERE vacations.id == :id")
    LiveData<Vacation> get(int id);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);
}

