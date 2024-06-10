package daos.daosMariaDb;

import daos.daosMongoDb.MongoDbDAOFactory;
import daos.interfaces.IActorDAO;
import daos.interfaces.IDAOFactory;
import daos.interfaces.IMovieDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MariaDbDAOFactory implements IDAOFactory {
    // ---------------------------------------------------------------
    // paramètres "variables"
    // ---------------------------------------------------------------
    private IActorDAO actorDAO;
    private IMovieDAO movieDAO;

    private Connection connection;

    // ---------------------------------------------------------------
    // paramètres "statiques"
    // ---------------------------------------------------------------
    public static final String dbName = "Movies";
    private static final String driver = "org.mariadb.jdbc";
    private static final String url = "jdbc:mariadb://localhost:3306/" + dbName + "?serverTimezone=UTC";


    public MariaDbDAOFactory(String username, String password){
        connection = getConnection(username, password);
    }

    protected Connection getConnection(String username, String password) {
        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MongoDbDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }

    public IActorDAO getActorDAO() {
        if (actorDAO == null) actorDAO = new MariaDbActorDAO(connection);
        return actorDAO;
    }

    public IMovieDAO getMovieDAO() {
        if (movieDAO == null) movieDAO = new MariaDbMovieDAO(connection);
        return movieDAO;
    }

    // ---------------------------------------------------------------
    // gestion des Transactions
    // ---------------------------------------------------------------
    public void beginTransaction() throws SQLException { connection.setAutoCommit(false); }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    public void close() throws SQLException { connection.close(); }
}