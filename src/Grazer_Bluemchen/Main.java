package Grazer_Bluemchen;

import Grazer_Bluemchen.DB.SqliteClient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final String CREATETABLE = "CREATE TABLE Sessions (Player varchar(100) NOT NULL, Session int NOT NULL, " +
            "Round int NOT NULL, Score int NOT NULL, CONSTRAINT PK_Sessions PRIMARY KEY (Player, Session, Round));";


    public static void main(String[] args) {


        try {
            SqliteClient client = new SqliteClient("demodatabase.sqlite");
            if (client.tableExists("Sessions")) {
                client.executeStatement("DROP TABLE Sessions;");
            }
            client.executeStatement(CREATETABLE);

            Scanner input = new Scanner(System.in);
            UNOApp app = new UNOApp(input, System.out, client);
            app.Run();

            while (input.nextLine().equalsIgnoreCase("j")) {
                app.Run();
            }

            input.close();
            System.out.println("Das Programm wird beendet ...");
        } catch (SQLException ex) {
            System.out.println("Ups! Something went wrong:" + ex.getMessage());
        }
    }


}
