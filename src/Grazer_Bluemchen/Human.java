package Grazer_Bluemchen;

import java.util.ArrayList;

public class Human extends Player{

    public Human() {
        super();
    }

    @Override
    public Card playCard(Card c) {
        handCards.remove(c);
        return c;
    }

}
