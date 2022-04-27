package Grazer_Bluemchen;

import java.util.ArrayList;
import java.util.Collections;

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
    public void shuffle() {
        Collections.shuffle(deck);
    }

    // austeilen method
    public ArrayList<Card> dealCards() { // parameter wieviele karten
        ArrayList<Card> handCards = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            handCards.add(deck.get(i));
            deck.remove(i);
        }
        return handCards;
    }

    // method: print all card names
    public void printAllCards() {
        for (Card c : deck) {
            System.out.print(c.name + " ");
        }
        System.out.println();
    }

    public int countCards() {
        return deck.size();
    }

    @Override
    public String toString() {
        return "CardDeck{" +
                "deck=" + deck +
                '}';
    }
}
