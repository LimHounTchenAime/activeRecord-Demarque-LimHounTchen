package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {
    private int id;
    private String nom, prenom;

    public Personne(String n, String p){
        id=-1;
        nom=n;
        prenom=p;
    }

    public int getId(){
        return id;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        Connection connect=DBConnection.getConnection();

        String queryString = "SELECT * FROM personne";
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(queryString);
        ArrayList<Personne> tab = new ArrayList<>();
        while(rs.next()){
            tab.add(new Personne(rs.getString("NOM"),rs.getString("PRENOM")));
        }
        return tab;
    }

    public static Personne findById(int id) throws SQLException{
        Connection connect=DBConnection.getConnection();

        Personne p = null;
        String queryString = "SELECT * FROM personne WHERE ID = ?";
        PreparedStatement stmt = connect.prepareStatement(queryString);
        stmt.setInt(1,id);
        ResultSet rs = stmt.executeQuery(queryString);
        if(rs.next()){
            p = new Personne(rs.getString("NOM"),rs.getString("PRENOM"));
        }
        return p;
    }

    public static ArrayList<Personne> findByName(String name) throws SQLException{
        Connection connect=DBConnection.getConnection();

        String queryString = "SELECT * FROM personne WHERE NOM = ?";
        PreparedStatement stmt = connect.prepareStatement(queryString);
        ResultSet rs = stmt.executeQuery(queryString);
        ArrayList<Personne> tab = new ArrayList<>();
        while(rs.next()){
            tab.add(new Personne(rs.getString("NOM"),rs.getString("PRENOM")));
        }
        return tab;
    }

}
