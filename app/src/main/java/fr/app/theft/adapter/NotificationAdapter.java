package fr.app.theft.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.util.ArrayList;

import fr.app.theft.R;
import fr.app.theft.entities.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    //private ArrayList<Notification> notifications;
    private String[] data;

    public NotificationAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_notifiaction, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @NonNull
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.message.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
        //return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titre;
        TextView message;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            titre  = itemView.findViewById(R.id.titre);
            message = itemView.findViewById(R.id.message);
        }
    }
}
