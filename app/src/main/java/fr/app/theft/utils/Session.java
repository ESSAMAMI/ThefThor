package fr.app.theft.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

import fr.app.theft.entities.Account;

public class Session {

    private static Session session = null ;
    private final Account account ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Session(Account account){
        super() ;
        this.account = account ;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean sessionStart(Account account){
        if( account != null ){
            Session.session = new Session( account ) ;
            return true ;
        }
        else {
            return false ;
        }
    }

    public static Session getSession(){
        return Session.session ;
    }
    public static void setSession(Session session){
        Session.session = session ;
    }

    public static void sessionClose(){
        Session.session = null ;
    }

    public Account getAccount(){
        return this.account ;
    }


}