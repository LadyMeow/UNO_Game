package Grazer_Bluemchen;

import java.util.ArrayList;

public abstract class Player {
    public String name;
    //public int order; // 1-4 - equals number of player
    public ArrayList<Card> handCards;

    public Player() {
        handCards = new ArrayList<>();
    }

    public void addCard(Card c) {
        handCards.add(c);
    }

    public void addHandCards(ArrayList<Card> a) {
        handCards.addAll(a);
    }

    public void printHandCards() {
        for (Card c : handCards) {
            System.out.print(c.name + " ");
        }
        System.out.println();
    }

    // play cards
    public abstract Card playCard(Card c);

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", handCards=" + handCards +
                '}';
    }
}
