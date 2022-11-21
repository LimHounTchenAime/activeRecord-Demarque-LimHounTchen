package activeRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {
    @Test
    public void testConnection(){
        //appel de getConnection qui retourne Connection
        Connection connection=DBConnection.getConnection();
        Connection connection1=DBConnection.getConnection();

        //comparaison des addresses des deux connections
        assertEquals(connection, connection1);

        //appel de setConnection et creation de connection2
        DBConnection.setNomDB("test");
        Connection connection2=DBConnection.getConnection();

        //verification que les bdd sont differentes
        assert(connection!=connection2);
    }
}