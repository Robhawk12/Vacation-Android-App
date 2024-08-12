package d308.robertjohnson.vacationplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.strictmode.FragmentStrictMode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import d308.robertjohnson.vacationplanner.R;

public class VacationsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacations_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(VacationsList.this,VacationView.class);
                startActivity(intent);
            }
        });
        //System.out.println(getIntent().getStringExtra("test"));
    }
}