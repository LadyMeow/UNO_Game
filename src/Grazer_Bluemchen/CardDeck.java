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

    public CardType checkCard(Card playedCard) {
        CardType validation = CardType.INVALID;

        Card topCard = discardpile.get(discardpile.size() - 1);

        if (playedCard.getColor() == topCard.getColor()) {
            validation = CardType.NORMAL;
        }
        if (playedCard.getValue() == topCard.getValue() && (topCard.getValue() >= 0)) { // special und action cards value = -1!!
            validation = CardType.NORMAL;
        }
        if (playedCard.getName().contains("+2") && ((topCard.getColor() == playedCard.getColor()) || topCard.getName().contains("+2"))) {
            validation = CardType.PLUS_TWO;
        }
        if (playedCard.getName().toLowerCase().contains("reverse") && ((topCard.getColor() == playedCard.getColor()) || topCard.getName().contains("Reverse"))) {
            validation = CardType.REVERSE;
        }
        if (playedCard.getName().toLowerCase().contains("skip") && ((topCard.getColor() == playedCard.getColor()) || topCard.getName().contains("Skip"))) {
            validation = CardType.SKIP;
        }
        if (playedCard.getName().toLowerCase().contains("colorchange")) {
            validation = CardType.COLORCHANGE;
        }
        if (playedCard.getName().contains("+4")) {
            validation = CardType.PLUS_FOUR;
        }
        return validation;
    }

    public void addCardToDiscard(Card card) {
        discardpile.add(card);
    }

    // shuffle method
    public void shuffle() {
        Collections.shuffle(drawpile);
    }

    // austeilen method
    public ArrayList<Card> dealCards(int quantity) {
        ArrayList<Card> handCards = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            if(drawpile.size() == 0) {
                Card helper = discardpile.get(0); // top card
                discardpile.remove(0);

                Collections.shuffle(discardpile);
                drawpile.addAll(discardpile);
                discardpile.clear();
                discardpile.add(helper);
            }
            handCards.add(drawpile.get(0));
            drawpile.remove(0);
        }
        return handCards;
    }

    // method: print all card names
    public void printAllCards() {
        for (Card c : drawpile) {
            System.out.print(c.getName() + " ");
        }
        System.out.println();
    }

    public int countCards(ArrayList<Card> pile) {
        return pile.size();
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
        while(drawpile.get(0).getName().contains("+4")) {
            shuffle();
        }
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
