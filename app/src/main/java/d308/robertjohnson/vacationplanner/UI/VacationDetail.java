package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import d308.robertjohnson.vacationplanner.R;
import d308.robertjohnson.vacationplanner.data.Repository;
import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

public class VacationDetail extends AppCompatActivity {
    String title;
    String hotel;
    String startDate;
    String endDate;
    int vacationID;

    EditText editTitle;
    EditText editHotel;
    EditText editStart;
    EditText editEnd;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_detail);



        editTitle = findViewById(R.id.vacation_name);
        editHotel = findViewById(R.id.hotel_name);
        editStart = findViewById(R.id.startdate);
        editEnd = findViewById(R.id.enddate);

        vacationID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        hotel = getIntent().getStringExtra("hotel");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        editTitle.setText(title);
        editHotel.setText(hotel);
        editStart.setText(startDate);
        editEnd.setText(endDate);


        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> assocExcursion = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()){
            if(e.getVacationID() == vacationID) assocExcursion.add(e);
        }
        excursionAdapter.setExcursions(assocExcursion);
        FloatingActionButton fab1 =findViewById(R.id.floatingActionButton2);
        fab1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetail.this, ExcursionDetail.class);
                intent.putExtra("vacationID",vacationID);
                startActivity(intent);
            }
        }));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.vacationsave) {
            Vacation vacation;
            if (vacationID == -1) {
                if (repository.getAllVacations().size() == 0) vacationID = 1;
                else
                 vacationID = repository.getAllVacations()
                            .get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacation(vacationID, editTitle.getText().toString(),
                        editHotel.getText().toString(), editStart.getText().toString(),
                        editEnd.getText().toString());
                repository.insert(vacation);
                this.finish();
            } else {
                vacation = new Vacation(vacationID, editTitle.getText().toString(),
                        editHotel.getText().toString(), editStart.getText().toString(),
                        editEnd.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }
        return true;

    }
}