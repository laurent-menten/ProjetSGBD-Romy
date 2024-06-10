package daos.daosMongoDb;

import beans.Movie;
import daos.BaseMovieDAO;
import daos.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class MovieDAO extends BaseMovieDAO {
    public final String tableName = "movies";
    public final String keyColumnName = "id";
    public final String titleColumnName = "title";
    public final String releaseDateColumnName = "releaseDate";

    public MovieDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Movie obj) throws SQLException {
        String rq = String.format("INSERT INTO %s  VALUES(?,?,?);", tableName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, obj.getId());
        ps.setObject(2, obj.getTitle());
        ps.setObject(3, obj.getReleaseDate());
        int result = ps.executeUpdate();
    }

    @Override
    public void update(Movie obj) throws SQLException {
        String rq = String.format("UPDATE %s  SET %s = ?, %s = ? WHERE %s = ?", tableName, titleColumnName, releaseDateColumnName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, obj.getId());
        ps.setObject(2, obj.getTitle());
        ps.setObject(3, obj.getReleaseDate());
        int result = ps.executeUpdate();
    }

    @Override
    public void delete(Movie obj) throws SQLException {
        deleteById(obj.getId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String rq = String.format("DELETE FROM %s WHERE %s = ?;", tableName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, id);
        int result = ps.executeUpdate();
    }

    @Override
    public Movie findById(Integer id) throws SQLException {
        String rq = String.format("SELECT * FROM %s WHERE %s=?;", tableName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();  // se placer sur la 1ère ligne si elle existe
        return rsLineToObj(rs);
    }

    @Override
    public Movie[] findAll() throws SQLException {
        String rq = String.format("SELECT * FROM %s;", tableName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ResultSet rs = ps.executeQuery();

        List<Movie> lst = new LinkedList<>();
        while (rs.next()) {
            lst.add(rsLineToObj(rs));
        }

        return lst.toArray(Movie[]::new);
    }

    @Override
    public Movie rsLineToObj(ResultSet rs) throws SQLException {
        if (rs.getRow() == 0) return null;
        return new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                LocalDate.parse(rs.getDate("birthdate").toString()));
    }

    /*
    *  1 ActeurBidule
    *  1 FilmRandom
    *  1 1
    *
    *  1 ActeurBidule 1 FilmRandom
    *  2 ActeurMachin 1 FilmRandom
    *
    * */
}