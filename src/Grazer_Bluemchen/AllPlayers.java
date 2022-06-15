package Grazer_Bluemchen;

import java.util.ArrayList;
import java.util.Collections;

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

    public int nextPlayer(boolean direction, int currentPlayerNumber) {

        if(direction) { // if true
            if (currentPlayerNumber < 4) {
                currentPlayerNumber++;
            } else {
                currentPlayerNumber = 1;
            }
        } else {
            if (currentPlayerNumber > 1) {
                currentPlayerNumber--;
            } else {
                currentPlayerNumber = 4;
            }
        }

        return currentPlayerNumber;
    }

    public int prevPlayer(boolean direction, int currentPlayerNumber) {
        if(direction) { // if true
            if (currentPlayerNumber > 1) {
                currentPlayerNumber--;
            } else {
                currentPlayerNumber = 1;
            }
        } else {
            if (currentPlayerNumber < 4) {
                currentPlayerNumber++;
            } else {
                currentPlayerNumber = 4;
            }
        }

        return currentPlayerNumber;
    }

    @Override
    public String toString() {
        return "AllPlayers{" +
                "allPlayer=" + allPlayer +
                '}';
    }
}
