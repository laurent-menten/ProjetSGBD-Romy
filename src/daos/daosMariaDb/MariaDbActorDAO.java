package daos.daosMariaDb;

import beans.Actor;
import daos.interfaces.IActorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MariaDbActorDAO implements IActorDAO {
    public final String tableName = "actors";
    public final String keyColumnName = "id";
    public final String firstnameColumnName = "firstname";
    public final String lastnameColumnName = "lastname";
    public final String birthdateColumnName = "birthdate";

    public final Connection conn;

    public MariaDbActorDAO(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void create(Actor obj) throws SQLException {
        String rq = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", tableName, keyColumnName, firstnameColumnName, lastnameColumnName, birthdateColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, obj.getId());
        ps.setObject(2, obj.getFirstname());
        ps.setObject(3, obj.getLastname());
        ps.setObject(4, obj.getBirthdate());
        ps.executeUpdate();
    }

    @Override
    public void update(Actor obj) throws SQLException {
        String rq = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?", tableName, firstnameColumnName, lastnameColumnName, birthdateColumnName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, obj.getFirstname());
        ps.setObject(2, obj.getLastname());
        ps.setObject(3, obj.getBirthdate());
        ps.setObject(4, obj.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(Actor obj) throws SQLException {
        deleteById(obj.getId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String rq = String.format("DELETE FROM %s WHERE %s = ?;", tableName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, id);
        ps.executeUpdate();
    }

    @Override
    public Actor findById(Integer id) throws SQLException {
        String rq = String.format("SELECT * FROM %s WHERE %s = ?;", tableName, keyColumnName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ps.setObject(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rsLineToObj(rs);
        }
        return null; // Return null if no result is found
    }

    @Override
    public Actor[] findAll() throws SQLException {
        String rq = String.format("SELECT * FROM %s;", tableName);
        PreparedStatement ps = conn.prepareStatement(rq);
        ResultSet rs = ps.executeQuery();

        List<Actor> lst = new LinkedList<>();
        while (rs.next()) {
            lst.add(rsLineToObj(rs));
        }

        return lst.toArray(new Actor[0]);
    }

    private Actor rsLineToObj(ResultSet rs) throws SQLException {
        return new Actor(
                rs.getInt(keyColumnName),
                rs.getString(firstnameColumnName),
                rs.getString(lastnameColumnName),
                rs.getDate(birthdateColumnName).toLocalDate());
    }
}
