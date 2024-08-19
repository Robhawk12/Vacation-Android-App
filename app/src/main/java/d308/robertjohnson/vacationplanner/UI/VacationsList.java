package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import d308.robertjohnson.vacationplanner.R;
import d308.robertjohnson.vacationplanner.data.Repository;
import d308.robertjohnson.vacationplanner.entities.Vacation;

public class VacationsList extends AppCompatActivity {
private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacations_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(VacationsList.this, VacationDetail.class);
                startActivity(intent);
            }

        });
        //System.out.println(getIntent().getStringExtra("test"));
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.vacation) {
            Repository repository=new Repository(getApplication());

            //insert samples
            Vacation vacation=new Vacation(0,"Hawaii","Beach Surf","01/02/2025","01/10/2025");
            repository.insert(vacation);

            vacation = new Vacation(1, "Jamaica", "Rum Runner Hotel", "09/02/2025", "09/10/2025");
            repository.insert(vacation);

            List<Vacation> allVacations=repository.getmAllVacations();



            return true;
        }

        return true;
    }
}