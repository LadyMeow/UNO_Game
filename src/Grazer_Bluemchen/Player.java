package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player {
    private String name;
    //public int order; // 1-4 - equals number of player
    public ArrayList<Card> handCards;
    protected final Scanner input;
    protected final PrintStream output;

    public Player(Scanner input, PrintStream output) {
        handCards = new ArrayList<>();
        this.input = input;
        this.output = output;
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
    public void removeHandCard(String card) {
        handCards.removeIf(c -> c.name.equals(card));
    }

    // searchCards (only Bot)
    public abstract String searchHandCards(Card topCard);


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", handCards=" + handCards +
                '}';
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
