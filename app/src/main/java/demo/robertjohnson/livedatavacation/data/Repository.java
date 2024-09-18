package demo.robertjohnson.livedatavacation.data;

import static demo.robertjohnson.livedatavacation.data.VacationDBBuilder.databaseExecutor;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import demo.robertjohnson.livedatavacation.dao.ExcursionDao;
import demo.robertjohnson.livedatavacation.dao.VacationDao;
import demo.robertjohnson.livedatavacation.entity.Excursion;
import demo.robertjohnson.livedatavacation.entity.Vacation;

public class Repository {
    private ExcursionDao mExcursionDao;
    private VacationDao mVacationDao;


    private LiveData<List<Vacation>> mAllVacations;
    private LiveData<List<Excursion>> mAllExcursions;
    private LiveData<List<Excursion>> mAssocExcursions;



    public Repository(Application application) {
        VacationDBBuilder db =VacationDBBuilder.getDatabase(application);
        mExcursionDao = db.excursionDao();
        mVacationDao = db.vacationDao();

        mAllVacations = mVacationDao.getAllVacations();
        mAllExcursions = mExcursionDao.getAllExcursions();



    }

    public  LiveData<List<Vacation>> getAllVacations(){
        return mAllVacations;
    }
    public LiveData<List<Excursion>> getAllExcursions(){return mAllExcursions; }

    public LiveData <List<Excursion>>getAssociatedExcursions(int vacationID){
        databaseExecutor.execute(()->{
            mAssocExcursions=mExcursionDao.getAssociatedExcursions(vacationID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssocExcursions;
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
