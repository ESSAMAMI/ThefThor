package fr.app.theft.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import fr.app.theft.R;
import fr.app.theft.entities.Notification;
import fr.app.theft.fragment.NotificationDescriebFragment;
import fr.app.theft.utils.Session;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_notifiaction, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.tag.setText(notifications.get(position).getTag());
        holder.message.setText(notifications.get(position).getMessage());
        LocalDate today = LocalDate.now();
        String duration = String.valueOf(Duration.between(notifications.get(position).getDate().atStartOfDay(), today.atStartOfDay()).toDays());
        int durationInt = Integer.parseInt(duration);
        boolean aYearAgo = false;
        if(durationInt > 365){
            durationInt = (int) durationInt / 365;
            aYearAgo = true;
        }
        if(aYearAgo){
            if(durationInt > 1){
                duration = String.valueOf(durationInt)+" ans";
            }else{
                duration = String.valueOf(durationInt)+" an";
            }
        }else{
            if(duration.equals("0")){
                duration = "Auj";
            }else{
                duration = duration+" j";
            }
        }

        holder.date.setText(duration);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                NotificationDescriebFragment notificationDescriebFragment = new NotificationDescriebFragment("More info");
                notificationDescriebFragment.show(fragmentManager, "Voir plus");*/
                String url = String.format(
                            context.getResources().getString(R.string.MORE_INFO),
                            String.valueOf(notifications.get(position).getId()),
                            Session.getSession().getAccount().getPwd());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tag;
        TextView message;
        CardView cardView;
        TextView date;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            tag  = itemView.findViewById(R.id.titre);
            message = itemView.findViewById(R.id.message);
            cardView = itemView.findViewById(R.id.card_notification);
            date = itemView.findViewById(R.id.date_recieve);
        }
    }

}
