package Grazer_Bluemchen;

import java.util.ArrayList;

public class CardDeck {
    ArrayList<Card> deck;

    public CardDeck() {
        deck = new ArrayList<>();
    }

    public void addCard(Card c) {
        deck.add(c);
    }

    // drawpile
    // discardpile

    // shuffle method

    // austeilen method

    // method: print all card names
    public void printAllCards() {
        for (Card c : deck) {
            System.out.print(c.name + " ");
        }
    }

    @Override
    public String toString() {
        return "CardDeck{" +
                "deck=" + deck +
                '}';
    }
}
