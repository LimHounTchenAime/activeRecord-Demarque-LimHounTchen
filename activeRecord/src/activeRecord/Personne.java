package activeRecord;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

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
        Personne p = null;
        while(rs.next()){
            p = new Personne(rs.getString("NOM"),rs.getString("PRENOM"));
            p.id = rs.getInt("ID");
            tab.add(p);

        }
        return tab;
    }

    public static Personne findById(int id) throws SQLException{
        Connection connect=DBConnection.getConnection();

        Personne p = null;
        String queryString = "SELECT * FROM personne WHERE ID = ?";
        PreparedStatement stmt = connect.prepareStatement(queryString);
        stmt.setInt(1,id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            p = new Personne(rs.getString("NOM"),rs.getString("PRENOM"));
            p.id = rs.getInt("ID");
        }
        return p;
    }

    public static ArrayList<Personne> findByName(String name) throws SQLException{
        Connection connect=DBConnection.getConnection();

        String queryString = "SELECT * FROM personne WHERE NOM = ?";
        PreparedStatement stmt = connect.prepareStatement(queryString);
        ResultSet rs = stmt.executeQuery();
        ArrayList<Personne> tab = new ArrayList<>();
        Personne p = null;
        while(rs.next()){
            p = new Personne(rs.getString("NOM"),rs.getString("PRENOM"));
            p.id = rs.getInt("ID");
            tab.add(p);
        }
        return tab;
    }

    public static void createTable() throws SQLException{
        Connection connect=DBConnection.getConnection();
        String createString = "CREATE TABLE personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException{
        Connection connect=DBConnection.getConnection();
        String createString = "DROP TABLE IF EXISTS personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public void delete() throws SQLException {
        Connection connect=DBConnection.getConnection();
        String query = "DELETE FROM personne WHERE id = ?";
        PreparedStatement stmt = connect.prepareStatement(query);
        stmt.setInt(1,this.id);
        stmt.executeUpdate();
        this.id=-1;
    }

    public void save() throws SQLException {
        if(this.id==-1){
            this.saveNew();
        } else {
            this.update();
        }
    }

    private void saveNew() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String query = "INSERT INTO personne (NOM,PRENOM) VALUES (?,?)";
        PreparedStatement stmt = connect.prepareStatement(query);
        stmt.setString(1, this.nom);
        stmt.setString(2, this.prenom);
        stmt.executeUpdate();

        query = "SELECT ID FROM personne WHERE NOM=? AND PRENOM=?";
        stmt = connect.prepareStatement(query);
        stmt.setString(1,this.nom);
        stmt.setString(2,this.prenom);
        this.id = stmt.executeQuery().getInt("ID");
    }

    private void update() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String query = "UPDATE personne" +
                "SET NOM=?,PRENOM=?" +
                "WHERE ID=?";
        PreparedStatement stmt = connect.prepareStatement(query);
        stmt.setString(1, this.nom);
        stmt.setString(2, this.prenom);
        stmt.setInt(3,this.id);
        stmt.executeUpdate();
    }

}
