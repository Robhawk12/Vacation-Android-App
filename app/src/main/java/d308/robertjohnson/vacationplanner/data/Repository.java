package d308.robertjohnson.vacationplanner.data;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import d308.robertjohnson.vacationplanner.dao.ExcursionDao;
import d308.robertjohnson.vacationplanner.dao.VacationDao;
import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

public class Repository {
   private ExcursionDao mExcursionDao;
   private VacationDao mVacationDao;

    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDBBuilder db =VacationDBBuilder.getDatabase(application);
        mExcursionDao = db.excursionDao;
        mVacationDao = db.vacationDao;
    }

    public List<Vacation>getmAllVacations(){
        databaseExecutor.execute(()->{
            mAllVacations=mVacationDao.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVacations;
    }
}
