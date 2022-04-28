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

    // drawpile = CardDeck
    // discardpile

    // shuffle method
    public void shuffle() {
        Collections.shuffle(deck);
    }

    // austeilen method
    public ArrayList<Card> dealCards(int quantity) {
        ArrayList<Card> handCards = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
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

    public CardDeck createDrawPile() {
        // Deck erstellen:
        CardDeck deck = new CardDeck();

        // 1-9er cards and actioncards
        for (int i = 0; i < 2; i++) {
            for (Colors co : Colors.values()) {
                for (int j = 1; j <= 9; j++) {
                    Card c = new Card(co, j);
                    deck.addCard(c);
                }
                Card c1 = new Card(co, "+2");
                Card c2 = new Card(co, "Reverse");
                Card c3 = new Card(co, "Skip");
                deck.addCard(c1);
                deck.addCard(c2);
                deck.addCard(c3);
            }
        }

        // 0er cards
        for (Colors co : Colors.values()) {
            Card o = new Card(co, 0);
            deck.addCard(o);
        }

        // special cards
        for (int i = 0; i < 4; i++) {
            Card s1 = new Card("+4");
            Card s2 = new Card("ColorChange");
            deck.addCard(s1);
            deck.addCard(s2);
        }
        return deck;
    }

    public CardDeck createDiscardPile() {
        return new CardDeck();
    }

    @Override
    public String toString() {
        return "CardDeck{" +
                "deck=" + deck +
                '}';
    }
}
