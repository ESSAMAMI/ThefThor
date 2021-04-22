package fr.app.theft.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;

import fr.app.theft.R;
import fr.app.theft.entities.Account;
import fr.app.theft.utils.Session;

public class ConnectionActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    //private static final String URL_CONNETION = ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.connection_activity);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void signIn(final View v) throws UnsupportedEncodingException {

        EditText login = (EditText) findViewById(R.id.identifiant);
        EditText pwd = (EditText) findViewById(R.id.pwd);

        String credentials = String.valueOf(login.getText()).toLowerCase() +"."+String.valueOf(pwd.getText()).toLowerCase();
        String url = String.format(getResources().getString(R.string.URL_CONNECTION), credentials);

        /*RequestQueue queue = Volley.newRequestQueue(ConnectionActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if(response.length() == 1){
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);
                            String[] lastConnection = jsonObject.getString("last_connection").split("-");
                            LocalDate localDate = LocalDate.of(
                                    Integer.parseInt(lastConnection[0]),
                                    Integer.parseInt(lastConnection[1]),
                                    Integer.parseInt(lastConnection[2])
                            );
                            Account account = new Account(
                                    jsonObject.getInt("_id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("login"),
                                    jsonObject.getString("pwd"),
                                    localDate
                            );
                            Session.sessionStart(account);
                            System.out.println(jsonObject);
                            final Intent intent = new Intent(ConnectionActivity.this, BoardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            //Toast.makeText(ConnectionActivity.this, "Co ok...", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{

                        alert("Le login et ou le mot de passe est incorrect");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(ConnectionActivity.this, "Une erreur s'est produite...", Toast.LENGTH_LONG).show();
                    alert("Une erreur s'est produite...");
                }

        });
        queue.add(jsonArrayRequest);*/

        final Intent intent = new Intent(ConnectionActivity.this, BoardActivity.class);
        startActivity(intent);
    }

    public void alert (String text){
        TextView alert = (TextView) findViewById(R.id.alert);
        alert.setText(text);
        alert.setVisibility(View.VISIBLE);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(4000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(3000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        alert.setAnimation(animation);
        alert.setVisibility(View.INVISIBLE);
    }
}
