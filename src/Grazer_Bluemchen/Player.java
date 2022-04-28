package Grazer_Bluemchen;

import java.util.ArrayList;

public class Player {
    public String name;
    public int order; // 1-4 - equals number of player
    public ArrayList<Card> handCards;

    public Player() {
        order++;
        handCards = new ArrayList<>();
    }

    public void addCard(Card c) {
        handCards.add(c);
    }

    public void addHandCards(ArrayList<Card> a) {
        handCards.addAll(a);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", order=" + order +
                ", handCards=" + handCards +
                '}';
    }

}
