package database;

import androidx.annotation.NonNull;

public class ScoreData {
    private int idScore;
    private String player;
    private int score;

    public ScoreData(int _idScore, String _player, int _score){
        this.setIdScore(_idScore);
        this.setPlayer(_player);
        this.setScore(_score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    @NonNull
    @Override
    public String toString() {
        return idScore + " : " + player + " -> " + score + " Points.";
    }
}
