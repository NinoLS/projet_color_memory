package database;


import androidx.annotation.NonNull;

public class User {
    private int idUser;
    private String name;
    private String password;
    private String birth;
    private String email;
    private byte gender;

    public User(String _name, String _password, String _birth, String _email, byte _gender) {
        this.setName(_name);
        this.setPassword(_password);
        this.setBirth(_birth);
        this.setEmail(_email);
        this.setGender(_gender);
    }


    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() { return name; }
    public void setName(String name) {
        if(!name.equals("") && name.length() > 3){
            this.name = name;
        }else {
            throw new IllegalArgumentException();
        }

    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        if(!password.equals("")){
            this.password = password;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public String getBirth() { return birth; }
    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail(){return  email;}
    public void setEmail(String email){
        this.email = email;
    }

    public byte getGender(){return gender;}
    public void setGender(byte gender){
        if((gender == 0) || (gender == 1)){
            gender = gender;
        }else{
            throw new IllegalArgumentException();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return idUser + " -> " + "Name : " + name + " / Birth : " + birth + " / Gender : ";
    }
}
