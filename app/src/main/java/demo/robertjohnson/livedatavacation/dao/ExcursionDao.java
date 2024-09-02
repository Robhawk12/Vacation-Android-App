package demo.robertjohnson.livedatavacation.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import demo.robertjohnson.livedatavacation.entity.Excursion;
import demo.robertjohnson.livedatavacation.entity.Vacation;

public interface ExcursionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
void insert(Excursion excursion);

    @Query("DELETE FROM excursions")
    void deleteAll();

    @Query("SELECT * FROM excursions ORDER BY name ASC")
    LiveData<List<Vacation>> getAllExcursions();

    @Query("SELECT * FROM contact_table WHERE contact_table.id == :id")
    LiveData<Contact> get(int id);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}
