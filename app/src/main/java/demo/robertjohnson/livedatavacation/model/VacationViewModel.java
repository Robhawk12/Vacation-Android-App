package demo.robertjohnson.livedatavacation.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import demo.robertjohnson.livedatavacation.data.Repository;
import demo.robertjohnson.livedatavacation.entity.Vacation;

public class VacationViewModel extends AndroidViewModel {

    public static Repository repository;
    public final LiveData<List<Vacation>> allVacations;

    public VacationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allVacations = repository.getAllVacations();
    }

    public LiveData<List<Vacation>> getAllVacations() {return allVacations;}

    public static void  insert(Vacation vacation){repository.insert(vacation);}
}
