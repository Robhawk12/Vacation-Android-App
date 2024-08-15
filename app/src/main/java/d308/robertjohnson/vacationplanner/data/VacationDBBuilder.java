package d308.robertjohnson.vacationplanner.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import d308.robertjohnson.vacationplanner.dao.ExcursionDao;
import d308.robertjohnson.vacationplanner.dao.VacationDao;
import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class},version = 1,exportSchema = false)
public abstract class VacationDBBuilder extends RoomDatabase {

    private static volatile VacationDBBuilder Instance;
    public ExcursionDao excursionDao;
    public VacationDao vacationDao;


    static VacationDBBuilder getDatabase(final Context context){
        if (Instance == null){
            synchronized (VacationDBBuilder.class){
                if(Instance==null){
                    Instance = Room.databaseBuilder(context.getApplicationContext(),VacationDBBuilder.class,"My Vacation Builder")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return Instance;
    }
}
