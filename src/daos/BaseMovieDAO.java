package daos;

import beans.Movie;

import java.sql.Connection;

public abstract class BaseMovieDAO extends DAO<Movie, Integer> {

    public BaseMovieDAO(Connection connection) {
        super(connection);
    }
}
