package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Bot extends Player{

    // attribut niveau (evtl. before foreach in searchHandCards - sort cards by points)

    public Bot(Scanner input, PrintStream output) {
        super(input, output);
    }
@Override
    public Card searchHandCards(Card topCard) {

        for (Card c : handCards) {
            if(topCard.getColor() == c.getColor()) {
                return c;
            }
            if(topCard.getValue() == c.getValue()) {
                return c;
            }
            if(c.name.equals("ColorChange") || c.name.equals("+4")) {
                return c;
            }
        }
        return null;
    }


}
