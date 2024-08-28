package d308.robertjohnson.vacationplanner.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import d308.robertjohnson.vacationplanner.dao.ExcursionDao;
import d308.robertjohnson.vacationplanner.dao.VacationDao;
import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 1, exportSchema = false)
public abstract class VacationDBBuilder extends RoomDatabase {
    public abstract ExcursionDao excursionDao();

    public abstract VacationDao vacationDao();


    private static volatile VacationDBBuilder Instance;

    static VacationDBBuilder getDatabase(final Context context) {
        if (Instance == null) {
            synchronized (VacationDBBuilder.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(), VacationDBBuilder.class, "My_Vacation_Builder.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return Instance;
    }


}
