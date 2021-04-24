package fr.app.theft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fr.app.theft.R;
import fr.app.theft.adapter.NotificationAdapter;
import fr.app.theft.entities.Notification;
import fr.app.theft.utils.Session;

public class BoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotificationAdapter adapter;
    String [] test = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis aliquet, nunc sed viverra molestie, est eros venenatis nisl, id ullamcorper tortor nibh ac risus.","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis aliquet, nunc sed viverra molestie, est eros venenatis nisl, id ullamcorper tortor nibh ac risus.","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis aliquet, nunc sed viverra molestie, est eros venenatis nisl, id ullamcorper tortor nibh ac risus.","F","G","G","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis aliquet, nunc sed viverra molestie, est eros venenatis nisl, id ullamcorper tortor nibh ac risus."};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_activity);
        RelativeLayout layoutSynthese = findViewById(R.id.layout_synthese);
        layoutSynthese.setVisibility(View.VISIBLE);

        this.bottomNavigationViewConfiguration();
        this.setBarChart();

        recyclerView = findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(this, test);
        recyclerView.setAdapter(adapter);
        //Toast.makeText(BoardActivity.this, Session.getSession().getAccount().toString(), Toast.LENGTH_LONG).show();
    }

    public void bottomNavigationViewConfiguration(){

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                RelativeLayout layoutSynthese = findViewById(R.id.layout_synthese);
                RelativeLayout layoutProfil = findViewById(R.id.layout_profil);
                RelativeLayout layoutNotification = findViewById(R.id.layout_notification);
                switch (item.getItemId()){
                    case R.id.synthese:
                        layoutNotification.setVisibility(View.INVISIBLE);
                        layoutSynthese.setVisibility(View.VISIBLE);
                        layoutProfil.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.profil:
                        layoutNotification.setVisibility(View.INVISIBLE);
                        layoutProfil.setVisibility(View.VISIBLE);
                        layoutSynthese.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.notification:
                        layoutNotification.setVisibility(View.VISIBLE);
                        layoutProfil.setVisibility(View.INVISIBLE);
                        layoutSynthese.setVisibility(View.INVISIBLE);
                        //Toast.makeText(BoardActivity.this, "Notification", Toast.LENGTH_SHORT).show();

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

        BarDataSet bardataset = new BarDataSet(alerts, "Alerte par jour");
        chart.animateY(5000);
        BarData data = new BarData(days, bardataset);
        int[] colors = {ColorTemplate.rgb("F3AD4C")};
        bardataset.setColors(colors);
        chart.setData(data);

    }

}
