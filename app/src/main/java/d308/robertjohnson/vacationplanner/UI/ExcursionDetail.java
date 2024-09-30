package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    String vacationStartDate;
    String vacationEndDate;
    EditText editName;
    EditText editNote;
    TextView editDate;
    TextView startView;
    TextView endView;
    String excursionDate;
    Repository repository;
    Date minDate = null;
    Date maxDate = null;
    final Calendar vacationCalendarStart = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());
        excursionID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        excursionDate = getIntent().getStringExtra("date");
        vacationID = getIntent().getIntExtra("vacationID", -1);
        vacationStartDate = getIntent().getStringExtra("startDate");
        vacationEndDate = getIntent().getStringExtra("endDate");

        editName = findViewById(R.id.excursionName);
        editName.setText(name);
        startView = findViewById(R.id.vacationstart);
        startView.setText(vacationStartDate);
        endView = findViewById(R.id.vacationend);
        endView.setText(vacationEndDate);
        editDate = findViewById(R.id.excursionDate);
        editDate.setText(excursionDate);
        DatePickerDialog.OnDateSetListener startDate;

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        if (vacationStartDate != null && vacationEndDate != null) {
            try {
                minDate = sdf.parse(vacationStartDate);
                maxDate = sdf.parse(vacationEndDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getAllVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationID());
        }

        ArrayAdapter<Integer> vacationIdAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, vacationIdList);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);
        spinner.setSelection(vacationID - 1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0 && position < vacationIdList.size()) {
                    vacationID = vacationIdList.get(position);
                } else {
                    Toast.makeText(ExcursionDetail.this, "Invalid date.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startDate  = (view, year, monthOfYear, dayOfMonth) -> {
            vacationCalendarStart.set(Calendar.YEAR, year);
            vacationCalendarStart.set(Calendar.MONTH, monthOfYear);
            vacationCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelExcursion();
        };

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String def = editDate.getText().toString();
                if (def.equals("")) def = "01/01/25";
                try {
                    vacationCalendarStart.setTime(sdf.parse(def));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ExcursionDetail.this, startDate, vacationCalendarStart
                        .get(Calendar.YEAR), vacationCalendarStart.get(Calendar.MONTH),
                        vacationCalendarStart.get(Calendar.DAY_OF_MONTH));

                if (minDate != null) {
                    datePickerDialog.getDatePicker().setMinDate(minDate.getTime());
                }
                if (maxDate != null) {
                    datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
                }
                datePickerDialog.show();
            }
        });
    }

    private void updateLabelExcursion() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
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
            excursionDate = null;


            excursionDate = editDate.getText().toString();


            if (excursionID == -1) {
                if (repository.getAllExcursions().size() == 0)
                    excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                excursion = new Excursion(0, editName.getText().toString(),
                        editDate.getText().toString(), vacationID,vacationStartDate,vacationEndDate);
                repository.insert(excursion);
            } else {
                //boolean isInRange = checker.validateDates(excursionDate,startVacationDate,endVacationDate);
               /* SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                try {
                    Date date = sdf.parse(excursionDate);
                    Date start = null;
                    Date end = sdf.parse(endVacationDate);
                    start = sdf.parse(startVacationDate);


                    long dateMillis = date.getTime();
                    long startMillis = start.getTime();
                    long endMillis = end.getTime();

                    if (dateMillis >= startMillis && dateMillis <= endMillis) {
                        Toast.makeText(this, "Excursion is not in range.",
                                Toast.LENGTH_LONG).show();
                    }else{*/
                excursion = new Excursion(excursionID, editName.getText().toString(),
                        editDate.getText().toString(), vacationID,
                        vacationStartDate,vacationEndDate);
                repository.update(excursion);
            }
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.excursionDelete) {
            Excursion currentExcursion = null;
            for (Excursion e : repository.getAllExcursions()) {
                if (e.getExcursionID() == excursionID) currentExcursion = e;
            }
            if (currentExcursion != null) {
                repository.delete(currentExcursion);
            }
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.notifyExcursion) {
            String excName = editName.getText().toString();
            String dateString = editDate.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date excDate = null;
            try {
                excDate = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trig = excDate.getTime();
            Intent intent = new Intent(ExcursionDetail.this, ExcursionBCReceiver.class);
            intent.putExtra("exckey", "Your " + excName + " excursion is Today! " + excDate);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ExcursionDetail.this, ++MainActivity.excAlert,
                    intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trig, pendingIntent);
            return true;
        }
        return true;
    }




    /*private class DateRangeChecker {
        private boolean validateDates(String startDateStr, String endDateStr, String targetDateStr) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            try {
                Date date = sdf.parse(targetDateStr);
                Date start = sdf.parse(startDateStr);
                Date end = sdf.parse(endDateStr);
                long dateMillis = date.getTime();
                long startMillis = start.getTime();
                long endMillis = end.getTime();

                return dateMillis >= startMillis && dateMillis <= endMillis;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }*/


}