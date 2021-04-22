package fr.app.theft;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import fr.app.theft.activity.BoardActivity;
import fr.app.theft.activity.ConnectionActivity;

public class MainActivity extends AppCompatActivity {


    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luncher_activity);

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
