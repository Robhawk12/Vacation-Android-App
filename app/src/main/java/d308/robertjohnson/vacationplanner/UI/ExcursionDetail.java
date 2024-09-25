package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import d308.robertjohnson.vacationplanner.R;
import d308.robertjohnson.vacationplanner.data.Repository;
import d308.robertjohnson.vacationplanner.entities.Excursion;
import d308.robertjohnson.vacationplanner.entities.Vacation;

public class ExcursionDetail extends AppCompatActivity {
    Vacation currentVacation;
    int numExcursions;
    String name;
    int excursionID;
    int vacationID;
    String startVacationDate;
    String endVacationDate;
    EditText editName;
    EditText editNote;
    EditText editDate;
    String excursionDate;
    Repository repository;
    Date startStartDate = null;
    Date endEndDate = null;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar vacationCalendarStart = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());


        excursionID = getIntent().getIntExtra("id",-1);
        name = getIntent().getStringExtra("name");
        excursionDate = getIntent().getStringExtra("excursionDate");
        vacationID = getIntent().getIntExtra("vacationID",-1);
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");
        //editNote=findViewById(R.id.excursionnote);
        editName = findViewById(R.id.excursionName);
        editName.setText(name);
        editDate=findViewById(R.id.excursionDate);
        editDate.setText(excursionDate);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);


        ArrayList<Vacation> vacationArrayList= new ArrayList<>();
        vacationArrayList.addAll(repository.getAllVacations());
        ArrayList<Integer> vacationIdList= new ArrayList<>();
        for(Vacation vacation:vacationArrayList){
            vacationIdList.add(vacation.getVacationID());
        }
        ArrayAdapter<Integer> vacationIdAdapter= new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,vacationIdList);
        Spinner spinner=findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);
        spinner.setSelection(vacationID - 1);

        Log.d("DebugTag", "vacationIdList size: " + vacationIdList.size());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DebugTag", "Selected position: " + position + ", List size: " + vacationIdList.size());
                if (position >= 0 && position < vacationIdList.size()) {
                    vacationID = vacationIdList.get(position);
                } else {
                    Log.e("DebugTag", "Invalid position: " + position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        if(startVacationDate != null && endVacationDate != null) {
            try {
                startStartDate = sdf.parse(startVacationDate);
                endEndDate = sdf.parse(endVacationDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {

            Log.e("ExcursionDetails", "Received null for start or end vacation date");
        }

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Date date;
                String info=editDate.getText().toString();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ExcursionDetail.this, startDate, vacationCalendarStart
                        .get(Calendar.YEAR), vacationCalendarStart.get(Calendar.MONTH),
                        vacationCalendarStart.get(Calendar.DAY_OF_MONTH));

                if (startStartDate != null) {
                    datePickerDialog.getDatePicker().setMinDate(startStartDate.getTime());
                }
                if (endEndDate != null) {
                    datePickerDialog.getDatePicker().setMaxDate(endEndDate.getTime());
                }
                datePickerDialog.show();
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(vacationCalendarStart.getTime()));
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
                if (item.getItemId() == R.id.saveExcursion) {
                    Excursion excursion;
                    if (excursionID == -1) {
                        if (repository.getAllExcursions().size() == 0)
                            excursionID = 1;
                        else
                            excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                        excursion = new Excursion(0, editName.getText().toString(), editDate.getText().toString(), vacationID);
                        repository.insert(excursion);
                    } else {
                        excursion = new Excursion(excursionID, editName.getText().toString(), editDate.getText().toString(), vacationID);
                        repository.update(excursion);
                    }
                    this.finish();
                    return true;
                }


                return true;
            }


}