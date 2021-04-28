package fr.app.theft.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.app.theft.R;
import fr.app.theft.adapter.NotificationAdapter;
import fr.app.theft.entities.Account;
import fr.app.theft.entities.Notification;
import fr.app.theft.utils.Session;

public class BoardActivity extends AppCompatActivity {

    ArrayList<Notification> notifications = new ArrayList<>();
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    CircleImageView circleImageView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_activity);
        RelativeLayout layoutSynthese = findViewById(R.id.layout_synthese);
        circleImageView = findViewById(R.id.avatar);
        layoutSynthese.setVisibility(View.VISIBLE);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatar();
            }
        });
        String userID = String.valueOf(Session.getSession().getAccount().getIdAccount());
        String url = String.format(getResources().getString(R.string.URL_ALERTS), userID);

        RequestQueue queue = Volley.newRequestQueue(BoardActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() > 0){
                    try {
                        for(int i = 0; i < response.length(); i++){
                            JSONObject alert = response.getJSONObject(i);
                            String[] date = alert.getString("date_submit").split("-");
                            LocalDate dateSubmit = LocalDate.of(
                                    Integer.parseInt(date[0]),
                                    Integer.parseInt(date[1]),
                                    Integer.parseInt(date[2])
                            );
                            Notification nt = new Notification(
                                    alert.getInt("_id"),
                                    alert.getString("tag"),
                                    alert.getString("message"),
                                    dateSubmit);

                            //Toast.makeText(BoardActivity.this, nt.toString(), Toast.LENGTH_LONG).show();
                            notifications.add(nt);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    Toast.makeText(BoardActivity.this, "Une erreur s'est produite...", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BoardActivity.this, "Une erreur s'est produiteeeeeee...", Toast.LENGTH_LONG).show();

            }

        });
        queue.add(jsonArrayRequest);
        new BackgroundTask().execute();
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
                        TextView userName = findViewById(R.id.user_name);
                        TextView userSurName = findViewById(R.id.user_suername);
                        TextView userEmail = findViewById(R.id.user_email);
                        TextView userPwd = findViewById(R.id.user_pwd);

                        String[] userData = Session.getSession().getAccount().getName().split(" ");
                        userName.setText(userData[0]);
                        userSurName.setText(userData[1]);
                        userEmail.setText(Session.getSession().getAccount().getLogin());
                        userPwd.setText(Session.getSession().getAccount().getPwd());

                        layoutNotification.setVisibility(View.INVISIBLE);
                        layoutProfil.setVisibility(View.VISIBLE);
                        layoutSynthese.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.notification:

                        recyclerView = findViewById(R.id.recycler_view_notifications);
                        recyclerView.setLayoutManager(new LinearLayoutManager(BoardActivity.this));
                        Collections.sort(notifications);
                        adapter = new NotificationAdapter(BoardActivity.this, notifications);
                        recyclerView.setAdapter(adapter);

                        layoutNotification.setVisibility(View.VISIBLE);
                        layoutProfil.setVisibility(View.INVISIBLE);
                        layoutSynthese.setVisibility(View.INVISIBLE);
                        findViewById(R.id.notification_bulle).setVisibility(View.INVISIBLE);
                        break;
                }
                return true;
            }

        });
    }

    public void setBarChart(){

        BarChart chart = findViewById(R.id.barchart);

        ArrayList alerts = new ArrayList();

        for(int i = 0; i < 8; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 50);
            alerts.add(new BarEntry(randomNum, i));
        }


        ArrayList days = new ArrayList();
        for(int i = 0; i < 8; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 50);
            days.add(String.valueOf(i+1)+"/04");
        }

        BarDataSet bardataset = new BarDataSet(alerts, "Alerte(s) par jour");
        chart.animateY(5000);
        BarData data = new BarData(days, bardataset);
        int[] colors = {ColorTemplate.rgb("F3AD4C")};
        bardataset.setColors(colors);
        chart.setData(data);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setData(){

        TextView trial = findViewById(R.id.countdown_trial);
        TextView titleTv = findViewById(R.id.titleTv);
        TextView connectionCounter = findViewById(R.id.notification_counter);

        TextView lastConnection = findViewById(R.id.last_connection);
        lastConnection.setText(Session.getSession().getAccount().getLastConnection().toString());
        LocalDate today = LocalDate.now();
        String duration = String.valueOf(Duration.between(today.atStartOfDay(), Session.getSession().getAccount().getTrial().atStartOfDay()).toDays());
        trial.setText(duration+" Jour(s)");
        titleTv.setText(Session.getSession().getAccount().getName().split(" ")[1]);
        connectionCounter.setText(String.valueOf(notifications.size()));
        String ava = "id"+Session.getSession().getAccount().getIdAccount();
        int id = getResources().getIdentifier("fr.app.theft:drawable/" + ava, null, null);
        CircleImageView circleImageView = findViewById(R.id.avatar);
        circleImageView.setImageResource(id);

    }

    public void avatar(){
        Toast.makeText(BoardActivity.this, "Change avatar...", Toast.LENGTH_LONG).show();
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setData();
            bottomNavigationViewConfiguration();
            setBarChart();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) { }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onPostExecute(Void result) {
            setData();
            bottomNavigationViewConfiguration();
            setBarChart();
        }
    }

}
