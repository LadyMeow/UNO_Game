package Grazer_Bluemchen;

public class Card {
    private Colors color;
    private int value;
    public String name;
    private int points;
    // name is combination of color + value (z.B: R3)


    // normal Card
    public Card(Colors color, int value) {
        this.color = color;
        this.value = value;
        name = color + Integer.toString(value);
        points = value;
    }

    // ActionCard (Reverse, +2, skip)
    public Card(Colors color, String name) {
        this.color = color;
        this.name = color + name;
        points = 20;

    }

    // SpecialCard (+4, ColorChange)
    public Card(String name) {
        this.name = name;
        points = 50;
    }

    @Override
    public String toString() {
        return name;
    }

    public Colors getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public int getPoints() {
        return points;
    }
}
