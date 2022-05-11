package Grazer_Bluemchen;

import java.util.ArrayList;

public class Bot extends Player{

    // attribut niveau

    public Bot() {
        super();
    }

    @Override
    public Card playCard(Card c) {
        handCards.remove(c);
        return c;
    }


}
