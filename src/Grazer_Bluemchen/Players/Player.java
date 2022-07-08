package Grazer_Bluemchen.Players;

import Grazer_Bluemchen.Cards.Card;
import Grazer_Bluemchen.Cards.Colors;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player {
    private String name;
    public ArrayList<Card> handCards;
    protected final Scanner input;
    protected final PrintStream output;
    private boolean uno;

    public Player(Scanner input, PrintStream output) {
        handCards = new ArrayList<>();
        this.input = input;
        this.output = output;
        this.uno = false;
    }

    public void printHandCards() {
        output.print(name + ": ");

        for (Card c : handCards) {
            System.out.print(c.getName() + " ");
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
    public abstract Card searchHandCards(Card topCard, String playCard);

    // play Card if possible
    public abstract Card playIfPossible(Card topCard);

    // ColorWish
    public abstract Colors chooseColor();

    // contest +4
    public abstract int contestPlus4(Player nextPlayer, Card topCard);

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

    public boolean isUno() {
        return uno;
    }

    // setter
    public void setName(String name) {
        this.name = name;
    }

    public void setUno(boolean uno) {
        this.uno = uno;
    }
}
