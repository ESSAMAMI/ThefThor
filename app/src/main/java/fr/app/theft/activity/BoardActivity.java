package fr.app.theft.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.app.theft.MainActivity;
import fr.app.theft.R;
import fr.app.theft.adapter.NotificationAdapter;
import fr.app.theft.app.App;
import fr.app.theft.entities.Account;
import fr.app.theft.entities.Notification;
import fr.app.theft.fragment.NotificationDescriebFragment;
import fr.app.theft.utils.ArrayManipulator;
import fr.app.theft.utils.Session;

public class BoardActivity extends AppCompatActivity {

    ArrayList<Notification> notifications;
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    CircleImageView circleImageView;
    CardView startStopCamera;
    private NotificationManagerCompat notificationManager;
    private int N_SIZE;

    private SharedPreferences sharedPreferences;
    private static final String NOTIFICATIONS = "Notifications_push";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_activity);

        RelativeLayout layoutSynthese = findViewById(R.id.layout_synthese);
        circleImageView = findViewById(R.id.avatar);
        layoutSynthese.setVisibility(View.VISIBLE);
        startStopCamera = (CardView) findViewById(R.id.start_stop_camera);
        recyclerView = findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(BoardActivity.this));

        TextView cameraStatus = (TextView) findViewById(R.id.cameras_stauts);

        getAlerts();
        scheduledTasks();
        // Notification manager
        notificationManager = NotificationManagerCompat.from(this);
        startStopCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendNotificationChannel();
                String userId = String.valueOf(Session.getSession().getAccount().getIdAccount());
                String url = String.format(getResources().getString(R.string.URL_RUN_STOP_DEVICES), userId);

                RequestQueue queue = Volley.newRequestQueue(BoardActivity.this);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            boolean status = jsonObject.getBoolean("system_on");
                            if(status){
                                //Toast.makeText(BoardActivity.this, "Start", Toast.LENGTH_LONG).show();
                                cameraStatus.setText("Allumée");
                            }else{
                                //Toast.makeText(BoardActivity.this, "STOP", Toast.LENGTH_LONG).show();
                                cameraStatus.setText("Arrêtée");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BoardActivity.this, "Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    }
                }
                );
                queue.add(jsonArrayRequest);
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatar();
            }
        });

        new BackgroundTask().execute();
    }
    

    public void getAlerts(){

        notifications = new ArrayList<>();
        String userID = String.valueOf(Session.getSession().getAccount().getIdAccount());
        String url = String.format(getResources().getString(R.string.URL_ALERTS), userID);

        RequestQueue queue = Volley.newRequestQueue(BoardActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
        //Toast.makeText(BoardActivity.this, String.valueOf(notifications.size()), Toast.LENGTH_LONG).show();

    }
    public void bottomNavigationViewConfiguration(){

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                RelativeLayout layoutSynthese = findViewById(R.id.layout_synthese);
                RelativeLayout layoutProfil = findViewById(R.id.layout_profil);
                RelativeLayout layoutNotification = findViewById(R.id.layout_notification);
                switch (item.getItemId()){
                    case R.id.synthese:
                        setBarChart();
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
                        //userPwd.setText(Session.getSession().getAccount().getPwd());

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
                        //findViewById(R.id.notification_bulle).setVisibility(View.INVISIBLE);
                        break;
                }
                return true;
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBarChart(){

        BarChart chart = findViewById(R.id.barchart);
        LocalDate today = LocalDate.now();
        String[] currentDate = today.toString().split("-");

        int dayOfMonth = today.getDayOfMonth();
        String month = String.valueOf(today.getMonth().getValue());
        ArrayList<BarEntry> alerts = new ArrayList();
        ArrayList<String> days = new ArrayList();
        HashMap<String, Integer> dateOccurence = new HashMap<String, Integer>();
        for(int i = 0; i < 8; i++){
            // TODO:
            LocalDate dateToSearch = LocalDate.of(
                        Integer.parseInt(currentDate[0]),
                        Integer.parseInt(currentDate[1]),
                        Integer.parseInt(currentDate[2])-i
                    );
            int frequency = 0;
            dateOccurence.put(dateToSearch.toString(), frequency);
            for(Notification notif: notifications){
                if(notif.getDate().toString().equals(dateToSearch.toString())){
                    frequency++;
                    dateOccurence.put(dateToSearch.toString(), frequency);
                }
            }
        }

        int position = 0;
        for (Map.Entry<String, Integer> occurence : dateOccurence.entrySet()) {
            String date = occurence.getKey();
            int frequency = occurence.getValue();
            alerts.add(new BarEntry(frequency, position));
            days.add(date);
            position++;
        }

        BarDataSet bardataset = new BarDataSet(alerts, "Alerte(s) / jour (7 jours)");
        chart.animateY(5000);
        BarData data = new BarData(days, bardataset);
        int[] colors = {ColorTemplate.rgb("F3AD4C")};
        bardataset.setColors(colors);
        chart.setData(data);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setData(){

        N_SIZE = notifications.size();

        Collections.sort(notifications);
        adapter = new NotificationAdapter(BoardActivity.this, notifications);
        recyclerView.setAdapter(adapter);

        TextView trial = findViewById(R.id.countdown_trial);
        TextView titleTv = findViewById(R.id.titleTv);
        TextView connectionCounter = findViewById(R.id.notification_counter);
        connectionCounter.setText(String.valueOf(N_SIZE));
        TextView lastConnection = findViewById(R.id.last_connection);
        lastConnection.setText(Session.getSession().getAccount().getLastConnection().toString());
        LocalDate today = LocalDate.now();
        String duration = String.valueOf(Duration.between(today.atStartOfDay(), Session.getSession().getAccount().getTrial().atStartOfDay()).toDays());
        trial.setText(duration+" Jour(s)");
        titleTv.setText(Session.getSession().getAccount().getName().split(" ")[1]);

        String ava = "id"+Session.getSession().getAccount().getIdAccount();
        int id = getResources().getIdentifier("fr.app.theft:drawable/" + ava, null, null);
        CircleImageView circleImageView = findViewById(R.id.avatar);
        circleImageView.setImageResource(id);

        sharedPreferences = getSharedPreferences(NOTIFICATIONS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("PUSH_SIZE", notifications.size());
        editor.apply();
    }

    public void scheduledTasks(){

        final Handler handler = new Handler();
        final int delay = 4000; // 4000 milliseconds == 4 second

        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                getAlerts();
                handler.postDelayed(this, delay);
            }
        }, delay);

        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                if(notifications.size() > 0){
                    N_SIZE = notifications.size();
                    int PUSH_SIZE = sharedPreferences.getInt("PUSH_SIZE", 0);
                    //Toast.makeText(BoardActivity.this, String.valueOf(PUSH_SIZE), Toast.LENGTH_SHORT).show();
                    if(PUSH_SIZE < notifications.size()){
                        setData();
                        Notification n = ArrayManipulator.foundArray(notifications);
                        sendNotificationChannel(
                                n.getTag(),
                                n.getMessage(),
                                n.getId()
                        );
                    }else{
                        setData();
                    }

                }

                handler.postDelayed(this, 5000);
            }
        }, delay);
    }
    public void avatar(){
        //Toast.makeText(BoardActivity.this, "Change avatar...", Toast.LENGTH_LONG).show();
        NotificationDescriebFragment notificationDescriebFragment = new NotificationDescriebFragment("SE DECONNECTER");

    }

    public void sendNotificationChannel(String title, String message, int id){
        android.app.Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(id, notification);
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            N_SIZE = notifications.size();
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

            N_SIZE = notifications.size();
        }

    }

}
