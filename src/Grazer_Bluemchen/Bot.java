package Grazer_Bluemchen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Bot extends Player{

    // attribut niveau

    public Bot(Scanner input, PrintStream output) {
        super(input, output);
    }
@Override
    public String searchHandCards(Card lastCard) {

        for (Card c : handCards) {
            if(lastCard.getColor() == c.getColor()) {
                return c.name;
            }
            if(lastCard.getValue() == c.getValue()) {
                return c.name;
            }
            if(c.name.equals("ColorChange") || c.name.equals("+4")) {
                return c.name;
            }
        }
        return null;
    }


}
