package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    Film film1, film2;
    Personne personne1, personne2;

    @BeforeEach
    public void create() throws SQLException {
        Personne.createTable();
        Film.createTable();
        personne1=new Personne("Adams", "Richter");
        personne2=new Personne("Jean", "Philippe");
    }


    @AfterEach
    public void delete() throws SQLException{
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    public void testAjoutFilm() throws SQLException, RealisateurAbsentException{
        personne1.save();
        film1=new Film("film1", personne1);
        film1.save();
    }

    @Test
    public void testModificationFilm() throws SQLException, RealisateurAbsentException{
        personne1.save();
        personne2.save();
        film1=new Film("film1", personne2);
        film1.save();

        Personne personne=film1.getRealisateur();
        assertEquals( "Jean Philippe", personne.getNom()+" "+personne.getPrenom());

        film1=new Film("film1", personne1);
        film1.save();

        personne=film1.getRealisateur();
        assertEquals( "Adams Richter", personne.getNom()+" "+personne.getPrenom());
    }

    @Test
    public void testGetRealisateur() throws SQLException, RealisateurAbsentException{
        personne2.save();
        film2=new Film("film2", personne2);
        film2.save();
        Personne personne=Film.findById(1).getRealisateur();
        assertEquals("Jean Philippe", personne.getNom()+" "+personne.getPrenom());
    }

    @Test
    public void testRealisateurAbsentException() throws SQLException{
        try {
            film2 = new Film("film2", personne2);
            film2.save();
        }
        catch(RealisateurAbsentException realisateurAbsentException){
            System.out.println("RealisateurAbsentException est levée");
        }
    }

    @Test
    public void testFindById() throws SQLException, RealisateurAbsentException{
        personne1.save();
        film2 = new Film("film2", personne1);
        film2.save();
        Personne personne=Film.findById(1).getRealisateur();
        assertEquals( "Adams Richter", personne.getNom()+" "+personne.getPrenom());
    }

    @Test
    public void testFindByRealisateur() throws SQLException, RealisateurAbsentException{
        personne1.save();
        film2 = new Film("film2", personne1);
        film2.save();
        Film film3=new Film("film3", personne1);
        film3.save();
        Film film4=new Film("film4", personne1);
        film4.save();
        ArrayList<Film> films=Film.findByRealisateur(personne1);

        assertEquals("1 film2 1", films.get(0).toString());
        assertEquals("2 film3 1", films.get(1).toString());
        assertEquals("3 film4 1", films.get(2).toString());
    }
}