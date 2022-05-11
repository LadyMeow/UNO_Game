package Grazer_Bluemchen;

import java.util.ArrayList;

public class AllPlayers {
    public ArrayList<Player> allPlayer;

    public AllPlayers() {
        allPlayer = new ArrayList<>();
    }

    public void addPlayer(Player p) {
        allPlayer.add(p);
    }

    public Player getPlayer(int index) {
        return allPlayer.get(index);
    }

    public int countAllPlayerCards() {
        int sum = 0;
        for (Player p : allPlayer) {
            sum += p.handCards.size();
        }
       return sum;
    }

    @Override
    public String toString() {
        return "AllPlayers{" +
                "allPlayer=" + allPlayer +
                '}';
    }
}
