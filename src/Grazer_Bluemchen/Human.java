package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.Scanner;

public class Human extends Player{

    public Human(Scanner input, PrintStream output) {
        super(input, output);
    }

    @Override
    public Card searchHandCards(Card topCard) {
        while (true) {
            output.println("Spiele eine deiner Karten oder hebe eine Karte (mit h): ");
            String playCard = input.next();

            // abheben & Karte Spielen noch möglich!
            if(playCard.equalsIgnoreCase("h")) {
                return null;
            }

            for (Card hc : handCards) {
                if (hc.name.equalsIgnoreCase(playCard)) {
                    return hc;
                }
            }
            System.out.println("Du hast diese Karte nicht auf der Hand! Spiele eine andere Karte!");
        }
    }

    @Override
    public Card playIfPossible() {
        while(true) {
            output.println("Welche Karte willst du spielen? Wenn keine Karte passt, schreibe W für weiter!");

            String playCard = input.next();

            if (playCard.equalsIgnoreCase("w")) {
                return null;
            }

            for (Card hc : handCards) {
                if (hc.name.equalsIgnoreCase(playCard)) {
                    return hc;
                }
            }
            System.out.println("Du hast diese Karte nicht auf der Hand! Spiele eine andere Karte!");
        }
    }

}
