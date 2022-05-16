package Grazer_Bluemchen;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    public ArrayList<Card> drawpile;
    public ArrayList<Card> discardpile;

    // constructor
    public CardDeck() {
        drawpile = new ArrayList<>();
        discardpile = new ArrayList<>();
    }

    public boolean checkCard(String playedCard) {
        Colors color = Colors.valueOf(playedCard.substring(0, 1)); // erster Char von card wird als Enum gespeichert
        String name = playedCard.substring(1);
        int value = Integer.parseInt(name);
        Card topCard = discardpile.get(discardpile.size() - 1);

        if(color == topCard.getColor()){
            return true;
        }
        if(value == topCard.getValue()) {
            return true;
        }
        if(playedCard.equals("ColorChange") || playedCard.equals("+4")) {
            return true;
        }

        return false;
    }

    public void addCardToDiscard(String card) {
        // special card
        if(card.equals("ColorChange") || card.equals("+4")) {
            Card addCard = new Card(card);
            discardpile.add(addCard);
            return;
        }

        Colors color = Colors.valueOf(card.substring(0, 1)); // erster Char von card wird als Enum gespeichert
        String name = card.substring(1);

        // action cards
        if(card.contains("Skip") || card.contains("Reverse") || card.contains("+2")) {
            Card addCard = new Card(color, name);
            discardpile.add(addCard);
            return;
        }

        int value = Integer.parseInt(name);

        // normal cards
        Card addCard = new Card(color, value);

        discardpile.add(addCard);
    }

    // shuffle method
    public void shuffle() {
        Collections.shuffle(drawpile);
    }

    // austeilen method
    public ArrayList<Card> dealCards(int quantity) {
        ArrayList<Card> handCards = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            handCards.add(drawpile.get(i));
            drawpile.remove(i);
        }
        return handCards;
    }

    // method: print all card names
    public void printAllCards() {
        for (Card c : drawpile) {
            System.out.print(c.name + " ");
        }
        System.out.println();
    }

    public int countCards() {
        return drawpile.size();
    }

    public void createDrawPile() {
        // 1-9er cards and actioncards
        for (int i = 0; i < 2; i++) {
            for (Colors co : Colors.values()) {
                for (int j = 1; j <= 9; j++) {
                    Card c = new Card(co, j);
                    drawpile.add(c);
                }
                Card c1 = new Card(co, "+2");
                Card c2 = new Card(co, "Reverse");
                Card c3 = new Card(co, "Skip");
                drawpile.add(c1);
                drawpile.add(c2);
                drawpile.add(c3);
            }
        }

        // 0er cards
        for (Colors co : Colors.values()) {
            Card o = new Card(co, 0);
            drawpile.add(o);
        }

        // special cards
        for (int i = 0; i < 4; i++) {
            Card s1 = new Card("+4");
            Card s2 = new Card("ColorChange");
            drawpile.add(s1);
            drawpile.add(s2);
        }
    }

    // discardpile takes 1 card of drawpile (index 0)
    public CardDeck addToDiscardPile() {
        discardpile.add(drawpile.get(0));
        drawpile.remove(0);
        return new CardDeck();
    }

    public void printDiscardPile() {
        System.out.println("Discard Pile: " + discardpile.get(discardpile.size() - 1));
    }

    @Override
    public String toString() {
        return "CardDeck{" +
                "deck=" + drawpile +
                '}';
    }
}
