package fr.app.theft.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import fr.app.theft.R;
import fr.app.theft.entities.Notification;
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
        String duration = String.valueOf(Duration.between(notifications.get(position).getDate().atStartOfDay(), today.atStartOfDay()).toDays()) + " j";
        if(duration.equals("0 j")){
            duration = "Auj";
        }
        holder.date.setText(duration);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Images s'il y a...", Toast.LENGTH_LONG).show();
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
