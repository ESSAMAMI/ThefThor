package fr.app.theft.utils;

import fr.app.theft.entities.Account;

public class Session {

    private static Session session = null ;
    private Account account ;

    private Session(Account account){
        super() ;
        this.account = account ;
    }

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

    public static void sessionClose(){
        Session.session = null ;
    }

    public Account getAccount(){
        return this.account ;
    }
}