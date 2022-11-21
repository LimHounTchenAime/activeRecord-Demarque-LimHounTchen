package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String queryString="select * from film where id=?";
        PreparedStatement preparedStatement=connection.prepareStatement(queryString);
        preparedStatement.setInt(1, id);
        ResultSet resultSet=preparedStatement.executeQuery(queryString);
        if(resultSet.next()){
            f=new Film(resultSet.getString("TITRE"), resultSet.getInt("ID"), resultSet.getInt("ID_REA"));
        }
        return f;
    }

    public Personne getRealisateur() throws SQLException{
        return Personne.findById(id_real);
    }
}
