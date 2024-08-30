package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import d308.robertjohnson.vacationplanner.R;
import d308.robertjohnson.vacationplanner.data.Repository;
import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

public class ExcursionDetail extends AppCompatActivity {

    String name;

    int excursionID;
    int vacationID;
    EditText editName;
    TextView editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar vacationCalendarStart = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.excursionName);
        editName.setText(name);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        editDate = findViewById(R.id.excursionDate);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);

        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getAllVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationID());
        }
        ArrayAdapter<Integer> vacationIdAdapter =
                new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, vacationIdList);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);
        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                vacationCalendarStart.set(Calendar.YEAR, year);
                vacationCalendarStart.set(Calendar.MONTH, monthOfYear);
                vacationCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }

        };
    }

    private void updateLabelStart() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);

        editDate.setText(simpleDateFormat.format(vacationCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if(item.getItemId()==R.id.saveExcursion){
            Excursion excursion;
            if(excursionID == -1){
                if(repository.getAllExcursions().size()== 0)
            excursionID = 1;
            else
                excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size()-1).getExcursionID() +1;
            excursion = new Excursion(excursionID,editName.getText().toString(),editDate.getText().toString(),vacationID);
            repository.insert(excursion);
            }else{
                excursion = new Excursion(excursionID,editName.getText().toString(),editDate.getText().toString(),vacationID);
                repository.update(excursion);
            }
            return true;
        }


        return true;
    }
}