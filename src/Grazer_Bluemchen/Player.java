package Grazer_Bluemchen;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player {
    private String name;
    //public int order; // 1-4 - equals number of player
    public ArrayList<Card> handCards;
    protected final Scanner input;
    protected final PrintStream output;
    protected boolean uno;

    public Player(Scanner input, PrintStream output) {
        handCards = new ArrayList<>();
        this.input = input;
        this.output = output;
        this.uno = false;
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

    // remove Card from handCards
    public void removeHandCard(Card card) {
        for (Card c : handCards) {
            if(c.equals(card)) {
                handCards.remove(c);
                return;
            }
        }
    }

    // searchCards (only Bot)
    public abstract Card searchHandCards(Card topCard) throws IOException;

    // play Card if possible
    public abstract Card playIfPossible(Card topCard);

    // ColorWish
    public abstract Colors chooseColor();

    // check if color in handcards (contest)
    public abstract boolean checkContest(Card topCard);


    @Override
    public String toString() {
        return name;
    }

    // getter
    public String getName() {
        return name;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }
}
