package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
    String startVacationDate;
    String endVacationDate;
    EditText editName;
    EditText editNote;
    TextView editDate;
    String excursionDate;
    Repository repository;
    Date startStartDate = null;
    Date endEndDate = null;


    DatePickerDialog.OnDateSetListener startDate;
    final Calendar vacationCalendarStart = Calendar.getInstance();
    final Calendar excursionCalendar = Calendar.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());


        excursionID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        excursionDate = getIntent().getStringExtra("date");

        vacationID = getIntent().getIntExtra("vacationID", -1);
        startVacationDate = getIntent().getStringExtra("startVacationDate");
        endVacationDate = getIntent().getStringExtra("endVacationDate");

        editName = findViewById(R.id.excursionName);
        editName.setText(name);
        editDate = findViewById(R.id.excursionDate);
        editDate.setText(excursionDate);

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);


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
                    Log.e("DebugTag", "Invalid position: " + position);
                    Toast.makeText(ExcursionDetail.this, "Invalid date.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startDate = (view, year, monthOfYear, dayOfMonth) -> {
            vacationCalendarStart.set(Calendar.YEAR, year);
            vacationCalendarStart.set(Calendar.MONTH, monthOfYear);
            vacationCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStart();
        };

        if (startVacationDate != null && endVacationDate != null) {
            try {
                startStartDate = sdf.parse(startVacationDate);
                endEndDate = sdf.parse(endVacationDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        editDate.setOnClickListener(v -> {
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

            if (startStartDate != null) {
                datePickerDialog.getDatePicker().setMinDate(startStartDate.getTime());
            }
            if (endEndDate != null) {
                datePickerDialog.getDatePicker().setMaxDate(endEndDate.getTime());
            }
            datePickerDialog.show();
        });
    }

    private void updateLabelStart() {
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
           DateRangeChecker checker = new DateRangeChecker();
            boolean isInRange = checker.validateDates(excursionDate,startVacationDate,endVacationDate);
            if (!isInRange){
                Toast.makeText(this,"Excursion is not in range.",Toast.LENGTH_LONG).show();
                return false;
            }

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
    public class DateRangeChecker{
        private boolean validateDates(String startDateStr, String endDateStr, String targetDateStr) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            try {
                Date date = sdf.parse(targetDateStr);
                Date start = sdf.parse(startDateStr);
                Date end = sdf.parse(endDateStr);
                long dateMillis = date.getTime();
                long startMillis = start .getTime();
                long endMillis = end.getTime();

                return dateMillis >= startMillis && dateMillis <= endMillis;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


}