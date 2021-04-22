package fr.app.theft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fr.app.theft.R;
import fr.app.theft.utils.Session;

public class BoardActivity extends AppCompatActivity {

    //private BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_activity);
        RelativeLayout layoutSynthese = findViewById(R.id.layout_synthese);
        layoutSynthese.setVisibility(View.VISIBLE);

        this.bottomNavigationViewConfiguration();
        this.setBarChart();
        //Toast.makeText(BoardActivity.this, Session.getSession().getAccount().toString(), Toast.LENGTH_LONG).show();
    }

    public void bottomNavigationViewConfiguration(){

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                RelativeLayout layoutSynthese = findViewById(R.id.layout_synthese);
                RelativeLayout layoutProfil = findViewById(R.id.layout_profil);
                switch (item.getItemId()){
                    case R.id.synthese:
                        layoutSynthese.setVisibility(View.VISIBLE);
                        layoutProfil.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.profil:
                        layoutProfil.setVisibility(View.VISIBLE);
                        layoutSynthese.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.notification:
                        Toast.makeText(BoardActivity.this, "Notification", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.parametre:
                        Toast.makeText(BoardActivity.this, "Paramètre", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

        });
    }

    public void setBarChart(){

        BarChart chart = findViewById(R.id.barchart);

        ArrayList alerts = new ArrayList();

        alerts.add(new BarEntry(10, 0));
        alerts.add(new BarEntry(3, 1));
        alerts.add(new BarEntry(50, 2));
        alerts.add(new BarEntry(10, 3));
        alerts.add(new BarEntry(2, 4));
        alerts.add(new BarEntry(6, 5));
        alerts.add(new BarEntry(1, 6));

        ArrayList days = new ArrayList();

        days.add("15");
        days.add("16");
        days.add("17");
        days.add("18");
        days.add("19");
        days.add("20");
        days.add("21");

        BarDataSet bardataset = new BarDataSet(alerts, "Alert par jour");
        chart.animateY(5000);
        BarData data = new BarData(days, bardataset);
        int[] colors = {ColorTemplate.rgb("F3AD4C")};
        bardataset.setColors(colors);
        chart.setData(data);

    }

}
