package Grazer_Bluemchen.Players;

import Grazer_Bluemchen.Cards.Card;
import Grazer_Bluemchen.Cards.Colors;
import Grazer_Bluemchen.Help.Help;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Human extends Player {
    private Help help = new Help();

    public Human(Scanner input, PrintStream output) {
        super(input, output);
    }

    @Override
    public Card searchHandCards(Card topCard) throws IOException {
        while (true) {
            output.println("Spiele eine deiner Karten oder hebe eine Karte (mit k). Wenn du Hilfe brachst schreibe h: ");
            String playCard = input.nextLine();

            if(playCard.equalsIgnoreCase("h")) {
                help.printHelp();
            }

            if (playCard.toLowerCase().contains("uno")) {
                setUno(true);
                playCard = playCard.replace("uno", "").trim();
            }

            // abheben & Karte Spielen noch möglich!
            if (playCard.equalsIgnoreCase("k")) {
                return null;
            }

            for (Card hc : handCards) {
                if (hc.getName().equalsIgnoreCase(playCard)) {
                    return hc;
                }
            }
            output.println("Du hast diese Karte nicht auf der Hand! Spiele eine andere Karte!");
        }
    }

    @Override
    public Card playIfPossible(Card topCard) {
        while (true) {
            output.println("Welche Karte willst du spielen? Wenn keine Karte passt, schreibe W für weiter!");

            String playCard = input.nextLine();

            if (playCard.toLowerCase().contains("uno")) {
                setUno(true);
                playCard = playCard.replace("uno", "").trim();
            }

            if (playCard.equalsIgnoreCase("w")) {
                return null;
            }

            for (Card hc : handCards) {
                if (hc.getName().equalsIgnoreCase(playCard)) {
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

        if (colorWish.equalsIgnoreCase("R")) {
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

    public int contestPlus4(Player nextPlayer, Card topCard) {
        output.println(nextPlayer + ": willst du die +4 anfechten? (J/N)");

        if (input.nextLine().equalsIgnoreCase("j")) {
            output.print(getName() + ": ");
            printHandCards();

            if (checkContest(topCard)) {
                output.println(getName() + " hat geschummelt und bekommt 4 Strafkarten!");
                return 1;
            } else if(!checkContest(topCard)) {
                output.println(getName() + " hat NICHT geschummelt! Du bekommst jetzt 6 Strafkarten.");
                return 2;
            }
        }
        return 0;
    }

    public boolean checkContest(Card topCard) {
        for (Card c : handCards) {
            if (c.getName().equals("ColorChange") || c.getName().equals("+4")) {

            } else if (c.getColor() == topCard.getColor()) {
                return true;
            }
        }
        return false;
    }

}
