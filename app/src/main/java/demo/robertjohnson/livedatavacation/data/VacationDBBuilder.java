package demo.robertjohnson.livedatavacation.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import demo.robertjohnson.livedatavacation.dao.ExcursionDao;
import demo.robertjohnson.livedatavacation.dao.VacationDao;
import demo.robertjohnson.livedatavacation.entity.Excursion;
import demo.robertjohnson.livedatavacation.entity.Vacation;

@Database(entities = {Vacation.class, Excursion.class},version = 0,exportSchema = false)
public abstract class VacationDBBuilder extends RoomDatabase {

    public abstract VacationDao vacationDao();

    public abstract ExcursionDao excursionDao();

    private static volatile VacationDBBuilder INSTANCE;

    //private static final ExecutorService d

    public static VacationDBBuilder getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (VacationDBBuilder.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VacationDBBuilder.class, "vacation_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
