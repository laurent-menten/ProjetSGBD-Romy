package daos.daosMariaDb;

import beans.Movie;
import daos.interfaces.IMovieDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MariaDbMovieDAO implements IMovieDAO {
    public final String tableName = "movies";
    public final String keyColumnName = "id";
    public final String titleColumnName = "title";
    public final String releaseDateColumnName = "releaseDate";

    public final Connection conn;

    public MariaDbMovieDAO(Connection connection) {
        this.conn = connection;
    }

    public void create(Movie obj) throws SQLException {
        String rq = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);", tableName, keyColumnName, titleColumnName, releaseDateColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, obj.getId());
        ps.setObject(2, obj.getTitle());
        ps.setObject(3, obj.getReleaseDate());
        ps.executeUpdate();
    }

    public void update(Movie obj) throws SQLException {
        String rq = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", tableName, titleColumnName, releaseDateColumnName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, obj.getTitle());
        ps.setObject(2, obj.getReleaseDate());
        ps.setObject(3, obj.getId());
        ps.executeUpdate();
    }

    public void delete(Movie obj) throws SQLException {
        deleteById(obj.getId());
    }

    public void deleteById(Integer id) throws SQLException {
        String rq = String.format("DELETE FROM %s WHERE %s = ?;", tableName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, id);
        ps.executeUpdate();
    }

    public Movie findById(Integer id) throws SQLException {
        String rq = String.format("SELECT * FROM %s WHERE %s=?;", tableName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rsLineToObj(rs);
        }
        return null; // Return null if no result is found
    }

    public Movie[] findAll() throws SQLException {
        String rq = String.format("SELECT * FROM %s;", tableName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ResultSet rs = ps.executeQuery();

        List<Movie> lst = new LinkedList<>();
        while (rs.next()) {
            lst.add(rsLineToObj(rs));
        }

        return lst.toArray(new Movie[0]);
    }

    private Movie rsLineToObj(ResultSet rs) throws SQLException {
        return new Movie(
                rs.getInt(keyColumnName),
                rs.getString(titleColumnName),
                rs.getDate(releaseDateColumnName).toLocalDate(),
                new int[0]);
    }
}
