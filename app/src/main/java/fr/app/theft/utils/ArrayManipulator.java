package fr.app.theft.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

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


    public static TreeMap<String, Integer> sortHashMapByValues(TreeMap<String, Integer> passedMap) {

        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        //Collections.reverseOrder()
        TreeMap<String, Integer> sortedHashMap = new TreeMap<>();

        Collections.sort(mapKeys, (object1, object2) -> object1.compareTo(object2));
        System.out.println("Sorted date =>>> "+mapKeys.toString());

        for(String key: mapKeys){
            sortedHashMap.put(key, passedMap.get(key));
        }

        return sortedHashMap;
        /*Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Integer> sortedMap =
                new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            int val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                int comp1 = passedMap.get(key);
                int comp2 = val;

                if (comp1 == comp2) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;*/
    }


}
