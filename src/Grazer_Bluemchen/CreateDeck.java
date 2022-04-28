package Grazer_Bluemchen;

public class CreateDeck {

    public static void main(String[] args) {

        // Deck erstellen:
        CardDeck deck = new CardDeck();

        for (int i = 0; i < 2; i++) {
            // 2x: 1-9 in all colors
            for (int j = 1; j <= 9; j++) {
                Card c = new Card(Colors.R, j);
                deck.addCard(c);
            }

            Card r1 = new Card("R", "+2");
            Card r2 = new Card("R", "Reverse");
            Card r3 = new Card("R", "Skip");
            deck.addCard(r1);
            deck.addCard(r2);
            deck.addCard(r3);

            for (int j = 1; j <= 9; j++) {
                Card c = new Card("B", j);
                deck.addCard(c);
            }

            Card b1 = new Card("B", "+2");
            Card b2 = new Card("B", "Reverse");
            Card b3 = new Card("B", "Skip");
            deck.addCard(b1);
            deck.addCard(b2);
            deck.addCard(b3);

            for (int j = 1; j <= 9; j++) {
                Card c = new Card("G", j);
                deck.addCard(c);
            }

            Card g1 = new Card("G", "+2");
            Card g2 = new Card("G", "Reverse");
            Card g3 = new Card("G", "Skip");
            deck.addCard(g1);
            deck.addCard(g2);
            deck.addCard(g3);

            for (int j = 1; j <= 9; j++) {
                Card c = new Card("Y", j);
                deck.addCard(c);
            }

            Card c1 = new Card("Y", "+2");
            Card c2 = new Card("Y", "Reverse");
            Card c3 = new Card("Y", "Skip");
            deck.addCard(c1);
            deck.addCard(c2);
            deck.addCard(c3);
        }

        // 0er Cards
        Card o1 = new Card("R", 0);
        Card o2 = new Card("B", 0);
        Card o3 = new Card("G", 0);
        Card o4 = new Card("Y", 0);
        deck.addCard(o1);
        deck.addCard(o2);
        deck.addCard(o3);
        deck.addCard(o4);

        // special cards
        for (int i = 0; i < 4; i++) {
            Card s1 = new Card("+4");
            Card s2 = new Card("ColorChange");
            deck.addCard(s1);
            deck.addCard(s2);
        }


        deck.printAllCards();
        System.out.println(deck.countCards());
        deck.shuffle();
        deck.printAllCards();

        Player nr1 = new Player();
        System.out.println(nr1);

        // added dealt Cards to player nr1
        nr1.addHandCards(deck.dealCards());

        System.out.println(nr1);
        System.out.println(deck.countCards());


        // methode für summe von allen Cards im Spiel

    }
}
