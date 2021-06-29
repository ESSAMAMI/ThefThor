package fr.app.theft.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import fr.app.theft.R;

public class NotificationDescriebFragment extends DialogFragment {
    LayoutInflater layoutInflater;
    View view;

    private String title;

    /*public NotificationDescriebFragment(){

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.notification_descreib_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.create();
    }*/

    public NotificationDescriebFragment(String title) {
        // Empty constructor required for DialogFragment
        this.title = title;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_descreib_layout, container, false);
        TextView title = (TextView) view.findViewById(R.id.title_notification);
        title.setText(this.title);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return view;
    }
    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Hello");
        alertDialogBuilder.setMessage("Are you sure?");
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });

        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.notification_descreib_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }*/
}
