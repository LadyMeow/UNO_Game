package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Bot extends Player {

    // attribut niveau (evtl. before foreach in searchHandCards - sort cards by points)

    public Bot(Scanner input, PrintStream output) {
        super(input, output);
    }

    @Override
    public Card searchHandCards(Card topCard) {

        for (Card c : handCards) {
            if (topCard.getColor() == c.getColor()) {
                return c;
            } else if (topCard.getValue() == c.getValue()) {
                return c;
            } else if (c.name.equals("ColorChange") || c.name.equals("+4")) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Card playIfPossible() {
        return null;
    }

    @Override
    public Colors chooseColor() {
        for (Card c : handCards) {
            if ((!c.name.equals("ColorChange")) && (!c.name.equals("+4"))) {
                output.println("Wunschfarbe ist: " + c.getColor());
                return c.getColor();
            }
        }
        return Colors.R;
    }
}
