package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.Scanner;

public class Human extends Player{

    public Human(Scanner input, PrintStream output) {
        super(input, output);
    }

    public Card searchHandCards(Card topCard) {
        while (true) {
            output.println("Spiele eine deiner Karten: ");
            String playCard = input.next();

            for (Card hc : handCards) {
                if (hc.name.equalsIgnoreCase(playCard)) {
                    return hc;
                }
            }
            System.out.println("Du hast diese Karte nicht auf der Hand! Spiele eine andere Karte!");
        }
    }

}
