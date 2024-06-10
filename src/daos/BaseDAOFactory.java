package daos;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAOFactory {
    // ---------------------------------------------------------------
    // gestion des DAOs
    // ---------------------------------------------------------------
    protected BaseMovieDAO movieDAO;
    protected BaseActorDAO actorDAO;

    public abstract BaseMovieDAO getMovieDAO();
    public abstract BaseActorDAO getActorDAO();

    // ---------------------------------------------------------------
    // DAOFactory est un singleton
    // ---------------------------------------------------------------
    protected BaseDAOFactory daoFactory = null;
    public abstract BaseDAOFactory connectTo(String username, String password);

    protected BaseDAOFactory(String username, String password) { connection = getConnection(username, password); }

    // ---------------------------------------------------------------
    // gestion de la connexion
    // ---------------------------------------------------------------
    protected Connection connection = null;
    protected abstract Connection getConnection(String username, String password);

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
