package database;


import java.util.Date;

public class UserData {
    private int idUser;
    private String name;
    private String password;
    private Date Birth;


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
        if(name != "" && name.length() > 3){
            this.name = name;
        }else {
            throw new IllegalArgumentException();
        }

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password != ""){
            this.password = password;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public Date getBirth() {
        return Birth;
    }

    public void setBirth(Date birth) {
        Birth = birth;
    }
}
