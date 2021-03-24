package database;

import androidx.annotation.NonNull;

public class Score {
    private int id_score;
    private String email_user;
    private float value_score;

    public Score(String _email_user, float _score){
        this.setPlayer(_email_user);
        this.setScore(_score);
    }

    public float getScore() {
        return value_score;
    }

    public void setScore(float score) {
        if(score < 0){
            throw new IllegalArgumentException();
        }else{
            this.value_score = score;
        }
    }

    public String getPlayer() {
        return email_user;
    }

    public void setPlayer(String _email_user) {
        this.email_user = _email_user;
    }

    public int getIdScore() {
        return id_score;
    }

    public void setIdScore(int _id_score) {
        this.id_score = _id_score;
    }

    @NonNull
    @Override
    public String toString() {
        return id_score + " : " + email_user + " -> " +value_score + " Points.";
    }
}
