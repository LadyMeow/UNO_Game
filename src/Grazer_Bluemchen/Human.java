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
            output.println("Spiele eine deiner Karten oder hebe eine Karte (mit k): ");
            String playCard = input.nextLine();

            if(playCard.toLowerCase().contains("uno")) {
                uno = true;
                playCard = playCard.replace(" uno", "");
            }

            // abheben & Karte Spielen noch möglich!
            if(playCard.equalsIgnoreCase("k")) {
                return null;
            }

            for (Card hc : handCards) {
                if (hc.name.equalsIgnoreCase(playCard)) {
                    return hc;
                }
            }
            output.println("Du hast diese Karte nicht auf der Hand! Spiele eine andere Karte!");
        }
    }

    @Override
    public Card playIfPossible() {
        while(true) {
            output.println("Welche Karte willst du spielen? Wenn keine Karte passt, schreibe W für weiter!");

            String playCard = input.nextLine();

            if(playCard.toLowerCase().contains("uno")) {
                uno = true;
                playCard = playCard.replace(" uno", "");
            }

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

    @Override
    public Colors chooseColor() {
        String colorWish;
        output.println("Welche Farbe wünschst du dir? (R, B, Y, G)");
        colorWish = input.nextLine();

        if(colorWish.equalsIgnoreCase("R")) {
            output.println("Ich wünsche mir rot!");
            return Colors.R;
        } else if (colorWish.equalsIgnoreCase("B")) {
            output.println("Ich wünsche mir blau!");
            return Colors.B;
        } else if (colorWish.equalsIgnoreCase("Y")) {
            output.println("Ich wünsche mir gelb!");
            return Colors.Y;
        } else if (colorWish.equalsIgnoreCase("G")) {
            output.println("Ich wünsche mir grün!");
            return Colors.G;
        } else {
            output.println("Diese Farbe ist nicht gültig!");
            chooseColor();
        }

        return null;
    }

}
