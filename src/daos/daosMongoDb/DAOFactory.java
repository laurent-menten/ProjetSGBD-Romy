package daos.daosMongoDb;

import daos.BaseActorDAO;
import daos.BaseDAOFactory;
import daos.BaseMovieDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOFactory extends BaseDAOFactory {

    // ---------------------------------------------------------------
    // paramètres "variables"
    // ---------------------------------------------------------------
    public final String dbName = "ProjetSGBD";

    // ---------------------------------------------------------------
    // paramètres "statiques"
    // ---------------------------------------------------------------
    private final String driver = "com.dbschema.MongoJdbcDriver";
    private final String url = "jdbc:mariadb://localhost:3306/" + dbName + "?serverTimezone=UTC";

    public DAOFactory(String username, String password){
        super(username, password);
    }

    @Override
    protected Connection getConnection(String username, String password) {
        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(DAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }

    @Override
    public BaseActorDAO getActorDAO() {
        if (actorDAO == null) actorDAO = new ActorDAO(connection);
        return actorDAO;
    }

    @Override
    public BaseMovieDAO getMovieDAO() {
        if (movieDAO == null) movieDAO = new MovieDAO(connection);
        return movieDAO;
    }

    @Override
    public BaseDAOFactory connectTo(String username, String password) {
        if (daoFactory == null) {
            daoFactory = new DAOFactory(username, password);
        }
        return daoFactory;
    }
}
