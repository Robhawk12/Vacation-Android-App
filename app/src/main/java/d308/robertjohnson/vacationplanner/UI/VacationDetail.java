package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import d308.robertjohnson.vacationplanner.R;
import d308.robertjohnson.vacationplanner.data.Repository;
import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

public class VacationDetail extends AppCompatActivity {
    String title;
    String hotel;
    String startVacationDate;
    String endVacationDate;
    int vacationID;
    EditText editTitle;
    EditText editHotel;
    TextView editStartDate;
    TextView editEndDate;
    Vacation currentVacation;
    int numExcursions;
    DatePickerDialog.OnDateSetListener startVacDate;
    DatePickerDialog.OnDateSetListener endVacDate;
    final Calendar vacationCalendarStart = Calendar.getInstance();
    final Calendar vacationCalendarEnd = Calendar.getInstance();
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_detail);
        editTitle = findViewById(R.id.vacation_name);
        editHotel = findViewById(R.id.hotel_name);
        editStartDate = findViewById(R.id.startdate);
        editEndDate = findViewById(R.id.enddate);
        vacationID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        hotel = getIntent().getStringExtra("hotel");
        startVacationDate = getIntent().getStringExtra("startDate");
        endVacationDate = getIntent().getStringExtra("endDate");
        editTitle.setText(title);
        editHotel.setText(hotel);
        editStartDate.setText(startVacationDate);
        editEndDate.setText(endVacationDate);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

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
                intent.putExtra("startVacationDate", startVacationDate);
                intent.putExtra( "endVacationDate", endVacationDate);
                startActivity(intent);
            }
        }));
        startVacDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                vacationCalendarStart.set(Calendar.YEAR, year);
                vacationCalendarStart.set(Calendar.MONTH, monthOfYear);
                vacationCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }

        };

        endVacDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                vacationCalendarEnd.set(Calendar.YEAR, year);
                vacationCalendarEnd.set(Calendar.MONTH, monthOfYear);
                vacationCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info=editStartDate.getText().toString();
                if(info.equals(""))info="01/01/25";
                try{
                 vacationCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetail.this, startVacDate, vacationCalendarStart
                        .get(Calendar.YEAR), vacationCalendarStart.get(Calendar.MONTH),
                        vacationCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info=editEndDate.getText().toString();
                if(info.equals(""))info="01/01/25";
                try{
                    vacationCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(VacationDetail.this, endVacDate, vacationCalendarStart
                        .get(Calendar.YEAR), vacationCalendarStart.get(Calendar.MONTH),
                        vacationCalendarStart.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(vacationCalendarStart.getTimeInMillis());

                datePickerDialog.show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(vacationCalendarStart.getTime()));
    }
    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(vacationCalendarEnd.getTime()));
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
                        editHotel.getText().toString(), editStartDate.getText().toString(),
                        editEndDate.getText().toString());
                repository.insert(vacation);
                this.finish();
            } else {
                vacation = new Vacation(vacationID, editTitle.getText().toString(),
                        editHotel.getText().toString(), editStartDate.getText().toString(),
                        editEndDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }
        this.finish();
        return true;

    }
    @Override
    public void onResume(){
        super.onResume();
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

    }
}