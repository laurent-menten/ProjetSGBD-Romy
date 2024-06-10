package daos.interfaces;

import java.sql.SQLException;

public interface IDAOFactory {
    IActorDAO getActorDAO();
    IMovieDAO getMovieDAO();

    void beginTransaction() throws SQLException;
    void commitTransaction() throws SQLException;
    void rollbackTransaction() throws SQLException;
    void close() throws SQLException;
}
