package demo.robertjohnson.livedatavacation.model;

import static androidx.fragment.app.FragmentManager.TAG;
import static demo.robertjohnson.livedatavacation.model.VacationViewModel.repository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import demo.robertjohnson.livedatavacation.R;
import demo.robertjohnson.livedatavacation.data.Repository;
import demo.robertjohnson.livedatavacation.entity.Excursion;
import demo.robertjohnson.livedatavacation.entity.Vacation;

public class MainVacation extends AppCompatActivity {
    private VacationViewModel vacationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vacation);

        if(!repository.getAllVacations().isInitialized()) {
            Vacation vacation = new Vacation(0, "Miami", "Vista", "01/02/2025", "01/07/2025");
            repository.insert(vacation);
            Excursion excursion = new Excursion(0, "Shopping", "01/05/2025", 0);
            repository.insert(excursion);
        }

        vacationViewModel = new ViewModelProvider.AndroidViewModelFactory(MainVacation.this
                .getApplication())
                .create(VacationViewModel.class);

        vacationViewModel.getAllVacations().observe(this, vacations -> {
            Log.d("TAG", "onCreate: " + vacations.get(0).getName());

        });
    }
}