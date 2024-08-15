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
    public void insert(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDao.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDao.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDao.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Excursion>getAllExcursions(){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDao.getAllExcursions();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }
    public List<Excursion>getAssociatedExcursions(int excursionID){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDao.getAssociatedExcursions(excursionID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }
    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDao.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDao.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDao.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
