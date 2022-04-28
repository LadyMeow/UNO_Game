package Grazer_Bluemchen;

import java.util.ArrayList;

public class AllPlayers {
    ArrayList<Player> allPlayer;

    public AllPlayers() {
        allPlayer = new ArrayList<>();
    }

    public void addPlayer(Player p) {
        allPlayer.add(p);
    }

    @Override
    public String toString() {
        return "AllPlayers{" +
                "allPlayer=" + allPlayer +
                '}';
    }
}
