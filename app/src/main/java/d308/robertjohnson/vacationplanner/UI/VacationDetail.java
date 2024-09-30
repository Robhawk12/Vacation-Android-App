package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    EditText editNote;
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
        editTitle = findViewById(R.id.vacation_name);//find string displayed
        editHotel = findViewById(R.id.hotel_name);
        editStartDate = findViewById(R.id.startdate);
        editEndDate = findViewById(R.id.enddate);
        vacationID = getIntent().getIntExtra("id", -1);//grabs information from db
        title = getIntent().getStringExtra("title");
        hotel = getIntent().getStringExtra("hotel");
        startVacationDate = getIntent().getStringExtra("startDate");
        endVacationDate = getIntent().getStringExtra("endDate");

        editTitle.setText(title);
        editHotel.setText(hotel);
        editStartDate.setText(startVacationDate);
        editEndDate.setText(endVacationDate);
        editNote = findViewById(R.id.vacationNote);
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> assocExcursion = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationID() == vacationID) assocExcursion.add(e);
        }
        excursionAdapter.setExcursions(assocExcursion);

        FloatingActionButton fab1 = findViewById(R.id.floatingActionButton2);
        fab1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetail.this, ExcursionDetail.class);
                intent.putExtra("vacationID", vacationID);
                intent.putExtra("startDate", startVacationDate);
                intent.putExtra("endDate", endVacationDate);

                startActivity(intent);
            }
        }));
        startVacDate = (view, year, monthOfYear, dayOfMonth) -> {
            vacationCalendarStart.set(Calendar.YEAR, year);
            vacationCalendarStart.set(Calendar.MONTH, monthOfYear);
            vacationCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabelStart();
        };

        endVacDate = (view, year, monthOfYear, dayOfMonth) -> {
            vacationCalendarEnd.set(Calendar.YEAR, year);
            vacationCalendarEnd.set(Calendar.MONTH, monthOfYear);
            vacationCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEnd();
        };

        editStartDate.setOnClickListener(v -> {
            Date date;
            String def = editStartDate.getText().toString();
            if(def.equals(""))def="01/01/25";
            try{
                vacationCalendarStart.setTime(sdf.parse(def));
            }catch (ParseException e){
                e.printStackTrace();
            }
            new DatePickerDialog(VacationDetail.this, startVacDate, vacationCalendarStart
                    .get(Calendar.YEAR), vacationCalendarStart.get(Calendar.MONTH),
                    vacationCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });

        editEndDate.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(VacationDetail.this,
                    endVacDate, vacationCalendarStart
                    .get(Calendar.YEAR), vacationCalendarStart.get(Calendar.MONTH),
                    vacationCalendarStart.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.getDatePicker().setMinDate(vacationCalendarStart.getTimeInMillis());

            datePickerDialog.show();
        });
    }

    private void updateLabelStart() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editStartDate.setText(sdf.format(vacationCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editEndDate.setText(sdf.format(vacationCalendarEnd.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }
    private void scheduleAlarm(AlarmManager alarmManager, long triggerTime, String message, int notificationId) {
        Intent intent = new Intent(VacationDetail.this, VacationBCReceiver.class);
        intent.putExtra("show", message);
        intent.putExtra("notification_id", notificationId);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetail.this, notificationId, intent,
                PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.vacationdelete) {
            for (Vacation v : repository.getAllVacations()) {
                if (v.getVacationID() == vacationID) currentVacation = v;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }

            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetail.this, currentVacation.getTitle() + " was deleted",
                        Toast.LENGTH_LONG).show();
                VacationDetail.this.finish();
            } else {
                Toast.makeText(VacationDetail.this, "Can't delete a Vacation with excursions",
                        Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.vacationsave) {
            //validateDates();
            /*if (validateDates() == 1){
                VacationDetail.this.finish();
            }*/
            startVacationDate =  editStartDate.getText().toString();
            endVacationDate = editEndDate.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            try {
                Date s  = sdf.parse(startVacationDate);
                Date e  = sdf.parse(endVacationDate);
                //assert e != null;
                if(e.before(s )){
                    Toast.makeText(VacationDetail.this,"Start date must be before end date!",
                            Toast.LENGTH_LONG).show();
                    this.finish();

                }
            }catch (ParseException e){
                e.printStackTrace();
            }
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
        if (item.getItemId() == R.id.share) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TEXT, "Welcome to " + editTitle.getText().toString() + " you're staying at "
                    + editHotel.getText().toString() + " From " + editStartDate.getText().toString() + " to " + editEndDate.getText().toString() + "." +
                    " Note: " + editNote.getText().toString());
            sentIntent.putExtra(Intent.EXTRA_TITLE, editTitle.getText().toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;

        }

        if (item.getItemId() == R.id.startNotify) {
            String vacName = editTitle.getText().toString();
            String date = editStartDate.getText().toString();

            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date vacaStartDate = null;

            try {
                vacaStartDate = sdf.parse(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                assert vacaStartDate != null;
                Long trig = vacaStartDate.getTime();
                Intent intent = new Intent(VacationDetail.this, VacationBCReceiver.class);
                intent.putExtra("vackey", vacName + " starts today " + date + "!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(VacationDetail.this, ++MainActivity.excAlert,
                        intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trig, pendingIntent);
            }catch (Exception e){
                e.printStackTrace();
            }

            return true;
        }
        if (item.getItemId() == R.id.endNotify) {
            String vacName = editTitle.getText().toString();
            String date = editEndDate.getText().toString();

            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date vacaEndDate = null;

            try {
                vacaEndDate = sdf.parse(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert vacaEndDate != null;
            Long trig = vacaEndDate.getTime();
            Intent intent =new Intent(VacationDetail.this,VacationBCReceiver.class);
            intent.putExtra("vackey","Sorry! Your vacation to "+vacName+" ends today "+date+".");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(VacationDetail.this,++MainActivity.excAlert,
                    intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP,trig,pendingIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> assocExcursion = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationID() == vacationID) assocExcursion.add(e);
        }
        excursionAdapter.setExcursions(assocExcursion);

    }

   private void validateDates() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        try {
            Date start = sdf.parse(startVacationDate);
            Date end = sdf.parse(endVacationDate);
            assert end != null;
            if(end.before(start)){
                Toast.makeText(VacationDetail.this,"Start date must be before end date!",
                        Toast.LENGTH_LONG).show();
                this.finish();

            }
        }catch (ParseException e){
            e.printStackTrace();
        }

    }



}
