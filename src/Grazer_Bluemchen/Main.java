package Grazer_Bluemchen;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();
        Player p4 = new Player();


        Scanner input = new Scanner(System.in);
        UNOApp app = new UNOApp(input, System.out);
        app.Run();
        input.close();
        System.out.println("Das Programm wird beendet ...");



    }
}
