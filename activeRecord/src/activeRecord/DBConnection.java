package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

public class DBConnection {
    private static Connection connect=null;
    private static String dbName="testpersonne";

    public static synchronized Connection getConnection(){
        if(connect==null)
        {
            // variables a modifier en fonction de la base
            String userName = "root";
            String password = "";
            String serverName = "localhost";
            //Attention, sous MAMP, le port est 8889
            String portNumber = "3306";

            // iL faut une base nommee testPersonne !

            // creation de la connection
            Properties connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", password);
            String urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;
            try {
                connect = DriverManager.getConnection(urlDB, connectionProps);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connect;
    }

    public static void setNomDB(String nomDB){
        dbName=nomDB;
        connect=null;
        getConnection();
    }
}
