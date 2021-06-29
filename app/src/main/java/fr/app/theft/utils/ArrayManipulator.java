package fr.app.theft.utils;

import java.util.ArrayList;

import fr.app.theft.entities.Notification;

public class ArrayManipulator {

    public static Notification foundArray(ArrayList<Notification> notifications){
        Notification notification = notifications.get(0);
        for(Notification n: notifications){
            if(notification.getId() < n.getId()){
                notification = n;
            }
        }
        return notification;
    }
}
