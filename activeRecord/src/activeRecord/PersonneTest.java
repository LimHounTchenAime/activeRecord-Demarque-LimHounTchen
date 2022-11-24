package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {

    @BeforeEach
    void setUp() throws SQLException{
        Personne.createTable();
        Personne[] tab = {new Personne("Spielberg","Steven"),
                new Personne("Scott","Ridley"),
                new Personne("Kubrick","Stanley"),
                new Personne("Fincher","David")};
        for(int i=0;i<tab.length;i++){
            tab[i].save();
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    void findAll() throws SQLException{
        Personne p1 , p2, p3, p4;
        p1 = new Personne("Spielberg","Steven");
        p2 = new Personne("Scott","Ridley");
        p3 = new Personne("Kubrick","Stanley");
        p4 = new Personne("Fincher","David");
        ArrayList<Personne> tab = Personne.findAll();
        ArrayList<Personne> tabassert = new ArrayList<Personne>();
        tabassert.add(p1);
        tabassert.add(p2);
        tabassert.add(p3);
        tabassert.add(p4);
        for(int i = 0;i<tab.size();i++){
            assertEquals(tabassert.get(i).getNom(), tab.get(i).getNom());
            assertEquals(tabassert.get(i).getPrenom(), tab.get(i).getPrenom());
        }

    }

    @Test
    void findById() throws SQLException {
        Personne p1 = new Personne("Spielberg","Steven");
        Personne test = Personne.findById(1);
        assertEquals(p1.getNom(),test.getNom());
        assertEquals(p1.getPrenom(),test.getPrenom());
    }

    @Test
    void findByName() throws SQLException {
        Personne p1 = new Personne("Spielberg","Steven");
        ArrayList<Personne> test = Personne.findByName("Spielberg");
        for (int i = 0;i<test.size();i++){
            assertEquals(p1.getNom(),test.get(i).getNom());
            assertEquals(p1.getPrenom(),test.get(i).getPrenom());
        }

    }

    @Test
    void delete() throws SQLException {
        Personne p1 , p2, p3, p4;
        p1 = new Personne("Spielberg","Steven");
        p2 = new Personne("Scott","Ridley");
        p3 = new Personne("Kubrick","Stanley");
        p4 = Personne.findById(4);
        p4.delete();
        ArrayList<Personne> tab = Personne.findAll();
        ArrayList<Personne> tabassert = new ArrayList<Personne>();
        tabassert.add(p1);
        tabassert.add(p2);
        tabassert.add(p3);
        for(int i = 0;i<tab.size();i++){
            assertEquals(tabassert.get(i).getNom(), tab.get(i).getNom());
            assertEquals(tabassert.get(i).getPrenom(), tab.get(i).getPrenom());
        }
    }

    @Test
    void saveNew() throws SQLException {
        Personne p5 = new Personne("DJ_m0m0","AmauryD");
        p5.save();
        Personne test = Personne.findById(5);
        assertEquals("DJ_m0m0",test.getNom());
        assertEquals("AmauryD", test.getPrenom());
    }

    @Test
    void saveUpdate() throws SQLException {
        Personne p1 = Personne.findById(1);
        p1.setNom("DJ_m0m0");
        p1.setPrenom("AmauryD");
        p1.save();
        Personne test = Personne.findById(1);
        assertEquals(p1.getNom(),test.getNom());
        assertEquals(p1.getPrenom(),test.getPrenom());
    }
}