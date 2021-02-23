package database;


import androidx.annotation.NonNull;

import java.util.Date;

public class User {
    private int idUser;
    private String name;
    private String password;
    private String birth;


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!name.equals("") && name.length() > 3){
            this.name = name;
        }else {
            throw new IllegalArgumentException();
        }

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!password.equals("")){
            this.password = password;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        //TODO : Formate Date in String
        this.birth = birth;
    }

    @NonNull
    @Override
    public String toString() {
        return idUser + " -> " + "Name : " + name + " / Birth : " + birth;
    }
}
