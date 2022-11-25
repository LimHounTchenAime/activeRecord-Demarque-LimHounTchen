package activeRecord;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class Film {
    String titre;
    int id, id_real;

    public Film(String titre, Personne personne){
        this.titre=titre;
        this.id=-1;
        this.id_real=personne.getId();
    }

    private Film(String titre, int id, int id_real){
        this.titre=titre;
        this.id=id;
        this.id_real=id_real;
    }

    public static Film findById(int id) throws SQLException {
        Film f=null;
        Connection connection=DBConnection.getConnection();
        String queryString="select * from film where ID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(queryString);
        preparedStatement.setInt(1, id);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            f=new Film(resultSet.getString("TITRE"), resultSet.getInt("ID"), resultSet.getInt("ID_REA"));
        }
        return f;
    }

    public Personne getRealisateur() throws SQLException{
        return Personne.findById(id_real);
    }

    public static void createTable() throws SQLException{
        Connection connection=DBConnection.getConnection();
        String queryString="create table if not exists film(\n" +
                "\tid int(11) not null primary key auto_increment,\n" +
                "    titre varchar(40) not null,\n" +
                "    id_rea int(11) default null,\n" +
                "    constraint film_ibfk_1 foreign key(id_rea) references personne(id)\n" +
                ")";
        Statement statement=connection.createStatement();
        statement.executeUpdate(queryString);
    }

    public static void deleteTable() throws SQLException{
        Connection connection=DBConnection.getConnection();
        String queryString="drop table Film";
        Statement statement=connection.createStatement();
        statement.executeUpdate(queryString);
    }

    public void save() throws SQLException, RealisateurAbsentException{
        if(this.id_real==-1)
            throw new RealisateurAbsentException();
        if(this.id==-1)
            saveNew();
        else
            update();
    }

    public void update() throws SQLException{
        Connection connection=DBConnection.getConnection();

        String queryString="update Film set TITRE=?, ID_REA=? where ID=?";
        PreparedStatement preparedStatement=connection.prepareStatement(queryString);

        preparedStatement.setString(1, this.titre);
        preparedStatement.setInt(2, this.id_real);
        preparedStatement.setInt(3, this.id);

        preparedStatement.execute();
    }

    public void saveNew() throws SQLException{
        Connection connection=DBConnection.getConnection();

        String queryString="insert into Film(TITRE, ID_REA) values(?, ?)";
        PreparedStatement preparedStatement=connection.prepareStatement(queryString);

        preparedStatement.setString(1, this.titre);
        preparedStatement.setInt(2, this.id_real);

        preparedStatement.executeUpdate();
    }

    public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException{
        ArrayList<Film> res=new ArrayList<>();
        Connection connection=DBConnection.getConnection();

        String queryString="select * from film where id_rea=?";
        PreparedStatement preparedStatement=connection.prepareStatement(queryString);
        preparedStatement.setInt(1, p.getId());
        ResultSet resultSet=preparedStatement.executeQuery();
        while(resultSet.next()){
            res.add(new Film(resultSet.getString("TITRE"), resultSet.getInt("ID"), resultSet.getInt("ID_REA")));
        }
        return res;
    }

    public String toString(){
        return(this.id+" "+this.titre+" "+this.id_real);
    }
}
