package Grazer_Bluemchen.DB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBApp {
    private static final String CREATETABLE = "CREATE TABLE Sessions (Player varchar(100) NOT NULL, Session int NOT NULL, Round int NOT NULL, Score int NOT NULL, CONSTRAINT PK_Sessions PRIMARY KEY (Player, Session, Round));";
    private static final String INSERT_TEMPLATE= "INSERT INTO Sessions (Player, Session, Round, Score) VALUES ('%1s', %2d, %3d, %4d);";
    private static final String SELECT_BYPLAYERANDSESSION = "SELECT Player, SUM(Score) AS Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";




    public static void main(String[] args) {
        try{
            SqliteClient client = new SqliteClient("demodatabase.sqlite");
            if (client.tableExists("Sessions")){
                client.executeStatement("DROP TABLE Sessions;");
            }
            client.executeStatement(CREATETABLE);

            client.executeStatement(String.format(INSERT_TEMPLATE, "Anita", 1, 1, 50));
            client.executeStatement(String.format(INSERT_TEMPLATE, "Hans", 1, 1, 0));
            client.executeStatement(String.format(INSERT_TEMPLATE, "Anita", 1, 2, 20));
            client.executeStatement(String.format(INSERT_TEMPLATE, "Hans", 1, 2, 100));

            ArrayList<HashMap<String, String>> results = client.executeQuery(String.format(SELECT_BYPLAYERANDSESSION, "Anita", 1));

            for (HashMap<String, String> map : results) {
                System.out.println(map.get("Player") + " hat derzeit:  " + map.get("Score") + " Punkte");
            }
        }catch (SQLException ex) {
            System.out.println("Ups! Something went wrong:" + ex.getMessage());
        }
    }
}
