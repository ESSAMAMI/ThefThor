package fr.app.theft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import fr.app.theft.activity.BoardActivity;
import fr.app.theft.activity.ConnectionActivity;
import fr.app.theft.utils.DateFormatter;
import fr.app.theft.utils.Session;

public class MainActivity extends AppCompatActivity {


    private Handler handler = new Handler();
    private  SharedPreferences sharedPreferences;
    private static final String SESSION = "USER_SESSION";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luncher_activity);
        //sharedPreferences = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        sharedPreferences = getApplicationContext().getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        if(sharedPreferences.contains("Session")){
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    ProgressBar spinner = (ProgressBar) findViewById(R.id.spinner_start);
                    spinner.setVisibility(View.VISIBLE);
                }
            }, 1000);
            Gson gson = new Gson();
            String session = sharedPreferences.getString("Session", "m");
            Session.setSession(gson.fromJson(session, Session.class));
            Session.getSession().getAccount().setLastConnection(DateFormatter.stringToLocalDate(sharedPreferences.getString("Last_Connection", ""), "-"));
            Session.getSession().getAccount().setTrial(DateFormatter.stringToLocalDate(sharedPreferences.getString("Trial_Date", ""), "-"));
            // Redirect to Board Activity...
            final Intent intent = new Intent(this, BoardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 2000);

        }else{
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    ProgressBar spinner = (ProgressBar) findViewById(R.id.spinner_start);
                    spinner.setVisibility(View.VISIBLE);
                }
            }, 1000);

            final Intent intent = new Intent(this, ConnectionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 2000);
        }


    }

}
