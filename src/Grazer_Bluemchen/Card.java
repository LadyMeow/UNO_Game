package Grazer_Bluemchen;

public class Card {
    public Colors color;
    public int value;
    public String name;
    public int points;
    // name is combination of color + value (z.B: R3)


    // normal Card
    public Card(Colors color, int value) {
        this.color = color;
        this.value = value;
        name = color + Integer.toString(value);
        points = value;
    }

    // ActionCard
    public Card(Colors color, String name) {
        this.color = color;
        this.name = color + name;
        points = 20;

    }

    // SpecialCard
    public Card(String name) {
        this.name = name;
        points = 50;
    }

    @Override
    public String toString() {
        return "Card{" +
                "color='" + color + '\'' +
                ", value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
