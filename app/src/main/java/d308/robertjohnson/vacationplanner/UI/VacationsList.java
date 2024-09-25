package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import d308.robertjohnson.vacationplanner.R;
import d308.robertjohnson.vacationplanner.data.Repository;
import d308.robertjohnson.vacationplanner.data.VacationDBBuilder;
import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

public class VacationsList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacations_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationsList.this, VacationDetail.class);
                startActivity(intent);
            }

        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setmVacations(allVacations);
        if(repository.getAllVacations().size()==0) {
            Vacation vacation = new Vacation(0, "Miami", "Vista", "01/02/2025", "01/07/2025");
            repository.insert(vacation);
            Excursion excursion = new Excursion(1, "Shopping", "01/05/2025", 1);
            repository.insert(excursion);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }
    @Override
    protected void onResume() {

        super.onResume();

        List<Vacation> allVacations=repository.getAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setmVacations(allVacations);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Vacation> allVacations = repository.getAllVacations();
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return true;
    }
}