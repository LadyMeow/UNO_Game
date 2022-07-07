package Grazer_Bluemchen.Players;

import java.util.ArrayList;

public class AllPlayers {
    public ArrayList<Player> playerList;

    public AllPlayers() {
        playerList = new ArrayList<>();
    }

    public void addPlayer(Player p) {
        playerList.add(p);
    }

    public Player getPlayer(int index) {
        return playerList.get(index);
    }

    public int countAllPlayerCards() {
        int sum = 0;
        for (Player p : playerList) {
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

    @Override
    public String toString() {
        return "AllPlayers{" +
                "allPlayer=" + playerList +
                '}';
    }
}
