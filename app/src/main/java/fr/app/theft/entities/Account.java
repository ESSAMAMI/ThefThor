package fr.app.theft.entities;

import java.time.LocalDate;

public class Account {

    /**
     * id_account | name | login | pwd | last_connection
     */
    private int idAccount;
    private String name;
    private String login;
    private String pwd;
    private LocalDate lastConnection;

    public Account(int idAccount, String name, String login, String pwd, LocalDate lastConnection) {
        this.idAccount = idAccount;
        this.name = name;
        this.login = login;
        this.pwd = pwd;
        this.lastConnection = lastConnection;
    }


    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public LocalDate getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(LocalDate lastConnection) {
        this.lastConnection = lastConnection;
    }

    @Override
    public String toString() {
        return "Account{" +
                "idAccount=" + idAccount +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", pwd='" + pwd + '\'' +
                ", lastConnection=" + lastConnection +
                '}';
    }
}
